package org.stocksrin.common.utils.options;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.stocksrin.common.model.InstrumentType;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.trade.StrategyModel;
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

		System.out.println(data);
	}

	// strikediff=50 for nifty
	// strikediff =100 for bnf
	private static Double getATMStrikeV2(OptionModles optionModles, int strikediff) {
		Double atmStrike = 0.0;
		try {
			double spot = optionModles.getSpot();
			int round = round((int) spot, strikediff);
			atmStrike = getATM(optionModles, round, strikediff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return atmStrike;
	}

	private static Double getATM(OptionModles optionModles, int round, int symboleDiff) {
		try {
			int one = round - (symboleDiff * 2);
			int two = round - symboleDiff;
			int four = round + symboleDiff;
			int five = round + (symboleDiff * 2);

			Double oneStrike = null;
			Double twoStrike = null;
			Double threeStrike = null;
			Double foureStrike = null;
			Double fiveStrike = null;

			List<OptionModle> lst = optionModles.getOptionModle();
			List<Double> premiumDiff = new ArrayList<>();
			for (OptionModle optionModle : lst) {
				if (optionModle.getStrike_price() != null) {
					double value = optionModle.getStrike_price();
					if (value == one) {
						if (optionModle.getC_ltp() != null && optionModle.getP_ltp() != null) {
							double diff = Math.abs(optionModle.getC_ltp() - optionModle.getP_ltp());
							premiumDiff.add(diff);
							oneStrike = optionModle.getStrike_price();
						}

					} else if (value == two) {

						if (optionModle.getC_ltp() != null && optionModle.getP_ltp() != null) {
							double diff = Math.abs(optionModle.getC_ltp() - optionModle.getP_ltp());
							premiumDiff.add(diff);
							twoStrike = optionModle.getStrike_price();
						}

					} else if (value == round) {

						if (optionModle.getC_ltp() != null && optionModle.getP_ltp() != null) {
							double diff = Math.abs(optionModle.getC_ltp() - optionModle.getP_ltp());
							premiumDiff.add(diff);
							threeStrike = optionModle.getStrike_price();
						}

					} else if (value == four) {

						if (optionModle.getC_ltp() != null && optionModle.getP_ltp() != null) {
							double diff = Math.abs(optionModle.getC_ltp() - optionModle.getP_ltp());
							premiumDiff.add(diff);
							foureStrike = optionModle.getStrike_price();
						}

					} else if (value == five) {

						if (optionModle.getC_ltp() != null && optionModle.getP_ltp() != null) {
							double diff = Math.abs(optionModle.getC_ltp() - optionModle.getP_ltp());
							premiumDiff.add(diff);
							fiveStrike = optionModle.getStrike_price();
						}
						break;
					}
				}
			}
			if (!premiumDiff.isEmpty()) {
				List<Double> origianl = new ArrayList<>(premiumDiff);
				Collections.sort(premiumDiff);
				int index = origianl.indexOf(premiumDiff.get(0));
				if (index == 0) {
					return oneStrike;
				} else if (index == 1) {
					return twoStrike;
				} else if (index == 2) {
					return threeStrike;
				} else if (index == 3) {
					return foureStrike;
				} else if (index == 4) {
					return fiveStrike;
				}
			}
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
		return null;
	}

	// function to round the number
	private static int round(int n, int multiple) {
		// Smaller multiple
		int a = (n / multiple) * multiple;

		// Larger multiple
		int b = a + multiple;

		// Return of closest of two
		return (n - a > b - n) ? b : a;
	}

	public static void futurePrice(String url) {
		Document doc = HTMLPageDocumentDownloader.getDocument(url);
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
			// FuturePrice futurePrice = CommonHTMLDocParsher.getFuturePrice(futureDc);

			// optionModles.setFuturePrice(futurePrice);
			// optionModles.setSpot(futurePrice.getLastPrice());
			return optionModles;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR OptionUtils !" + e.getMessage() + "url " + url);
		}

	}

	// expiry can be null
	public static OptionModles getOptionChain(String url, String expiry, InstrumentType instrumentType, String symbole) throws Exception {
		Document doc = HTMLPageDocumentDownloader.getDocument(url);
		try {
			Elements c = CommonHTMLDocParsher.getOptionChainTable(doc, "octable", 0);
			OptionModles columns = CommonHTMLDocParsher.parseNSEColumn(doc, c, symbole);

			List<String> expiryList = CommonHTMLDocParsher.getSelectBoxById(doc, "date", 0);
			List<String> firstExpirys = new ArrayList<>();
			int expiryCount = 6;
			if (InstrumentType.OTHER.equals(instrumentType)) {
				expiryCount = expiryList.size();
			}

			for (int i = 0; i < expiryCount; i++) {
				firstExpirys.add(expiryList.get(i));
			}
			if (expiry != null) {
				columns.setExpiry(expiry);
			} else {
				columns.setExpiry((String) firstExpirys.get(0));
			}
			columns.setExpiryList(firstExpirys);
			initATMandIV(columns, instrumentType);
			return columns;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR OptionUtils !" + e.getMessage());
		}
	}

	private static void initATMandIV(OptionModles optionModles, InstrumentType instrumentType) {
		Double atm = 0.0;
		if (instrumentType.equals(InstrumentType.NIFTY)) {
			atm = OptionUtils.getATMStrikeV2(optionModles, 50);
		} else if (instrumentType.equals(InstrumentType.BNF)) {
			atm = OptionUtils.getATMStrikeV2(optionModles, 100);
		}
		if (atm != null) {
			optionModles.setAtmStrike(atm);
		}

		List<OptionModle> lst = optionModles.getOptionModle();
		double iv = 0.0;
		for (OptionModle optionModle : lst) {
			if (optionModle.getStrike_price() == atm) {

				if (optionModle.getP_iv() != null) {
					optionModles.setPe_atm_iv(optionModle.getP_iv());
				} else {
					optionModles.setPe_atm_iv(0.0);
				}

				if (optionModle.getC_iv() != null) {
					optionModles.setCe_atm_iv(optionModle.getC_iv());
				} else {
					optionModles.setCe_atm_iv(0.0);
				}

				iv = (double) (optionModles.getCe_atm_iv() + optionModles.getPe_atm_iv()) / 2;
			}
		}
		optionModles.setIv(iv);
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
