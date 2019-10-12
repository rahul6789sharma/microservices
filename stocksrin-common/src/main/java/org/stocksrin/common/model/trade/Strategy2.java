package org.stocksrin.common.model.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Strategy2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Trade> trades = new ArrayList<>();

	private String strategyName;

	private double tradeSpotPrice;
	private double spotLTP;

	private double totalPL;
	private double target;
	private double targetLoss;

	private double totalPLMin;
	private double totalPLMax;
	private String totalPLMinTime;
	private String totalPLMaxTime;
	private double totalPLMinSpot;
	private double totalPLMaxSpot;

	// time and its pnl
	private LinkedHashMap<String, Integer> pnlList = new LinkedHashMap<>();

	public Strategy2() {
		super();
	}

	public Strategy2(String strategyName) {
		super();
		this.strategyName = strategyName;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public double getTotalPL() {
		return totalPL;
	}

	public void setTotalPL(double totalPL) {
		this.totalPL = totalPL;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public double getTargetLoss() {
		return targetLoss;
	}

	public void setTargetLoss(double targetLoss) {
		this.targetLoss = targetLoss;
	}

	public double getTotalPLMin() {
		return totalPLMin;
	}

	public void setTotalPLMin(double totalPLMin) {
		this.totalPLMin = totalPLMin;
	}

	public double getTotalPLMax() {
		return totalPLMax;
	}

	public void setTotalPLMax(double totalPLMax) {
		this.totalPLMax = totalPLMax;
	}

	public String getTotalPLMinTime() {
		return totalPLMinTime;
	}

	public void setTotalPLMinTime(String totalPLMinTime) {
		this.totalPLMinTime = totalPLMinTime;
	}

	public String getTotalPLMaxTime() {
		return totalPLMaxTime;
	}

	public void setTotalPLMaxTime(String totalPLMaxTime) {
		this.totalPLMaxTime = totalPLMaxTime;
	}

	public double getTotalPLMinSpot() {
		return totalPLMinSpot;
	}

	public void setTotalPLMinSpot(double totalPLMinSpot) {
		this.totalPLMinSpot = totalPLMinSpot;
	}

	public double getTotalPLMaxSpot() {
		return totalPLMaxSpot;
	}

	public void setTotalPLMaxSpot(double totalPLMaxSpot) {
		this.totalPLMaxSpot = totalPLMaxSpot;
	}

	public double getTradeSpotPrice() {
		return tradeSpotPrice;
	}

	public void setTradeSpotPrice(double tradeSpotPrice) {
		this.tradeSpotPrice = tradeSpotPrice;
	}

	public double getSpotLTP() {
		return spotLTP;
	}

	public void setSpotLTP(double spotLTP) {
		this.spotLTP = spotLTP;
	}

	public LinkedHashMap<String, Integer> getPnlList() {
		return pnlList;
	}

	public void setPnlList(LinkedHashMap<String, Integer> pnlList) {
		this.pnlList = pnlList;
	}

	@Override
	public String toString() {
		return "Strategy2 [trades=" + trades + ", strategyName=" + strategyName + ", tradeSpotPrice=" + tradeSpotPrice + ", spotLTP=" + spotLTP + ", totalPL=" + totalPL + ", target=" + target
				+ ", targetLoss=" + targetLoss + ", totalPLMin=" + totalPLMin + ", totalPLMax=" + totalPLMax + ", totalPLMinTime=" + totalPLMinTime + ", totalPLMaxTime=" + totalPLMaxTime
				+ ", totalPLMinSpot=" + totalPLMinSpot + ", totalPLMaxSpot=" + totalPLMaxSpot + ", pnlList=" + pnlList + "]";
	}

}
