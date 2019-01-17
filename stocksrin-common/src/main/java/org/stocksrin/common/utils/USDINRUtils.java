package org.stocksrin.common.utils;

import java.util.List;

public class USDINRUtils {

	private final static String NSE_FUTURE_URL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteCID.jsp?underlying=USDINR&instrument=FUTCUR&expiry=";

	public static String getUSDINRFutureURL(String expiryDate) {
		String url = NSE_FUTURE_URL + expiryDate + "&key=FUTCURUSDINR" + expiryDate + "--" + DateUtils.getTodayDate();
		return url;
	}

	public static String getcurrentMOnthlyExpiry(List<String> monthlyExpiry) {
		String currentMOnth = DateUtils.getCurrentMonth();

		int i = 0;
		for (String string : monthlyExpiry) {

			if (string.contains(currentMOnth.toUpperCase())) {

				i++;
			}
		}

		return monthlyExpiry.get(i - 1);
	}
}
