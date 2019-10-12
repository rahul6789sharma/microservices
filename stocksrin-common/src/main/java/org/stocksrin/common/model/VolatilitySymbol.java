package org.stocksrin.common.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;

public class VolatilitySymbol {

	private String date;
	private String symbol;
	private double closePrice;
	private double previousDayClosePrice;
	private double previousDayVolatility;
	private double volatility;

	private StocksSymbol stocksSymbol;

	public VolatilitySymbol(String date, String symbol, double closePrice, double previousDayClosePrice,
			double previousDayVolatility, double volatility) {
		super();
		this.date = date;
		this.symbol = symbol;
		this.closePrice = closePrice;
		this.previousDayClosePrice = previousDayClosePrice;
		this.previousDayVolatility = previousDayVolatility;
		this.volatility = volatility;
	}

	public StocksSymbol getStocksSymbol() {
		return stocksSymbol;
	}

	public void setStocksSymbol(StocksSymbol stocksSymbol) {
		this.stocksSymbol = stocksSymbol;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getPreviousDayClosePrice() {
		return previousDayClosePrice;
	}

	public void setPreviousDayClosePrice(double previousDayClosePrice) {
		this.previousDayClosePrice = previousDayClosePrice;
	}

	public double getPreviousDayVolatility() {
		return previousDayVolatility;
	}

	public void setPreviousDayVolatility(double previousDayVolatility) {
		this.previousDayVolatility = previousDayVolatility;
	}

	public Double getVolatility() {
		return volatility;
	}

	public void setVolatility(double volatility) {
		this.volatility = volatility;
	}

	@Override
	public String toString() {
		return "VolatilitySymbol [date=" + date + ", symbol=" + symbol + ", closePrice=" + closePrice
				+ ", previousDayClosePrice=" + previousDayClosePrice + ", previousDayVolatility="
				+ previousDayVolatility + ", volatility=" + volatility + ", stocksSymbol=" + stocksSymbol + "]";
	}

	public static List<VolatilitySymbol> getVolatilitySymbole() throws Exception {
		List<VolatilitySymbol> lst = new ArrayList<>();
		List<StocksSymbol> nifty100 = StocksSymbol.getStocksSymbol(AppConstant.NIFTY100_LIST);
		String date = DateUtils.dateToString(new Date(), "ddMMMyyyy");
		String fileName = "CMVOLT_" + date + ".csv";
		String fullFilename = AppConstant.REPORTS_VOLATILITY + File.separator + fileName;
		List<String[]> data = CommonUtils.getCSVData(fullFilename);

		for (String[] strings : data) {
			for (StocksSymbol strings2 : nifty100) {
				if (strings[1].equals(strings2.getSymbol())) {
					VolatilitySymbol volatilitySymbol = new VolatilitySymbol(strings[0], strings[1],
							Double.parseDouble(strings[2]), Double.parseDouble(strings[3]),
							Double.parseDouble(strings[4]), Double.parseDouble(strings[5]));
					volatilitySymbol.setStocksSymbol(strings2);
					lst.add(volatilitySymbol);
				}
			}
		}
		return lst;
	}

	public static List<VolatilitySymbol> sortedByDayIV(List<VolatilitySymbol> lst) {

		Collections.sort(lst, (item1, item2) -> item2.getVolatility().compareTo(item1.getVolatility()));
		return lst;
	}

	public static List<VolatilitySymbol> fileter(List<VolatilitySymbol> lst, Double ivCount) {
		List<VolatilitySymbol> result = new ArrayList<>();
		for (VolatilitySymbol volatilitySymbol : lst) {
			
			if ((volatilitySymbol.getVolatility() * 100) >= ivCount) {
				result.add(volatilitySymbol);
			}
		}
		return result;

	}
}
