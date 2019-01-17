package org.stocksrin.collector.option.data;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.data.USDINRData;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

@Controller
public class LiveUSDINRDataCollector extends TimerTask {

	// 5 min
	private long timeInteval = 300000;
	//private long timeInteval = 10000;
	private static final Logger log = LoggerFactory.getLogger(LiveDataCollectortask.class);

	@Override
	public void run() {
		log.info("******* LiveUSDINRDataCollector Started**********");
		USDINRData.clear();
		try {
			USDINRPriceLoader.fetchData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment

			while (CommonUtils.getEveningTimeUSDINR()) {

				try {
					USDINRPriceLoader.fetchData();
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
