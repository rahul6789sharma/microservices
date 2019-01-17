package org.stocksrin.strategies.common.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TimeZone;

import org.stocksrin.common.utils.DateUtils;

public class BNFStrategyUtils extends StrategyUtils {

	public static String getExpiry(SortedSet<String> shortedExpiry) throws Exception {

		Calendar time = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		int hr = time.get(Calendar.HOUR);
		if (hr < 14) {
			return shortedExpiry.first();
		} else {
			String currentExpiry = shortedExpiry.first();
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

	public static String getNextExpiry(SortedSet<String> shortedExpiry) throws Exception {

		List<String> expiryList = new ArrayList<>(shortedExpiry);
		return expiryList.get(1);

	}

}
