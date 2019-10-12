package org.stocksrin.option.data.store;

import java.time.LocalDate;

public class OIData {

	String symbole;
	int totalCe;
	int totalPe;
	LocalDate date;
	double closePrice;

	public OIData(String symbole, int totalCe, int totalPe, LocalDate date) {
		super();
		this.symbole = symbole;
		this.totalCe = totalCe;
		this.totalPe = totalPe;
		this.date = date;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public int getTotalCe() {
		return totalCe;
	}

	public void setTotalCe(int totalCe) {
		this.totalCe = totalCe;
	}

	public int getTotalPe() {
		return totalPe;
	}

	public void setTotalPe(int totalPe) {
		this.totalPe = totalPe;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
