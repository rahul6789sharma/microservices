package org.stocksrin.common.utils.options;

import java.util.Arrays;
import java.util.List;

public class ExpiryDirUtils {

	private static List<String> expiries = Arrays.asList("6JUN2019", "13JUN2019", "20JUN2019", "27JUN2019", "4JUL2019",
			"11JUL2019");

	public static void main(String[] args) throws Exception {
		System.out.println(getCurrentMonthExpiry(expiries));
	}

	public static String getCurrentMonthExpiry(List<String> expiries) {
		String currentExpiryMonth = expiries.get(0).replaceAll("[^A-Za-z]", "");
		System.out.println(currentExpiryMonth);
		long count = expiries.stream().filter(i -> i.contains(currentExpiryMonth)).count();
		System.out.println(count);
		return expiries.get((int) count-1);
	}

	public static void createDir(String basePath, List<String> sortedExpiry) throws Exception {

		if (ExpiryUtils.isTodayExpiry(sortedExpiry)) {
			if (ExpiryUtils.isMonthlyExpiry(sortedExpiry)) {
				ExpiryUtils.monthlyDirCreate(basePath, sortedExpiry);
			}
			// weekly Expire
			ExpiryUtils.weeklyDirCreate(basePath, sortedExpiry);
		} else {
			// check for dir

		}
	}
}
