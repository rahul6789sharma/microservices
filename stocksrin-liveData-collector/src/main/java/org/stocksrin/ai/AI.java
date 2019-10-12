package org.stocksrin.ai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.collector.option.data.LiveDataCollectortask;
import org.stocksrin.common.data.IndicesData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.NSEIndice;
import org.stocksrin.common.model.NSEIndicesData;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.common.utils.options.OptionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AI extends TimerTask {

	// map for vixmap of dte

	private long timeInteval = 120000;
	private static final Logger log = LoggerFactory.getLogger(LiveDataCollectortask.class);

	@Override
	public void run() {
		log.info("******* LiveIndicicesCollector Started**********");

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment

			while (CommonUtils.getEveningTime()) {
				try {
					getStradel();
					Thread.sleep(timeInteval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void getStradel() throws Exception {
		if (!NiftyData.shortedExpiry.isEmpty()) {
			String currentExpiry = NiftyData.shortedExpiry.first();
			OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
			// double spot = NiftyData.getNFSpot();
			// System.out.println("spot " + spot);
			double atm = optionModles.getAtmStrike();
			// System.out.println("atm " + atm);
			// System.out.println(IndicesData.getData());
			// System.out.println("vix " + IndicesData.getVIX());
			Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
			// System.out.println("dte " + dte);
			Integer value = (int) IndicesData.getVIX();

			Straddle straddle = new Straddle();
			straddle.setDte(dte.toString());
			straddle.setVix(value.toString());

			int total = getStradel(optionModles.getOptionModle(), atm);
			add(dte.toString(), value.toString(), total);
			System.out.println(NIftyAIData.straddleData);
		}

	}

	private static int getStradel(List<OptionModle> optionModles, Double strike) {
		int result = 0;
		for (OptionModle optionModle : optionModles) {

			if (optionModle.getStrike_price().equals(strike)) {
				result = (int) (optionModle.getC_ltp() + optionModle.getP_ltp());
				// System.out.println(result);
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String expiry = "27DEC2018";
		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		// System.out.println(getAllData());

		add(dte.toString(), "14", 140);
		add(dte.toString(), "15", 140);
		add(dte.toString(), "14", 150);
		add(dte.toString(), "14", 135);
		add(dte.toString(), "13", 135);
		add(dte.toString(), "14", 190);
		add(dte.toString(), "13", 90);
		add(dte.toString(), "15", 10);
		System.out.println(NIftyAIData.straddleData);
		// toJosn(data2);
		// fromJson();

	}

	private static void add(String dte, String vix, Integer stradleTotal) {

		if (!NIftyAIData.straddleData.containsKey(dte)) {
			Map<String, Straddle> map = new HashMap<>();
			Straddle straddle = new Straddle();
			straddle.setDte(dte);
			straddle.setVix(vix);
			straddle.setLow(stradleTotal);
			straddle.setHigh(stradleTotal);
			map.put(vix, straddle);
			NIftyAIData.straddleData.put(straddle.getDte(), map);
		} else {
			// data is there for this expiry
			Map<String, Straddle> data = NIftyAIData.straddleData.get(dte);
			// System.out.println("data is there for " + dte);
			udpate2(dte, data, vix, stradleTotal);
		}

	}

	private static void udpate2(String dte, Map<String, Straddle> data, String vix, Integer stradleTotal) {

		if (!data.containsKey(vix)) {
			// System.out.println("data is not there for this vix " + vix);
			Straddle straddle = new Straddle();
			straddle.setDte(dte);
			straddle.setVix(vix);
			straddle.setLow(stradleTotal);
			straddle.setHigh(stradleTotal);
			data.put(vix, straddle);
		} else {
			// System.out.println("data is there for this vix " + vix);
			Straddle straddle = data.get(vix);
			if (straddle.getLow() > stradleTotal) {
				straddle.setLow(stradleTotal);
			}
			if (straddle.getHigh() < stradleTotal) {
				straddle.setHigh(stradleTotal);
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