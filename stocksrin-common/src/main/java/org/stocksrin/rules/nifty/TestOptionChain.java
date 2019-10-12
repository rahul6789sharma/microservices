package org.stocksrin.rules.nifty;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;

public class TestOptionChain {

	static String currentExpiry = "29AUG2019";
	static StringBuilder result;
	// static String dir = AppConstant.STOCKSRIN_OPTION_DATA_DIR_STOCKS;

	public static String analysis(String dir) throws Exception {
		result = new StringBuilder();
		List<String> lst = FileUtils.listdir(dir);

		for (String string : lst) {

			List<String> dates = FileUtils.listFilesForFolder(dir + string + File.separator + currentExpiry);

			SortedSet<Date> datesData = new TreeSet<>();
			for (String string2 : dates) {

				datesData.add(DateUtils.stringToDate(string2.split(".json")[0], "dd_MMM_yyyy"));
			}
			if (FileUtils.isFileExits(dir + string + File.separator + currentExpiry) && !datesData.isEmpty()) {
				String lastUpdatedfile = dir + string + File.separator + currentExpiry + File.separator
						+ DateUtils.dateToString(datesData.last(), "dd_MMM_yyyy");

				OptionModles optionModles = FileUtils.fromJson2(lastUpdatedfile + ".json");

				test(optionModles, string);
			}
		}
		result.append("OI Valid Stocks Count: " + count);
		result.append("\n");
		result.append("Bull Count: " + bulish);
		result.append("\n");
		result.append("Bullish Stocks " + bullishStocks);
		result.append("\n");
		result.append("Bearish stocks Count:  " + bearish);
		result.append("\n");
		result.append("Bearish Stocks " + bearishStocks);
		result.append("\n");
		result.append(ceShoker);
		result.append("\n");
		result.append(peShoker);
		System.out.println(result);
		return result.toString();
	}

	public static void main(String[] args) throws Exception {
		// System.out.println();

	}

	private static void test(OptionModles optionModles, String symbole) {
		if (oiCountValid(optionModles)) {
			System.out.println("----------------------------");
			bulish(optionModles, symbole);
			oiShoker(optionModles, symbole);
			System.out.println("----------------------------");
		}
	}

	static int count;
	static int bulish;
	static int bearish;
	static List<String> bullishStocks = new ArrayList<>();
	static List<String> bearishStocks = new ArrayList<>();
	static Map<String, Double> ceShoker = new HashMap<>();
	static Map<String, Double> peShoker = new HashMap<>();

	private static boolean oiCountValid(OptionModles optionModles) {
		if (optionModles.getTotal_ce_oi() + optionModles.getTotal_pe_oi() > 2000000) {
			count++;
			return true;

		} else {
			return false;
		}
	}

	private static boolean bulish(OptionModles optionModles, String symbole) {
		if (optionModles.getTotal_ce_oi() < optionModles.getTotal_pe_oi() + 200000) {
			bulish++;
			bullishStocks.add(symbole);
			return true;
		} else {
			bearishStocks.add(symbole);
			bearish++;
			return false;
		}
	}

	private static void oiShoker(OptionModles optionModles, String symbole) {
		List<OptionModle> data = optionModles.getOptionModle();
		for (OptionModle optionModle : data) {
			if (optionModle.getC_change_oi() != null && optionModle.getC_change_oi() > 1000000) {
				ceShoker.put(symbole, optionModle.getStrike_price());
			}
			if (optionModle.getP_change_oi() != null && optionModle.getP_change_oi() > 1000000) {
				peShoker.put(symbole, optionModle.getStrike_price());
			}
		}

	}

}
