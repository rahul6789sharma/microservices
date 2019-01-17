package org.stocksrin.collector.option.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.IndicesData;
import org.stocksrin.common.model.NSEIndice;
import org.stocksrin.common.model.NSEIndicesData;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LiveIndicicesCollector extends TimerTask {

	private long timeInteval = 120000;
	private static final Logger log = LoggerFactory.getLogger(LiveDataCollectortask.class);

	@Override
	public void run() {
		log.info("******* LiveIndicicesCollector Started**********");
		BankNiftyData.clear();
		try {
			getAllData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment

			while (CommonUtils.getEveningTime()) {

				try {
					getAllData();
					Thread.sleep(timeInteval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List<NSEIndice> getAllData() throws Exception {
		List<NSEIndice> lst = new ArrayList<>();
		String data = sendGet(AppConstant.NIFTY_INDICES);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		NSEIndicesData obj = mapper.readValue(data, NSEIndicesData.class);
		NSEIndice[] d = obj.getData();

		for (NSEIndice data2 : d) {

			// data2.setDate(date);
			String ltp = data2.getLastPrice().replaceAll(",", "");
			data2.setLastPrice(ltp);
			lst.add(data2);

		}
		IndicesData.setData(lst);
		return lst;
	}

	// HTTP GET request
	private static String sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("Status code is not 200");
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
		// print result

	}

}
