package org.stocksrin;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.stocksrin.LiveDataCollectorApplication;
import org.stocksrin.collector.option.data.PriceUtils;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.OptionChainOIData;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.participant.oidata.Util;

public class DataLoaderOnStartUP {

//	private static List<String> expiries = Arrays.asList("6JUN2019", "13JUN2019", "20JUN2019", "27JUN2019", "4JUL2019");

	public static void main(String[] args) throws Exception {
		PriceUtils.fetchData();
		loadOIData();
	}

	public static void loadOIData() {
		try {
			LiveDataCollectorApplication.appWait();
			// PriceUtils.fetchData();

			SortedSet<String> shortedExpiry = NiftyData.shortedExpiry;

			List<String> expiries1 = new ArrayList<>(shortedExpiry);
			loadData(expiries1, AppConstant.STOCKSRIN_OPTION_CHAIN_NIFTY, NiftyData.oiData);
			loadData(expiries1, AppConstant.STOCKSRIN_OPTION_CHAIN_BNF, BankNiftyData.oiData);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void loadData(List<String> expiries, String dirpath, Map<String, List<OptionChainOIData>> oiData) {
		for (String string : expiries) {
			String path = dirpath + string;
			File f = new File(path);
			if (f.exists()) {
				String fileName = FileUtils.listFilesForFolder(f).get(0);
				System.out.println(path + File.separator + fileName);
				List<OptionChainOIData> data = OptionChainOIData.getData(path + File.separator + fileName);
				System.out.println(data);
				oiData.put(string, data);
				data.forEach(i -> System.out.println(i));
				System.out.println("***************");
			}
		}
	}

	public static void loadParticipantOIDataOn() {
		try {

			String date2 = DateUtils.dateToString(new Date(), "MMMyyyy");
			String dir = AppConstant.FO_OI_DIR + date2;

			List<String> files = FileUtils.listFilesForFolder(new File(dir));
			System.out.println(files);
			if (files != null) {
				for (String string : files) {
					Util.collectAllDateForDay(dir + File.separator + string);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
