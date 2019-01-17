package org.stocksrin.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NSEHolidayUtils {
	private static List<String> holiday = new ArrayList<>();

	static {
		holiday.add("25-Dec-2018");
	}

	public static boolean isHoliday() {
		String formate = "dd-MMM-yyyy";
		try {
			String date = DateUtils.dateToString(new Date(), formate);
			return holiday.contains(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isHoliday());
	}
}
