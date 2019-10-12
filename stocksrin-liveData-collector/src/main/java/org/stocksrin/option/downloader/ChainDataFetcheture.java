package org.stocksrin.option.downloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TimerTask;

import org.springframework.stereotype.Controller;
import org.stocksrin.LiveDataCollectorApplication;
import org.stocksrin.collector.option.data.PriceUtils;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.InstrumentType;
import org.stocksrin.common.model.OptionChainOIData;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.ComparatorBasedOnDate;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.options.ExpiryUtils;

@Controller
public class ChainDataFetcheture extends TimerTask {

	/*
	 * private static List<String> expiries = Arrays.asList("30May2019", "6JUN2019",
	 * "13JUN2019", "20JUN2019", "27JUN2019", "4JUL2019");
	 */

	public static void main(String[] args) throws Exception {
		System.out.println(FileUtils.listdir(AppConstant.STOCKSRIN_OPTION_CHAIN_NIFTY));

		PriceUtils.fetchData();
		init(AppConstant.STOCKSRIN_OPTION_CHAIN_NIFTY, NiftyData.shortedExpiry, NiftyData.optionData, InstrumentType.NIFTY, NiftyData.oiData);

	}

	@Override
	public void run() {
		try {
			LiveDataCollectorApplication.appWait();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {

			init(AppConstant.STOCKSRIN_OPTION_CHAIN_NIFTY, NiftyData.shortedExpiry, NiftyData.optionData, InstrumentType.NIFTY, NiftyData.oiData);
		} catch (Exception e) {

			e.printStackTrace();
		}
		try {
			init(AppConstant.STOCKSRIN_OPTION_CHAIN_BNF, BankNiftyData.shortedExpiry, BankNiftyData.bnOptionData, InstrumentType.BNF, BankNiftyData.oiData);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void init(String path, SortedSet<String> shortedExpiry, Map<String, OptionModles> optionData, InstrumentType instrumentType, Map<String, List<OptionChainOIData>> oiData)
			throws Exception {

		List<String> expiries1 = new ArrayList<>(shortedExpiry);

		// create dir on every expire

		createDir(path, expiries1);

		List<String> dirs = FileUtils.listdir(path);
		Collections.sort(dirs, new ComparatorBasedOnDate());
		for (String string : shortedExpiry) {
			if (dirs.contains(string.toUpperCase())) {
				System.out.println("path : " + path + string);
				String fileName = FileUtils.listFilesForFolder(new File(path + string)).get(0);
				System.out.println("path : " + path + string + File.separator + fileName);
				String filePath = path + string + File.separator + fileName;

				// in memory already existing data
				List<OptionChainOIData> alreadyExistinglist = oiData.get(string);

				OptionModles optionModles = optionData.get(string);
				if (optionModles != null) {
					OptionChainOIData optionChainOIData = ExpiryUtils.convert(optionModles, instrumentType);
					String date = optionChainOIData.getDate();
					// check todays data is already downloaded or not
					if (alreadyExistinglist != null) {
						long count = alreadyExistinglist.stream().filter(i -> i.getDate().equalsIgnoreCase(date)).count();
						if (count == 0) {
							// data not downloaded

							FileUtils.appendData(optionChainOIData.toCSV(), filePath);
							alreadyExistinglist.add(optionChainOIData);
						} else {
							// already downloaded
							System.out.println("Data Already Downloaded");
						}
					} else {
						// todays expiry Data
						FileUtils.appendData(optionChainOIData.toCSV(), filePath);

						oiData.put(string, alreadyExistinglist);
					}

				}
			}
		}
	}

	public static void createDir(String basePath, List<String> sortedExpiry) throws Exception {

		if (ExpiryUtils.isTodayExpiry(sortedExpiry)) {
			if (ExpiryUtils.isMonthlyExpiry(sortedExpiry)) {
				ExpiryUtils.monthlyDirCreate(basePath, sortedExpiry);
			}
			// weekly Expire
			ExpiryUtils.weeklyDirCreate(basePath, sortedExpiry);
		} else {
			// check for dir

		}
	}
}