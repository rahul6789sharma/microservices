package org.stocksrin.common.model.intraday;

public class OHCL {

	private String symbole;
	private double open;
	private double high;
	private double close;
	private double low;

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

}
