package org.stocksrin.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public static void main(String[] args) {
		try {
			String day = getCurrentDay();
			int week=getCurrentWeek();

			System.out.println(day);
			System.out.println(week);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDayFromDate(String inputDate, String formate) throws Exception {
		SimpleDateFormat format1 = new SimpleDateFormat(formate);
		Date dt1 = format1.parse(inputDate);
		DateFormat format2 = new SimpleDateFormat("EEEE");
		return format2.format(dt1);
	}

	public static String getCurrentDay() {
		SimpleDateFormat format = new SimpleDateFormat("EEE");
		Calendar cal = Calendar.getInstance();
		cal.add(2, 0);
		return format.format(cal.getTime()).toUpperCase();
	}

	public static String getCurrentDatetwoDigit() {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		cal.add(2, 0);
		return format.format(cal.getTime()).toUpperCase();
	}

	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		cal.add(2, 0);
		return format.format(cal.getTime()).toUpperCase();
	}

	public static String getFullCurrentMonth() {
		SimpleDateFormat format = new SimpleDateFormat("MMMM");
		Calendar cal = Calendar.getInstance();
		cal.add(2, 0);
		return format.format(cal.getTime());
	}

	public static String getCurrentMonth() {
		SimpleDateFormat format = new SimpleDateFormat("MMM");
		Calendar cal = Calendar.getInstance();
		cal.add(2, 0);
		return format.format(cal.getTime());
	}

	public static String getCurrentYear() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(1, 0);
		return format.format(cal.getTime());
	}

	public static int getCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(4);
	}

	public static String getPreviousMonth(int previousMonth) {
		SimpleDateFormat format = new SimpleDateFormat("MMM");
		Calendar cal = Calendar.getInstance();
		cal.add(2, previousMonth);
		return format.format(cal.getTime());
	}

	public static int getWeekOfMonth(String expiry) throws Exception {
		String formate = "ddMMMyyyy";
		Date date = stringToDate(expiry, formate);

		String d = dateToString(date, "dd");

		Integer date1 = Integer.valueOf(Integer.parseInt(d));
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		now.set(5, date1.intValue());
		return now.get(4);
	}

	public static int getNumberOfWeekInMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(4);
	}

	public static String previousDayDate(String formate) {
		DateFormat dateFormat = new SimpleDateFormat(formate);
		return dateFormat.format(yesterday());
	}

	private static Date yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(5, -1);
		return cal.getTime();
	}

	public static boolean isWeekEndDay() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		if ((now.get(7) == 7) || (now.get(7) == 1)) {
			return true;
		}
		return false;
	}

	public static String dateToString(Date date, String formate) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formate);
			formatter.setTimeZone(TimeZone.getTimeZone("IST"));
			return formatter.format(date);
		} catch (Exception e) {
			throw new Exception("ERROR! dateToString " + e.getMessage());
		}
	}

	public static Date stringToDate(String sDate, String formate) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formate);
			formatter.setTimeZone(TimeZone.getTimeZone("IST"));
			return formatter.parse(sDate);
		} catch (Exception e) {
			throw new Exception("ERROR! StringToDate " + e.getMessage());
		}
	}

	public static String getDateOutofFileName(String fileName) {
		int last = fileName.lastIndexOf(".csv");
		return fileName.substring(25, last).trim();
	}

	public static Date getActualDateOutofFileName(String fileName) throws Exception {
		String stringDate = getDateOutofFileName(fileName);
		return stringToDate(stringDate, "dd_MM_yyyy");
	}

	public static String getTodayString() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));

		int i = now.get(7);
		if (i == 1) {
			return "SUNDAY";
		}
		if (i == 2) {
			return "MONDAY";
		}
		if (i == 3) {
			return "TUESDAY";
		}
		if (i == 4) {
			return "WEDNESDAY";
		}
		if (i == 5) {
			return "THURSDAY";
		}
		if (i == 6) {
			return "FRIDAY";
		}
		if (i == 7) {
			return "SATURDAY";
		}
		return null;
	}

	public static String getMonth(String s) throws Exception {
		int month = Integer.parseInt(s);
		if (month == 1) {
			return "Jan";
		}
		if (month == 2) {
			return "Feb";
		}
		if (month == 3) {
			return "Mar";
		}
		if (month == 4) {
			return "Apr";
		}
		if (month == 5) {
			return "May";
		}
		if (month == 6) {
			return "Jun";
		}
		if (month == 7) {
			return "Jul";
		}
		if (month == 8) {
			return "Aug";
		}
		if (month == 9) {
			return "Sep";
		}
		if (month == 10) {
			return "Oct";
		}
		if (month == 11) {
			return "Nov";
		}
		if (month == 12) {
			return "Dec";
		}
		throw new Exception("Error Month Out of range");
	}

	public static long getEpocTime(String dateString) throws ParseException {
		String str = dateString + " 15:30 UTC";
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm zzz");
		Date date = df.parse(str);
		long epoch = date.getTime();
		return epoch;
	}
}
