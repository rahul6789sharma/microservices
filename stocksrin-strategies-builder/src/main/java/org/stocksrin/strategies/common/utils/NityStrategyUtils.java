package org.stocksrin.strategies.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TimeZone;

import org.stocksrin.common.utils.DateUtils;

public class NityStrategyUtils extends StrategyUtils {

	
	public static String getNiftyExpiryForDayTrading(SortedSet<String> shortedExpiry, String currentExpiry) throws Exception {

		Calendar time = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		int hr = time.get(Calendar.HOUR);
		if (hr < 14) {
			return currentExpiry;
		} else {
			String today = DateUtils.dateToString(new Date(), "ddMMMyyyy");
			if (currentExpiry.equalsIgnoreCase(today)) {
				System.out.println("Expiry Day Moving to new Expiry");
				int i = 0;
				for (String item : shortedExpiry) {
					if (i == 1) {
						currentExpiry = item;
						break;
					}
					i++;
				}
			}
			return currentExpiry;
		}

	}

	




}
