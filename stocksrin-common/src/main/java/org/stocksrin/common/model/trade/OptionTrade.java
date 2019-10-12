package org.stocksrin.common.model.trade;

import java.io.Serializable;

import org.stocksrin.common.model.option.OptionType;

public class OptionTrade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OptionType optionType;
	private String expiry;

	private double optionTradedPrice;
	private double optionLTP;
	private double strike;
	private double iv;
	private long dte;

	public OptionTrade() {
		super();
	}

	public OptionTrade(OptionType optionType, String expiry) {
		super();
		this.optionType = optionType;
		this.expiry = expiry;
	}

	public double getOptionTradedPrice() {
		return optionTradedPrice;
	}

	public void setOptionTradedPrice(double optionTradedPrice) {
		this.optionTradedPrice = optionTradedPrice;
	}

	public OptionType getOptionType() {
		return optionType;
	}

	public void setOptionType(OptionType optionType) {
		this.optionType = optionType;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public double getStrike() {
		return strike;
	}

	public void setStrike(double strike) {
		this.strike = strike;
	}

	public double getIv() {
		return iv;
	}

	public void setIv(double iv) {
		this.iv = iv;
	}

	public long getDte() {
		return dte;
	}

	public void setDte(long dte) {
		this.dte = dte;
	}

	public double getOptionLTP() {
		return optionLTP;
	}

	public void setOptionLTP(double optionLTP) {
		this.optionLTP = optionLTP;
	}

	@Override
	public String toString() {
		return "OptionTrade [optionType=" + optionType + ", expiry=" + expiry + ", optionLTP=" + optionLTP + ", strike=" + strike + ", iv=" + iv + ", dte=" + dte + "]";
	}

}
