package org.stocksrin.common.model.trade;

import java.io.Serializable;

public class Trade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Instrument instrument;
	private TradeType tradeType;
	private String symbole;
	private String tradeTime;
	private String tradeDate;
	private String tradeDay;
	private String dataUpdatedAt;
	private int quantity;

	// underlyingPrice
	private double tradePrice;
	private double ltp;

	// define positive amount only
	// optional
	private double stopLoss;
	private double totalPL;

	private double ltp_min;
	private double ltp_max;
	private int minPNL;
	private int maxPNL;

	private boolean status = true;
	private String closeDate;
	private String closeTime;

	private OptionTrade optionTrade;
	private FutureTrade futureTrade;

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeDay() {
		return tradeDay;
	}

	public void setTradeDay(String tradeDay) {
		this.tradeDay = tradeDay;
	}

	public String getDataUpdatedAt() {
		return dataUpdatedAt;
	}

	public void setDataUpdatedAt(String dataUpdatedAt) {
		this.dataUpdatedAt = dataUpdatedAt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public double getLtp() {
		return ltp;
	}

	public void setLtp(double ltp) {
		this.ltp = ltp;
	}

	public double getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public double getTotalPL() {
		return totalPL;
	}

	public void setTotalPL(double totalPL) {
		this.totalPL = totalPL;
	}

	public double getLtp_min() {
		return ltp_min;
	}

	public void setLtp_min(double ltp_min) {
		this.ltp_min = ltp_min;
	}

	public double getLtp_max() {
		return ltp_max;
	}

	public void setLtp_max(double ltp_max) {
		this.ltp_max = ltp_max;
	}

	public int getMinPNL() {
		return minPNL;
	}

	public void setMinPNL(int minPNL) {
		this.minPNL = minPNL;
	}

	public int getMaxPNL() {
		return maxPNL;
	}

	public void setMaxPNL(int maxPNL) {
		this.maxPNL = maxPNL;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public OptionTrade getOptionTrade() {
		return optionTrade;
	}

	public void setOptionTrade(OptionTrade optionTrade) {
		this.optionTrade = optionTrade;
	}

	public FutureTrade getFutureTrade() {
		return futureTrade;
	}

	public void setFutureTrade(FutureTrade futureTrade) {
		this.futureTrade = futureTrade;
	}

	public Trade() {
		super();
	}

	public Trade(String symbole, TradeType tradeType) {
		super();
		this.symbole = symbole;
		this.tradeType = tradeType;
	}

	@Override
	public String toString() {
		return "Trade [tradeType=" + tradeType + ", symbole=" + symbole + ", tradeTime=" + tradeTime + ", tradeDate=" + tradeDate + ", tradeDay=" + tradeDay + ", dataUpdatedAt=" + dataUpdatedAt
				+ ", quantity=" + quantity + ", tradePrice=" + tradePrice + ", ltp=" + ltp + ", stopLoss=" + stopLoss + ", totalPL=" + totalPL + ", ltp_min=" + ltp_min + ", ltp_max=" + ltp_max
				+ ", minPNL=" + minPNL + ", maxPNL=" + maxPNL + ", status=" + status + ", closeDate=" + closeDate + ", closeTime=" + closeTime + ", optionTrade=" + optionTrade + ", futureTrade="
				+ futureTrade + "]";
	}

}
