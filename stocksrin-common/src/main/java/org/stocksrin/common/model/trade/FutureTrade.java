package org.stocksrin.common.model.trade;

import java.io.Serializable;

public class FutureTrade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String expiry;

	private double futureTradedPrice;
	private double futureLTP;
	private double iv;

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public double getFutureTradedPrice() {
		return futureTradedPrice;
	}

	public void setFutureTradedPrice(double futureTradedPrice) {
		this.futureTradedPrice = futureTradedPrice;
	}

	public double getFutureLTP() {
		return futureLTP;
	}

	public void setFutureLTP(double futureLTP) {
		this.futureLTP = futureLTP;
	}

	public double getIv() {
		return iv;
	}

	public void setIv(double iv) {
		this.iv = iv;
	}

	@Override
	public String toString() {
		return "FutureTrade [expiry=" + expiry + ", futureTradedPrice=" + futureTradedPrice + ", futureLTP=" + futureLTP + ", iv=" + iv + "]";
	}

}
