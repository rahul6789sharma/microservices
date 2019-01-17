package org.stocksrin.notifications;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.data.USDINRData;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

public class USDINRNotificationTask extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(USDINRNotificationTask.class);

	// 5 min
	private long timeInteval = 300000;
	// private long timeInteval = 10000;

	private static double recordedPrice = 0;
	private static double alertPriceDiff = 0.3;

	@Override
	public void run() {
		log.info("USDINRNotificationTask Started");
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			while (CommonUtils.getEveningTimeUSDINR()) {
				checkSpotAlert(USDINRData.getSpot());
				try {
					Thread.sleep(timeInteval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void checkSpotAlert(Double spot) {

		try {
			Double d = spot - recordedPrice;
			log.info("USDINR spot " + spot);
			log.info("USDINR recordedPrice " + spot);
			log.info("USDINR diff " + d);
			if (recordedPrice == 0.0) {
				log.info("USDINR Price Alert");
				SendEmail.sentMail("Openning status [" + spot + "]", "Open [" + spot + "]", "LiveData-Collector");
				recordedPrice = spot;
				return;
			}

			if (Math.abs(d) > alertPriceDiff) {
				log.info("USDINR Price Alart Triggered");
				SendEmail.sentMail("USDINR Price Alert : " + spot, "change [" + d.toString() + "]", "LiveData-Collector");
				recordedPrice = spot;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
