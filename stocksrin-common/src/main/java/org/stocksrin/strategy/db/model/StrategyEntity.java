package org.stocksrin.strategy.db.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "STRATEGY")
public class StrategyEntity {

	@Id
	private String name; // strategyName

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "strategyEntity", fetch = FetchType.EAGER)
	private List<TradeEntity> trades;

	// private double tradedPriceSpot;
	private double tradedPrice;
	private double ltp;
	private String tradedTime;
	private String tradedDate;
	private String tradedDay;
	private String symbole;

	private double totalPL;
	private double target;
	private double targetLoss;

	private double totalPLMin;
	private double totalPLMax;
	private String totalPLMinTime;
	private String totalPLMaxTime;

	private double totalPLMinSpot;
	private double totalPLMaxSpot;

	@Enumerated(EnumType.STRING)
	private TradeHoldingTime strategyHoldingTime;

	public StrategyEntity() {

	}

	public StrategyEntity(String name, String symbole, TradeHoldingTime strategyHoldingTime, String tradedDate) {
		super();
		this.name = name;
		this.symbole = symbole;
		this.strategyHoldingTime = strategyHoldingTime;
		this.tradedDate = tradedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<TradeEntity> getTrades() {
		return trades;
	}

	public void setTrades(List<TradeEntity> trades) {
		this.trades = trades;
	}

	public TradeHoldingTime getStrategyHoldingTime() {
		return strategyHoldingTime;
	}

	public void setStrategyHoldingTime(TradeHoldingTime strategyHoldingTime) {
		this.strategyHoldingTime = strategyHoldingTime;
	}

	public double getTradedPrice() {
		return tradedPrice;
	}

	public void setTradedPrice(double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}

	public String getTradedTime() {
		return tradedTime;
	}

	public void setTradedTime(String tradedTime) {
		this.tradedTime = tradedTime;
	}

	public String getTradedDate() {
		return tradedDate;
	}

	public void setTradedDate(String tradedDate) {
		this.tradedDate = tradedDate;
	}

	public String getTradedDay() {
		return tradedDay;
	}

	public void setTradedDay(String tradedDay) {
		this.tradedDay = tradedDay;
	}

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public double getLtp() {
		return ltp;
	}

	public void setLtp(double ltp) {
		this.ltp = ltp;
	}

	@Override
	public String toString() {
		return "StrategyEntity [name=" + name + ", totalPL=" + totalPL + ", target=" + target + ", targetLoss=" + targetLoss + ", totalPLMin=" + totalPLMin + ", totalPLMax=" + totalPLMax
				+ ", totalPLMinTime=" + totalPLMinTime + ", totalPLMaxTime=" + totalPLMaxTime + ", totalPLMinSpot=" + totalPLMinSpot + ", totalPLMaxSpot=" + totalPLMaxSpot + ", strategyHoldingTime="
				+ strategyHoldingTime + "]";
	}

	/*
	 * public double getTradedPriceSpot() { return tradedPriceSpot; }
	 * 
	 * public void setTradedPriceSpot(double tradedPriceSpot) { this.tradedPriceSpot
	 * = tradedPriceSpot; }
	 */

}
