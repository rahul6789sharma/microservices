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
	private double maxPainStrick;
	private List<OptionModle> optionModle = new ArrayList<>();

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

	public String toString() {
		return "OptionModles [date=" + this.date + ", expiry=" + this.expiry + ", expiryList=" + this.expiryList + ", lastDataUpdated=" + this.lastDataUpdated + ", underlyingSpotPriceString="
				+ this.underlyingSpotPriceString + ", spot=" + this.spot + ", change=" + this.change + ", type=" + this.type + ", total_ce_oi=" + this.total_ce_oi + ", total_pe_oi=" + this.total_pe_oi
				+ ", maxPainStrick=" + this.maxPainStrick + ", optionModle=" + this.optionModle + "]";
	}
}
