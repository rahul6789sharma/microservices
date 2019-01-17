package org.stocksrin.common.model.strategies;

import java.util.LinkedHashMap;
import java.util.Map;

import org.stocksrin.common.model.option.OptionType;

public class StrategyModel {
	private String strategySerial;
	private String expiry;
	private OptionType type;
	private double strike;
	private double avgPrice;
	private int quantity;
	private double target;
	private double stopLoss;
	private double spot_close;
	private String des;
	private boolean status;
	private double traded_IV;
	private String tradeDate;
	private double current_IV;
	private String underlying;
	private double underlying_ltp;
	private double ltp;
	private double ltp_min;
	private double ltp_max;
	private Map<Double, Double> intenrsic = new LinkedHashMap();

	public String toCSV() {
		return this.strategySerial + "," + this.expiry + "," + this.type + "," + this.strike + "," + this.avgPrice + "," + this.quantity + "," + this.target + "," + this.stopLoss + ","
				+ this.spot_close + "," + this.des + "," + this.status + "," + this.traded_IV + "," + this.tradeDate;
	}

	public Map<Double, Double> getIntenrsic() {
		return this.intenrsic;
	}

	public void setIntenrsic(Map<Double, Double> intenrsic) {
		this.intenrsic = intenrsic;
	}

	public String getStrategySerial() {
		return this.strategySerial;
	}

	public void setStrategySerial(String strategySerial) {
		this.strategySerial = strategySerial;
	}

	public String getExpiry() {
		return this.expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public OptionType getType() {
		return this.type;
	}

	public void setType(OptionType type) {
		this.type = type;
	}

	public double getStrike() {
		return this.strike;
	}

	public void setStrike(double strike) {
		this.strike = strike;
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

	public double getSpot_close() {
		return this.spot_close;
	}

	public void setSpot_close(double spot_close) {
		this.spot_close = spot_close;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getTraded_IV() {
		return this.traded_IV;
	}

	public void setTraded_IV(double traded_IV) {
		this.traded_IV = traded_IV;
	}

	public String getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public double getAvgPrice() {
		return this.avgPrice;
	}

	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getUnderlying() {
		return this.underlying;
	}

	public void setUnderlying(String underlying) {
		this.underlying = underlying;
	}

	public double getUnderlying_ltp() {
		return this.underlying_ltp;
	}

	public void setUnderlying_ltp(double underlying_ltp) {
		this.underlying_ltp = underlying_ltp;
	}

	public double getLtp() {
		return this.ltp;
	}

	public void setLtp(double ltp) {
		this.ltp = ltp;
	}

	public double getCurrent_IV() {
		return this.current_IV;
	}

	public void setCurrent_IV(double current_IV) {
		this.current_IV = current_IV;
	}

	public double getLtp_min() {
		return this.ltp_min;
	}

	public void setLtp_min(double ltp_min) {
		this.ltp_min = ltp_min;
	}

	public double getLtp_max() {
		return this.ltp_max;
	}

	public void setLtp_max(double ltp_max) {
		this.ltp_max = ltp_max;
	}

	public String toString() {
		return "StrategyModel [strategySerial=" + this.strategySerial + ", expiry=" + this.expiry + ", type=" + this.type + ", strike=" + this.strike + ", avgPrice=" + this.avgPrice + ", quantity="
				+ this.quantity + ", target=" + this.target + ", stopLoss=" + this.stopLoss + ", spot_close=" + this.spot_close + ", des=" + this.des + ", status=" + this.status + ", traded_IV="
				+ this.traded_IV + ", tradeDate=" + this.tradeDate + ", underlying=" + this.underlying + ", underlying_ltp=" + this.underlying_ltp + ", ltp=" + this.ltp + "]";
	}
}
