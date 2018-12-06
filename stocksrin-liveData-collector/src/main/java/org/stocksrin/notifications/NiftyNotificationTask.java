package org.stocksrin.notifications;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

public class NiftyNotificationTask extends TimerTask {
	private static final Logger log = LoggerFactory.getLogger(NiftyNotificationTask.class);
	// 1 min
	private long timeInteval = 60000;

	private static double recordedPrice = 0;
	private static double alertPriceDiff = 25;

	@Override
	public void run() {
		log.info("BankNiftyNotificationTask Started");
		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {

				while (CommonUtils.getEveningTime()) {
					if (!NiftyData.shortedExpiry.isEmpty()) {
						String currentExpiry = NiftyData.shortedExpiry.first();
						checkSpotAlert(NiftyData.optionData.get(currentExpiry).getSpot());
					}
					Thread.sleep(timeInteval);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void checkSpotAlert(Double spot) {

		try {

			// LoggerSysOut.print("recordedPrice " + recordedPrice);
			// LoggerSysOut.print("spot " + spot);
			// log.info("Nifty recordedPrice " + recordedPrice);
			// log.info("Nifty spot " + spot);
			Double d = spot - recordedPrice;

			// log.info("diff " + d);
			if (recordedPrice == 0.0) {
				log.info("NiftyOpenning Price Alert");
				SendEmail.sentMail("Openning status [" + spot + "]", "Open [" + spot + "]", "LiveData-Collector");
				recordedPrice = spot;
				return;
			}

			if (Math.abs(d) > alertPriceDiff) {
				log.info("Nifty price Alart Triggered");
				SendEmail.sentMail("Nifty Price Alert : " + spot, "change [" + d.toString() + "]", "LiveData-Collector");
				recordedPrice = spot;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}