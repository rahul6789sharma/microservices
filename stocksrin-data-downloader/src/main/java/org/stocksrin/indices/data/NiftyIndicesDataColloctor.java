package org.stocksrin.indices.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.model.NSEIndice;
import org.stocksrin.common.model.NSEIndicesData;
import org.stocksrin.common.utils.AppConstant;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NiftyIndicesDataColloctor {

	public static void main(String[] args) {
		try {
			// NSEIndice nifty = NiftyIndicesDataColloctor.getData("NIFTY BANK");
			// System.out.println(nifty);
			// System.out.println(nifty.toCsv());
			List<NSEIndice> data = getAllData();
			System.out.println("**********8");
			data.forEach((i)->{
				System.out.println(i.getName() + " :" + i.getChange());
			});
			// CommonUtils.appendData(nifty.toCsv(), STOCKSRIN_INDICES_NIFTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<NSEIndice> getAllData() throws Exception {

		List<NSEIndice> lst = new ArrayList<>();
		try {
			String data = sendGet(AppConstant.NIFTY_INDICES);
			String[] a = data.split("Normal Market has Closed.");
			String date = a[1].split("\"}")[0].trim().replace(",", "");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			NSEIndicesData obj = mapper.readValue(data, NSEIndicesData.class);
			NSEIndice[] d = obj.getData();
			for (NSEIndice data2 : d) {
				data2.setDate(date);
				String ltp = data2.getLastPrice().replaceAll(",", "");
				data2.setLastPrice(ltp);
				lst.add(data2);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return lst;
	}

	public static NSEIndice getData(String indice) throws Exception {

		String data = sendGet(AppConstant.NIFTY_INDICES);
		String[] a = data.split("Normal Market has Closed.");
		String date = a[1].split("\"}")[0].trim().replace(",", "");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		NSEIndicesData obj = mapper.readValue(data, NSEIndicesData.class);
		NSEIndice[] d = obj.getData();
		for (NSEIndice data2 : d) {
			if (data2.getName().equals(indice)) {
				data2.setDate(date);
				String ltp = data2.getLastPrice().replaceAll(",", "");
				data2.setLastPrice(ltp);
				return data2;
			}
		}
		return null;
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