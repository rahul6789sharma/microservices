package org.stocksrin.notifications;

import java.util.SortedSet;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.collector.option.data.utils.Calculation;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.model.option.MaxPains;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

public class BankNiftyNotificationTask extends TimerTask {
	private static final Logger log = LoggerFactory.getLogger(BankNiftyNotificationTask.class);
	// 1 min
	private long timeInteval = 60000;

	// private long timeInteval = 10000;

	private static double recordedPrice = 0;
	private static double alertPriceDiff = 50;

	private static int oiLimit = 1000000;

	@Override
	public void run() {

		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {

				while (CommonUtils.getEveningTime()) {
					if (!BankNiftyData.shortedExpiry.isEmpty()) {
						/*String currentExpiry = BankNiftyData.shortedExpiry.first();

						checkSpotAlert(BankNiftyData.bnOptionData.get(currentExpiry).getSpot());

						checkForMaxPainChange();*/
						// BankNiftyOINotification.checkOIChange();
					}

					Thread.sleep(timeInteval);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void checkForMaxPainChange() {
		try {
			SortedSet<String> expiryes = BankNiftyData.shortedExpiry;
			for (String expiry : expiryes) {
				OptionModles optionModles = BankNiftyData.bnOptionData.get(expiry);

				// only check when total oi is greater then 1 lacks
				if (optionModles.getTotal_ce_oi() > oiLimit || optionModles.getTotal_pe_oi() > oiLimit) {

					MaxPains maxPain = Calculation.calMaxPain(optionModles.getOptionModle(), AppConstant.BNF_STRIKE_DIFF, optionModles.getExpiry());

					Double currentMaxPain = maxPain.getMaxPainStrick();

					Double previousMaxPain = BankNiftyData.maxPains.get(expiry);

					if (previousMaxPain != null) {

						if (!previousMaxPain.equals(currentMaxPain)) {
							log.info(" *************** ******************* ***************** *************** ");
							log.info("Bank Nifty MaxPain Alert Triggered");
							SendEmail.sentMail("MS:BN MaxPain Changed Expiry: " + expiry, previousMaxPain + "-->" + currentMaxPain, "Strategies-BUilder");
							BankNiftyData.maxPains.put(expiry, currentMaxPain);
						}
					} else {
						BankNiftyData.maxPains.put(expiry, currentMaxPain);
					}
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

			Double d = spot - recordedPrice;
			log.info("BNF diff " + d);
			// LoggerSysOut.print("diff " + d);
			if (recordedPrice == 0.0) {
				log.info("Bank NiftyOpenning price Alert");
				SendEmail.sentMail("BN Openning status [" + spot + "]", "Open [" + spot + "]", "Strategies-BUilder");
				recordedPrice = spot;
				return;
			}

			if (Math.abs(d) > alertPriceDiff) {
				log.info("Bank Nifty price Alart Triggered");
				SendEmail.sentMail("BN Price Alert : " + spot, "change [" + d.toString() + "]", "Strategies-BUilder");
				recordedPrice = spot;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}