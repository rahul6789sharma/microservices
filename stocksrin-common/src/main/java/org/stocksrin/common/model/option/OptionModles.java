package org.stocksrin.common.model.option;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OptionModles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private int dte;
	private String day;
	private String expiry;
	private List<String> expiryList = new ArrayList<>();
	private String lastDataUpdated;
	private String underlyingSpotPriceString;
	private Double spot;

	private Double previousClose;
	private String change;
	private String type;

	private int total_ce_oi;
	private int total_pe_oi;

	private int total_ce_volume;
	private int total_pe_volume;

	private int total_ce_oi_change;
	private int total_pe_oi_change;

	private double maxPainStrick;
	private List<OptionModle> optionModle = new ArrayList<>();

	// used in usdInr
	private FuturePrice futurePrice;

	private Double atmStrike;
	private Double iv;
	private Double pe_atm_iv;
	private Double ce_atm_iv;

	public int getTotal_ce_volume() {
		return total_ce_volume;
	}

	public void setTotal_ce_volume(int total_ce_volume) {
		this.total_ce_volume = total_ce_volume;
	}

	public int getTotal_pe_volume() {
		return total_pe_volume;
	}

	public void setTotal_pe_volume(int total_pe_volume) {
		this.total_pe_volume = total_pe_volume;
	}

	public int getTotal_ce_oi() {
		return this.total_ce_oi;
	}

	public void setTotal_ce_oi(int total_ce_oi) {
		this.total_ce_oi = total_ce_oi;
	}

	public int getTotal_pe_oi() {
		return this.total_pe_oi;
	}

	public void setTotal_pe_oi(int total_pe_oi) {
		this.total_pe_oi = total_pe_oi;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpiry() {
		return this.expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public List<String> getExpiryList() {
		return this.expiryList;
	}

	public void setExpiryList(List<String> expiryList) {
		this.expiryList = expiryList;
	}

	public String getLastDataUpdated() {
		return this.lastDataUpdated;
	}

	public void setLastDataUpdated(String lastDataUpdated) {
		this.lastDataUpdated = lastDataUpdated;
	}

	public String getUnderlyingSpotPriceString() {
		return this.underlyingSpotPriceString;
	}

	public void setUnderlyingSpotPriceString(String underlyingSpotPriceString) {
		this.underlyingSpotPriceString = underlyingSpotPriceString;
	}

	public Double getPreviousClose() {
		return this.previousClose;
	}

	public void setPreviousClose(Double previousClose) {
		this.previousClose = previousClose;
	}

	public String getChange() {
		return this.change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public List<OptionModle> getOptionModle() {
		return this.optionModle;
	}

	public void setOptionModle(List<OptionModle> optionModle) {
		this.optionModle = optionModle;
	}

	public Double getSpot() {
		return this.spot;
	}

	public void setSpot(Double spot) {
		this.spot = spot;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMaxPainStrick() {
		return this.maxPainStrick;
	}

	public void setMaxPainStrick(double maxPainStrick) {
		this.maxPainStrick = maxPainStrick;
	}

	public int getTotal_ce_oi_change() {
		return total_ce_oi_change;
	}

	public void setTotal_ce_oi_change(int total_ce_oi_change) {
		this.total_ce_oi_change = total_ce_oi_change;
	}

	public int getTotal_pe_oi_change() {
		return total_pe_oi_change;
	}

	public void setTotal_pe_oi_change(int total_pe_oi_change) {
		this.total_pe_oi_change = total_pe_oi_change;
	}

	public FuturePrice getFuturePrice() {
		return futurePrice;
	}

	public void setFuturePrice(FuturePrice futurePrice) {
		this.futurePrice = futurePrice;
	}

	public Double getAtmStrike() {
		return atmStrike;
	}

	public void setAtmStrike(Double atmStrike) {
		this.atmStrike = atmStrike;
	}

	public Double getIv() {
		return iv;
	}

	public void setIv(Double iv) {
		this.iv = iv;
	}

	public Double getPe_atm_iv() {
		return pe_atm_iv;
	}

	public void setPe_atm_iv(Double pe_atm_iv) {
		this.pe_atm_iv = pe_atm_iv;
	}

	public Double getCe_atm_iv() {
		return ce_atm_iv;
	}

	public void setCe_atm_iv(Double ce_atm_iv) {
		this.ce_atm_iv = ce_atm_iv;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getDte() {
		return dte;
	}

	public void setDte(int dte) {
		this.dte = dte;
	}

	public String toString() {
		return "OptionModles [date=" + this.date + ", expiry=" + this.expiry + ", expiryList=" + this.expiryList + ", lastDataUpdated=" + this.lastDataUpdated + ", underlyingSpotPriceString="
				+ this.underlyingSpotPriceString + ", spot=" + this.spot + ", change=" + this.change + ", type=" + this.type + ", total_ce_oi=" + this.total_ce_oi + ", total_pe_oi=" + this.total_pe_oi
				+ ", maxPainStrick=" + this.maxPainStrick + ", optionModle=" + this.optionModle + "]";
	}
}
