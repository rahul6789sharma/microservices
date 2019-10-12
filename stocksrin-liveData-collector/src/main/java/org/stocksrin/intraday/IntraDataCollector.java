package org.stocksrin.intraday;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.IntraDayData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.intraday.IntraDayOptionModles;
import org.stocksrin.common.model.intraday.OHCL;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

// intraday option Chain Data Collector
@Controller
public class IntraDataCollector extends TimerTask {

	private long timeInteval5min = 300000; // 5 min
	private long timeInteval10min = 600000; // 10 min
	private long timeInteval15min = 1800000; // 30 min
	private static final Logger log = LoggerFactory.getLogger(IntraDataCollector.class);

	@Override
	public void run() {
		log.info("******* IntraDataCollector Started **********");

		Runnable task5min = () -> {
			processNifty(IntraDayData.niftyOptionDataIntraDay5Min, timeInteval5min);
		};

		Runnable task10min = () -> {
			processNifty(IntraDayData.niftyOptionDataIntraDay10Min, timeInteval10min);
		};

		Runnable task15min = () -> {
			processNifty(IntraDayData.niftyOptionDataIntraDay15Min, timeInteval15min);
		};

		// BNF Threads

		Runnable task5minBNF = () -> {
			processBNF(IntraDayData.bnfOptionDataIntraDay5Min, timeInteval5min);
		};

		Runnable task10minBNF = () -> {
			processBNF(IntraDayData.bnfOptionDataIntraDay10Min, timeInteval10min);
		};

		Runnable task15minBNF = () -> {
			processBNF(IntraDayData.bnfOptionDataIntraDay15Min, timeInteval15min);
		};

		new Thread(task5min).start();
		new Thread(task10min).start();
		new Thread(task15min).start();

		new Thread(task5minBNF).start();
		new Thread(task10minBNF).start();
		new Thread(task15minBNF).start();
	}

	private void processBNF(Map<Integer, IntraDayOptionModles> data, long sleeTimeInteval) {

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment
			int count = 1;
			data.clear();
			getBNFData(data, count);
			while (CommonUtils.getEveningTime()) {

				try {
					log.info("IntraDay Data Collection for BNF " + count);
					count++;
					getBNFData(data, count);
					Thread.sleep(sleeTimeInteval);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("its off today");
			if (NSEHolidayUtils.isHoliday()) {
				SendEmail.sentMail("Market is closed Today", "Take rest", "Live Data Collector");
			}

		}
	}

	private void getBNFData(Map<Integer, IntraDayOptionModles> data, int count) {
		try {
			if (!BankNiftyData.shortedExpiry.isEmpty()) {
				String currentMOnthExpiry = BankNiftyData.shortedExpiry.first();
				OptionModles optionModles = BankNiftyData.bnOptionData.get(currentMOnthExpiry);

				IntraDayOptionModles obj = new IntraDayOptionModles();
				obj.setOptionModles(optionModles);
				obj.setAllAdvanceDecline(NiftyData.advanceDecline);
				OHCL previousDayohcl = getPreviousDayData(AppConstant.STOCKSRIN_INDICES_BANK_NIFTY);
				obj.setPreviousDayohcl(previousDayohcl);
				data.put(count, obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processNifty(Map<Integer, IntraDayOptionModles> data, long sleeTimeInteval) {

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment
			int count = 1;
			data.clear();

			getNiftyData(data, count);
			while (CommonUtils.getEveningTime()) {

				try {
					log.info("IntraDay Nifty Data collecting " + count);

					count++;
					getNiftyData(data, count);
					Thread.sleep(sleeTimeInteval);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("its off today");
			if (NSEHolidayUtils.isHoliday()) {
				SendEmail.sentMail("Market is closed Today", "Take rest", "Live Data Collector");
			}

		}
	}

	private void getNiftyData(Map<Integer, IntraDayOptionModles> data, int count) {
		try {
			if (!NiftyData.shortedExpiry.isEmpty()) {
				String currentMOnthExpiry = NiftyData.shortedExpiry.first();
				OptionModles optionModles = NiftyData.optionData.get(currentMOnthExpiry);
				IntraDayOptionModles obj = new IntraDayOptionModles();
				obj.setOptionModles(optionModles);
				obj.setAllAdvanceDecline(NiftyData.advanceDecline);
				OHCL previousDayohcl = getPreviousDayData(AppConstant.STOCKSRIN_INDICES_NIFTY);
				obj.setPreviousDayohcl(previousDayohcl);
				data.put(count, obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// need to be corrected
	public static OHCL getPreviousDayData(String file) {
		OHCL ohcl = new OHCL();

		BufferedReader br = null;
		try {
			String sCurrentLine;
			String lastLine = "";
			br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				lastLine = sCurrentLine;
			}

			ohcl.setClose(Double.parseDouble(lastLine.substring(24, lastLine.length())));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return ohcl;

	}

	public static void main(String[] args) {
		getPreviousDayData(AppConstant.STOCKSRIN_INDICES_NIFTY);
	}
}
