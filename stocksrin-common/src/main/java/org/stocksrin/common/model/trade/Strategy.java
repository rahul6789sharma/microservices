package org.stocksrin.common.model.trade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Strategy {

	private UnderLyingInstrument underlying;
	private double underlying_ltp;
	private double tradeSpotPrice;
	private String dataUpdatedAt;
	private String tradeDate;
	private String tradeDay;

	// define positive amount only
	private double stopLoss;
	private String strategyName;

	private double totalPL;
	private double target;
	private double targetLoss;

	private double totalPLMin;
	private double totalPLMax;
	private String totalPLMinTime;
	private String totalPLMaxTime;
	private double totalPLMinSpot;
	private double totalPLMaxSpot;
	
	private String dir;
	private String fileName;

	private String dte;

	private List<StrategyModel> strategyModels = new ArrayList<>(4);

	private Map<Double, Double> allStrikePNL = new LinkedHashMap<>();
	private double maxLossLowerSide = Double.MIN_VALUE;
	private double maxLossUpperSide = Double.MIN_VALUE;
	private double lowerBreakEven;
	private double upperBreakEven;
	private double maxProfit = Double.MAX_VALUE;

	public Strategy() {
	}

	public Strategy(UnderLyingInstrument underlying) {
		this.underlying = underlying;
	}

	public Map<Double, Double> getAllStrikePNL() {
		return this.allStrikePNL;
	}

	public void setAllStrikePNL(Map<Double, Double> allStrikePNL) {
		this.allStrikePNL = allStrikePNL;
	}

	public Double getMaxLossLowerSide() {
		return Double.valueOf(this.maxLossLowerSide);
	}

	public void setMaxLossLowerSide(Double maxLossLowerSide) {
		this.maxLossLowerSide = maxLossLowerSide.doubleValue();
	}

	public Double getMaxLossUpperSide() {
		return Double.valueOf(this.maxLossUpperSide);
	}

	public void setMaxLossUpperSide(Double maxLossUpperSide) {
		this.maxLossUpperSide = maxLossUpperSide.doubleValue();
	}

	public Double getLowerBreakEven() {
		return Double.valueOf(this.lowerBreakEven);
	}

	public void setLowerBreakEven(Double lowerBreakEven) {
		this.lowerBreakEven = lowerBreakEven.doubleValue();
	}

	public Double getUpperBreakEven() {
		return Double.valueOf(this.upperBreakEven);
	}

	public void setUpperBreakEven(Double upperBreakEven) {
		this.upperBreakEven = upperBreakEven.doubleValue();
	}

	public Double getMaxProfit() {
		return Double.valueOf(this.maxProfit);
	}

	public void setMaxProfit(Double maxProfit) {
		this.maxProfit = maxProfit.doubleValue();
	}

	public String getDir() {
		return this.dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public UnderLyingInstrument getUnderlying() {
		return this.underlying;
	}

	public double getTradeSpotPrice() {
		return this.tradeSpotPrice;
	}

	public void setTradeSpotPrice(double tradeSpotPrice) {
		this.tradeSpotPrice = tradeSpotPrice;
	}

	public String getTradeDay() {
		return this.tradeDay;
	}

	public void setTradeDay(String tradeDay) {
		this.tradeDay = tradeDay;
	}

	public void setUnderlying(UnderLyingInstrument underlying) {
		this.underlying = underlying;
	}

	public double getUnderlying_ltp() {
		return this.underlying_ltp;
	}

	public void setUnderlying_ltp(double underlying_ltp) {
		this.underlying_ltp = underlying_ltp;
	}

	public String getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public double getTarget() {
		return this.target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public double getStopLoss() {
		return this.stopLoss;
	}

	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public String getStrategyName() {
		return this.strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public double getTotalPL() {
		return this.totalPL;
	}

	public void setTotalPL(double totalPL) {
		this.totalPL = totalPL;
	}

	public double getTotalPLMin() {
		return this.totalPLMin;
	}

	public void setTotalPLMin(double totalPLMin) {
		this.totalPLMin = totalPLMin;
	}

	public double getTotalPLMax() {
		return this.totalPLMax;
	}

	public void setTotalPLMax(double totalPLMax) {
		this.totalPLMax = totalPLMax;
	}

	public String getTotalPLMinTime() {
		return this.totalPLMinTime;
	}

	public void setTotalPLMinTime(String totalPLMinTime) {
		this.totalPLMinTime = totalPLMinTime;
	}

	public String getTotalPLMaxTime() {
		return this.totalPLMaxTime;
	}

	public void setTotalPLMaxTime(String totalPLMaxTime) {
		this.totalPLMaxTime = totalPLMaxTime;
	}

	public double getTotalPLMinSpot() {
		return this.totalPLMinSpot;
	}

	public void setTotalPLMinSpot(double totalPLMinSpot) {
		this.totalPLMinSpot = totalPLMinSpot;
	}

	public double getTotalPLMaxSpot() {
		return this.totalPLMaxSpot;
	}

	public void setTotalPLMaxSpot(double totalPLMaxSpot) {
		this.totalPLMaxSpot = totalPLMaxSpot;
	}

	public List<StrategyModel> getStrategyModels() {
		return this.strategyModels;
	}

	public void setStrategyModels(List<StrategyModel> strategyModels) {
		this.strategyModels = strategyModels;
	}

	public String getDataUpdatedAt() {
		return this.dataUpdatedAt;
	}

	public void setDataUpdatedAt(String dataUpdatedAt) {
		this.dataUpdatedAt = dataUpdatedAt;
	}

	public double getTargetLoss() {
		return targetLoss;
	}

	public void setTargetLoss(double targetLoss) {
		this.targetLoss = targetLoss;
	}

	public String getDte() {
		return dte;
	}

	public void setDte(String dte) {
		this.dte = dte;
	}

	public String toString() {
		return "Strategy [underlying=" + this.underlying + ", underlying_ltp=" + this.underlying_ltp + ", tradeDate=" + this.tradeDate + ", target=" + this.target + ", stopLoss=" + this.stopLoss
				+ ", Strategyname=" + this.strategyName + ", totalPL=" + this.totalPL + ", totalPLMin=" + this.totalPLMin + ", totalPLMax=" + this.totalPLMax + ", totalPLMinTime="
				+ this.totalPLMinTime + ", totalPLMaxTime=" + this.totalPLMaxTime + ", totalPLMinSpot=" + this.totalPLMinSpot + ", totalPLMaxSpot=" + this.totalPLMaxSpot + ", strategyModels="
				+ this.strategyModels + "]";
	}
}
