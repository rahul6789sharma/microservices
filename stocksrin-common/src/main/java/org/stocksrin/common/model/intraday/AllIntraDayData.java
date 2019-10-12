package org.stocksrin.common.model.intraday;

import java.util.ArrayList;
import java.util.List;

public class AllIntraDayData {

	private String lastDataUpdated;

	private String nifty_expiry;
	private double nifty_change_ce_oi;
	private double nifty_change_pe_oi;
	private double nifty_open;
	private double nifty_spot;

	private List<Integer> nifty_change_ce_oi_lst = new ArrayList<>();
	private List<Integer> nifty_change_pe_oi_lst = new ArrayList<>();

	private List<Integer> nifty_ce_avg_premium = new ArrayList<>();
	private List<Integer> nifty_pe_avg_premium = new ArrayList<>();

	private String bnf_expiry;
	private double bnf_change_ce_oi;
	private double bnf_change_pe_oi;
	private double bnf_spot;

	private List<Integer> bnf_change_ce_oi_lst = new ArrayList<>();
	private List<Integer> bnf_change_pe_oi_lst = new ArrayList<>();
	private List<Integer> bnf_ce_avg_premium = new ArrayList<>();
	private List<Integer> bnf_pe_avg_premium = new ArrayList<>();

	private double bnf_open;

	private int nifty50Advance;
	private int nifty50Decline;

	private int totalAdvance;
	private int totalDecline;

	private List<Integer> totalAdvances = new ArrayList<>();
	private List<Integer> totalDeclines = new ArrayList<>();

	private double inidaVix;

	/*
	 * public AllIntraDayData() {
	 * 
	 * totalAdvances.add(10); totalAdvances.add(20); totalDeclines.add(12);
	 * totalDeclines.add(1500);
	 * 
	 * nifty_change_ce_oi_lst.add(10); nifty_change_ce_oi_lst.add(2000);
	 * nifty_change_pe_oi_lst.add(100); nifty_change_pe_oi_lst.add(300);
	 * 
	 * }
	 */

	public String getNifty_expiry() {
		return nifty_expiry;
	}

	public void setNifty_expiry(String nifty_expiry) {
		this.nifty_expiry = nifty_expiry;
	}

	public String getBnf_expiry() {
		return bnf_expiry;
	}

	public void setBnf_expiry(String bnf_expiry) {
		this.bnf_expiry = bnf_expiry;
	}

	public String getLastDataUpdated() {
		return lastDataUpdated;
	}

	public void setLastDataUpdated(String lastDataUpdated) {
		this.lastDataUpdated = lastDataUpdated;
	}

	public double getNifty_change_ce_oi() {
		return nifty_change_ce_oi;
	}

	public void setNifty_change_ce_oi(double nifty_change_ce_oi) {
		this.nifty_change_ce_oi = nifty_change_ce_oi;
	}

	public double getNifty_change_pe_oi() {
		return nifty_change_pe_oi;
	}

	public void setNifty_change_pe_oi(double nifty_change_pe_oi) {
		this.nifty_change_pe_oi = nifty_change_pe_oi;
	}

	public double getNifty_open() {
		return nifty_open;
	}

	public void setNifty_open(double nifty_open) {
		this.nifty_open = nifty_open;
	}

	public double getBnf_change_ce_oi() {
		return bnf_change_ce_oi;
	}

	public void setBnf_change_ce_oi(double bnf_change_ce_oi) {
		this.bnf_change_ce_oi = bnf_change_ce_oi;
	}

	public double getBnf_change_pe_oi() {
		return bnf_change_pe_oi;
	}

	public void setBnf_change_pe_oi(double bnf_change_pe_oi) {
		this.bnf_change_pe_oi = bnf_change_pe_oi;
	}

	public double getBnf_open() {
		return bnf_open;
	}

	public void setBnf_open(double bnf_open) {
		this.bnf_open = bnf_open;
	}

	public int getNifty50Advance() {
		return nifty50Advance;
	}

	public void setNifty50Advance(int nifty50Advance) {
		this.nifty50Advance = nifty50Advance;
	}

	public int getNifty50Decline() {
		return nifty50Decline;
	}

	public void setNifty50Decline(int nifty50Decline) {
		this.nifty50Decline = nifty50Decline;
	}

	public int getTotalAdvance() {
		return totalAdvance;
	}

	public void setTotalAdvance(int totalAdvance) {
		this.totalAdvance = totalAdvance;
	}

	public int getTotalDecline() {
		return totalDecline;
	}

	public void setTotalDecline(int totalDecline) {
		this.totalDecline = totalDecline;
	}

	public double getInidaVix() {
		return inidaVix;
	}

	public void setInidaVix(double inidaVix) {
		this.inidaVix = inidaVix;
	}

	public List<Integer> getTotalAdvances() {
		return totalAdvances;
	}

	public void setTotalAdvances(List<Integer> totalAdvances) {
		this.totalAdvances = totalAdvances;
	}

	public List<Integer> getTotalDeclines() {
		return totalDeclines;
	}

	public void setTotalDeclines(List<Integer> totalDeclines) {
		this.totalDeclines = totalDeclines;
	}

	public List<Integer> getNifty_change_ce_oi_lst() {
		return nifty_change_ce_oi_lst;
	}

	public void setNifty_change_ce_oi_lst(List<Integer> nifty_change_ce_oi_lst) {
		this.nifty_change_ce_oi_lst = nifty_change_ce_oi_lst;
	}

	public List<Integer> getNifty_change_pe_oi_lst() {
		return nifty_change_pe_oi_lst;
	}

	public void setNifty_change_pe_oi_lst(List<Integer> nifty_change_pe_oi_lst) {
		this.nifty_change_pe_oi_lst = nifty_change_pe_oi_lst;
	}

	public List<Integer> getBnf_change_ce_oi_lst() {
		return bnf_change_ce_oi_lst;
	}

	public void setBnf_change_ce_oi_lst(List<Integer> bnf_change_ce_oi_lst) {
		this.bnf_change_ce_oi_lst = bnf_change_ce_oi_lst;
	}

	public List<Integer> getBnf_change_pe_oi_lst() {
		return bnf_change_pe_oi_lst;
	}

	public void setBnf_change_pe_oi_lst(List<Integer> bnf_change_pe_oi_lst) {
		this.bnf_change_pe_oi_lst = bnf_change_pe_oi_lst;
	}

	public double getNifty_spot() {
		return nifty_spot;
	}

	public void setNifty_spot(double nifty_spot) {
		this.nifty_spot = nifty_spot;
	}

	public double getBnf_spot() {
		return bnf_spot;
	}

	public void setBnf_spot(double bnf_spot) {
		this.bnf_spot = bnf_spot;
	}

	public List<Integer> getNifty_ce_avg_premium() {
		return nifty_ce_avg_premium;
	}

	public void setNifty_ce_avg_premium(List<Integer> nifty_ce_avg_premium) {
		this.nifty_ce_avg_premium = nifty_ce_avg_premium;
	}

	public List<Integer> getNifty_pe_avg_premium() {
		return nifty_pe_avg_premium;
	}

	public void setNifty_pe_avg_premium(List<Integer> nifty_pe_avg_premium) {
		this.nifty_pe_avg_premium = nifty_pe_avg_premium;
	}

	public List<Integer> getBnf_ce_avg_premium() {
		return bnf_ce_avg_premium;
	}

	public void setBnf_ce_avg_premium(List<Integer> bnf_ce_avg_premium) {
		this.bnf_ce_avg_premium = bnf_ce_avg_premium;
	}

	public List<Integer> getBnf_pe_avg_premium() {
		return bnf_pe_avg_premium;
	}

	public void setBnf_pe_avg_premium(List<Integer> bnf_pe_avg_premium) {
		this.bnf_pe_avg_premium = bnf_pe_avg_premium;
	}

	@Override
	public String toString() {
		return "AllIntraDayData [lastDataUpdated=" + lastDataUpdated + ", nifty_expiry=" + nifty_expiry
				+ ", nifty_change_ce_oi=" + nifty_change_ce_oi + ", nifty_change_pe_oi=" + nifty_change_pe_oi
				+ ", nifty_open=" + nifty_open + ", nifty_change_ce_oi_lst=" + nifty_change_ce_oi_lst
				+ ", nifty_change_pe_oi_lst=" + nifty_change_pe_oi_lst + ", bnf_expiry=" + bnf_expiry
				+ ", bnf_change_ce_oi=" + bnf_change_ce_oi + ", bnf_change_pe_oi=" + bnf_change_pe_oi
				+ ", bnf_change_ce_oi_lst=" + bnf_change_ce_oi_lst + ", bnf_change_pe_oi_lst=" + bnf_change_pe_oi_lst
				+ ", bnf_open=" + bnf_open + ", nifty50Advance=" + nifty50Advance + ", nifty50Decline=" + nifty50Decline
				+ ", totalAdvance=" + totalAdvance + ", totalDecline=" + totalDecline + ", totalAdvances="
				+ totalAdvances + ", totalDeclines=" + totalDeclines + ", inidaVix=" + inidaVix + "]";
	}

}
