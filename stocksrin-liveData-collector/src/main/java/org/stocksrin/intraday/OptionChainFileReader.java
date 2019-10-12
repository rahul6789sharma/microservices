package org.stocksrin.intraday;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.FileUtils;

public class OptionChainFileReader {

	public static void main(String[] args) throws Exception {

		List<OptionModles> o = getNiftyOptionChainExpiryIntraday("10OCT2019", "10_Oct_2019");
		System.out.println(o.size());

	}

	// date formate=10_Oct_2019
	// atm + 400 and -400 strickes only
	public static List<OptionModles> getNiftyOptionChainExpiryIntraday(String expiry, String date) throws Exception {
		List<OptionModles> result = new ArrayList<>();
		String path = AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY_INTRDAY_EXPIRY + expiry;
		List<String> files = FileUtils.listFilesForFolder(path);
		Comparator<String> byName = (String o1, String o2) -> {
			int pos = o1.lastIndexOf("_");

			String result1 = o1.substring(pos);
			String result2 = o2.substring(pos);
			String i = result1.replace("_", "").replace(".json", "");
			String j = result2.replace("_", "").replace(".json", "");
			Integer a = Integer.parseInt(i);
			Integer b = Integer.parseInt(j);
			return a.compareTo(b);
		};

		SortedSet<String> sortedFiles = new TreeSet<>(byName);
		for (String string2 : files) {

			if (string2.contains(date)) {
				sortedFiles.add(string2);

			}
		}

		for (String string : sortedFiles) {
			OptionModles o = FileUtils.fromJson2(path + File.separator + string);
			List<OptionModle> optionModle = o.getOptionModle();
			Double atm = o.getAtmStrike();
			Double lowerStrike = atm - 400;
			Double upper = atm + 400;
			List<OptionModle> optionModleResult = new ArrayList<>();
			for (OptionModle po : optionModle) {
				if (po.getStrike_price() > lowerStrike && po.getStrike_price() < upper) {
					optionModleResult.add(po);
				}
			}
			o.getOptionModle().clear();
			o.getOptionModle().addAll(optionModleResult);
			result.add(o);
		}
		return result;
	}

	public static List<OptionModles> getNiftyOptionChainByExpiry(String expiry) throws Exception {

		List<OptionModles> result = new ArrayList<>();
		List<String> files = FileUtils.listFilesForFolder(AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY + expiry);
		if (files == null) {
			return null;
		}
		for (String string2 : files) {
			OptionModles o = FileUtils.fromJson2(AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY + expiry + File.separator + string2);
			List<OptionModle> optionModle = o.getOptionModle();
			Double atm = o.getAtmStrike();
			Double lowerStrike = atm - 1000;
			Double upper = atm + 1000;
			List<OptionModle> optionModleResult = new ArrayList<>();
			for (OptionModle po : optionModle) {
				if (po.getStrike_price() > lowerStrike && po.getStrike_price() < upper) {
					optionModleResult.add(po);
				}
			}
			o.getOptionModle().clear();
			o.getOptionModle().addAll(optionModleResult);
			result.add(o);
		}
		return result;
	}
}
