package org.stocksrin.common.model.option;

public class FuturePrice {

	private String change;
	private String prevClose;
	private String percentageChange;
	private Double lastPrice;
	private String lowPrice;
	private String highPrice;
	private String expiryDate;
	
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getPrevClose() {
		return prevClose;
	}
	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
	}
	public String getPercentageChange() {
		return percentageChange;
	}
	public void setPercentageChange(String percentageChange) {
		this.percentageChange = percentageChange;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	@Override
	public String toString() {
		return "FuturePrice [change=" + change + ", prevClose=" + prevClose + ", percentageChange=" + percentageChange + ", lastPrice=" + lastPrice + ", lowPrice=" + lowPrice + ", highPrice="
				+ highPrice + ", expiryDate=" + expiryDate + "]";
	}
	

}
