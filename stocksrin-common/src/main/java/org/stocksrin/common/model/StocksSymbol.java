package org.stocksrin.common.model;

import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.utils.CommonUtils;

public class StocksSymbol {

	private String companyName;
	private String industry;
	private String symbol;

	public StocksSymbol() {

	}

	public StocksSymbol(String companyName, String industry, String symbol) {
		super();
		this.companyName = companyName;
		this.industry = industry;
		this.symbol = symbol;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return "StocksSymbole [companyName=" + companyName + ", industry=" + industry + ", symbol=" + symbol + "]";
	}

	public static List<StocksSymbol> getStocksSymbol(String csvFile) {
		List<StocksSymbol> lst = new ArrayList<>();
		List<String[]> data = CommonUtils.getCSVData(csvFile);
		for (String[] strings : data) {
			StocksSymbol s = new StocksSymbol(strings[0], strings[1], strings[2]);
			lst.add(s);
		}
		return lst;
	}
}
