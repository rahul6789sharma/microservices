package org.stocksrin.intraday;

import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.stocksrin.collector.option.data.PriceUtils;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;

@Controller
public class OptionChainDownloader extends TimerTask {

	private long timeInteval5min = 300000; // 5 min
	private long timeInteval10min = 600000; // 10 min
	private static final Logger log = LoggerFactory.getLogger(OptionChainDownloader.class);

	public static void main(String[] args) throws Exception {
		PriceUtils.fetchData();
		OptionChainDownloader optionChainDownloader = new OptionChainDownloader();
		optionChainDownloader.run();
	}

	@Override
	public void run() {
		log.info("******* OptionChainDownloader Started **********");
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {

			new Thread(() -> downloadNifty(timeInteval10min)).start();
			new Thread(() -> downloadBNF(timeInteval10min)).start();
		}

	}

	private void downloadNifty(long sleeTimeInteval) {
		int count = 1;
		while (CommonUtils.getEveningTime()) {

			try {
				if (!NiftyData.shortedExpiry.isEmpty()) {
					String currentExpiry = NiftyData.shortedExpiry.first();
					String nextExpiry = new ArrayList<>(NiftyData.shortedExpiry).get(1);
					OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
					downloadNifty(optionModles, AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY_INTRDAY_EXPIRY, currentExpiry, count);
					OptionModles optionModlesNext = NiftyData.optionData.get(nextExpiry);
					downloadNifty(optionModlesNext, AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY_INTRDAY_EXPIRY, nextExpiry, count);
					count++;
					Thread.sleep(sleeTimeInteval);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void downloadBNF(long sleeTimeInteval) {
		int count = 1;
		while (CommonUtils.getEveningTime()) {

			try {
				if (!BankNiftyData.shortedExpiry.isEmpty()) {
					String currentExpiry = BankNiftyData.shortedExpiry.first();
					String nextExpiry = new ArrayList<>(NiftyData.shortedExpiry).get(1);
					OptionModles optionModles = BankNiftyData.bnOptionData.get(currentExpiry);
					downloadNifty(optionModles, AppConstant.STOCKSRIN_OPTION_DATA_DIR_BNF_INTRDAY_EXPIRY, currentExpiry, count);
					OptionModles optionModlesNext = BankNiftyData.bnOptionData.get(nextExpiry);
					downloadNifty(optionModlesNext, AppConstant.STOCKSRIN_OPTION_DATA_DIR_BNF_INTRDAY_EXPIRY, nextExpiry, count);
					count++;
					Thread.sleep(sleeTimeInteval);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private OptionModles downloadNifty(OptionModles optionModles, String path, String expiry, int count) {

		boolean makedirstatus = FileUtils.makeDir(path + expiry);

		if (makedirstatus) {
			log.info("New Expiry Start " + expiry);
		}

		// OptionModles optionModles = niftyClient.getOptionModel(firstExpiry);
		String date = optionModles.getDate() + "_" + count;
		String optionFileDir = path + expiry + File.separator + date + ".json";

		boolean isFileExist = FileUtils.isFileExits(optionFileDir);
		log.info(optionFileDir + ", isFileExist : " + isFileExist);

		if (!isFileExist) {
			FileUtils.writeDataAsJson(optionModles, optionFileDir);
			log.info("Downloaded " + optionFileDir);
		} else {
			log.info("already data downloaded , " + optionFileDir);
		}
		return optionModles;
	}

}
