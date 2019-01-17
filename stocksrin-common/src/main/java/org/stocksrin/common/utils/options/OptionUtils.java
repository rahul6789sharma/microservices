package org.stocksrin.common.utils.options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.stocksrin.common.model.option.FuturePrice;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.strategies.StrategyModel;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.USDINRUtils;
import org.stocksrin.common.utils.html.CommonHTMLDocParsher;
import org.stocksrin.common.utils.html.HTMLPageDocumentDownloader;

public class OptionUtils {

	public static void main(String[] args) throws Exception {
		String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/fxTracker/optChainDataByExpDates.jsp";
		OptionModles data = getUSDINROptionChain(url, null);
		List<OptionModle> lst = data.getOptionModle();
		for (OptionModle optionModle : lst) {
			// System.out.println(optionModle);
		}
		System.out.println(data);
	}

	public static double getATMStrike(OptionModles optionModles, int strikediff) {

		double spot = optionModles.getSpot();
		List<OptionModle> lst = optionModles.getOptionModle();
		double atmStrike = 0.0;
		for (OptionModle optionModle : lst) {

			double diff = Math.abs(spot - optionModle.getStrike_price());
			if (diff < strikediff) {
				atmStrike = optionModle.getStrike_price();
				break;
			}
		}

		return atmStrike;
	}

	public static OptionModles getUSDINROptionChain(String url, String expiry) throws Exception {

		Document doc = HTMLPageDocumentDownloader.getDocument(url);
		try {
			Elements c = CommonHTMLDocParsher.getOptionChainTable(doc, "octable", 0);
			OptionModles optionModles = CommonHTMLDocParsher.parseUSDINRColumn(doc, c);
			List<String> expiryList = CommonHTMLDocParsher.getSelectBoxById(doc, "expirydate", 0);

			// so expiry ll be less the actual expiry count
			int expiryCount = 5;
			int size = expiryList.size();
			if (expiryCount > size) {
				expiryCount--;
			}

			List<String> firstThreeExpiry = new ArrayList<String>();
			for (int i = 0; i < expiryCount; i++) {
				firstThreeExpiry.add(expiryList.get(i));
			}
			optionModles.setExpiry(firstThreeExpiry.get(0));
			optionModles.setExpiryList(firstThreeExpiry);

			String monthlyExpiry = USDINRUtils.getcurrentMOnthlyExpiry(firstThreeExpiry);
			String futureUrl = USDINRUtils.getUSDINRFutureURL(monthlyExpiry);

			Document futureDc = HTMLPageDocumentDownloader.getDocument(futureUrl);
			FuturePrice futurePrice = CommonHTMLDocParsher.getFuturePrice(futureDc);

			optionModles.setFuturePrice(futurePrice);
			optionModles.setSpot(futurePrice.getLastPrice());
			return optionModles;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR OptionUtils !" + e.getMessage() + "url " + url);
		}

	}

	// expiry can be null
	public static OptionModles getOptionChain(String url, String expiry) throws Exception {
		Document doc = HTMLPageDocumentDownloader.getDocument(url);
		try {
			Elements c = CommonHTMLDocParsher.getOptionChainTable(doc, "octable", 0);
			OptionModles columns = CommonHTMLDocParsher.parseNSEColumn(doc, c);

			List<String> expiryList = CommonHTMLDocParsher.getSelectBoxById(doc, "date", 0);
			List<String> firstExpirys = new ArrayList<>();
			for (int i = 0; i < 6; i++) {
				firstExpirys.add(expiryList.get(i));
			}
			if (expiry != null) {
				columns.setExpiry(expiry);
			} else {
				columns.setExpiry((String) firstExpirys.get(0));
			}
			columns.setExpiryList(firstExpirys);
			return columns;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR OptionUtils !" + e.getMessage());
		}
	}

	public static Map<String, List<StrategyModel>> getStrategy(String dir) {
		Map<String, List<StrategyModel>> strategy = new HashMap<>();

		List<String> lst = FileUtils.listFilesForFolder(new File(dir));
		for (String string : lst) {
			String file = dir + string;
			List<StrategyModel> str = getStrategyModel(file);

			strategy.put(string, str);
		}
		return strategy;
	}

	public static synchronized List<StrategyModel> getStrategyModel(String file) {
		List<StrategyModel> result = new ArrayList<>(5);

		List<String[]> lst = CommonUtils.getCSVData(file);
		for (String[] strings : lst) {
			StrategyModel strategyModel = new StrategyModel();
			strategyModel.setStrategySerial(strings[0]);
			strategyModel.setExpiry(strings[1]);
			if (strings[2].equals("CALL")) {
				strategyModel.setType(OptionType.CALL);
			} else {
				strategyModel.setType(OptionType.PUT);
			}
			strategyModel.setStrike(Double.parseDouble(strings[3]));
			strategyModel.setAvgPrice(Double.parseDouble(strings[4]));
			strategyModel.setQuantity(Integer.parseInt(strings[5]));
			strategyModel.setTarget(Double.parseDouble(strings[6]));
			strategyModel.setStopLoss(Double.parseDouble(strings[7]));
			strategyModel.setSpot_close(Double.parseDouble(strings[8]));
			strategyModel.setDes(strings[9]);
			strategyModel.setStatus(Boolean.parseBoolean(strings[10]));
			strategyModel.setTraded_IV(Double.parseDouble(strings[11]));
			strategyModel.setTradeDate(strings[12]);
			result.add(strategyModel);
		}
		return result;
	}
}
