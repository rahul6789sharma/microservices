package org.stocksrin.strategy.db.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class DerivativeTrade {

	@Enumerated(EnumType.STRING)
	private DerivativeType optionType;
	// only for option
	private Double strike;

	private Long dte;
	private String expiry;
	private Double iv;

	private Double underlyingTradedprice;
	private Double underlyingltp;

	public DerivativeType getOptionType() {
		return optionType;
	}

	public void setOptionType(DerivativeType optionType) {
		this.optionType = optionType;
	}

	public Double getStrike() {
		return strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}

	public Long getDte() {
		return dte;
	}

	public void setDte(Long dte) {
		this.dte = dte;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public Double getIv() {
		return iv;
	}

	public void setIv(Double iv) {
		this.iv = iv;
	}

	public Double getUnderlyingTradedprice() {
		return underlyingTradedprice;
	}

	public void setUnderlyingTradedprice(Double underlyingTradedprice) {
		this.underlyingTradedprice = underlyingTradedprice;
	}

	public Double getUnderlyingltp() {
		return underlyingltp;
	}

	public void setUnderlyingltp(Double underlyingltp) {
		this.underlyingltp = underlyingltp;
	}

}
