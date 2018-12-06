package org.stocksrin.common.data;

public class TradingDay {

	private TradingDay(){
		
	}
	
	private static boolean tradingDay;

	public static boolean isTradingDay() {
		return tradingDay;
	}

	public static void setTradingDay(boolean tradingDay) {
		TradingDay.tradingDay = tradingDay;
	}

}
