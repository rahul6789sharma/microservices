package org.stocksrin.collector.option.data;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

public class LiveDataCollectortask extends TimerTask {
	private long timeInteval = 120000;
	private static final Logger log = LoggerFactory.getLogger(LiveDataCollectortask.class);

	public static void main(String[] args) {
		LiveDataCollectortask LiveDataCollectortask = new LiveDataCollectortask();
		LiveDataCollectortask.run();
	}

	@Override
	public void run() {
		log.info("******* LiveDataCollectortask Started**********");
		BankNiftyData.clear();
		try {
			PriceUtils.fetchData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment

			while (CommonUtils.getEveningTime()) {

				try {
					PriceUtils.fetchData();
					Thread.sleep(timeInteval);
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

}
