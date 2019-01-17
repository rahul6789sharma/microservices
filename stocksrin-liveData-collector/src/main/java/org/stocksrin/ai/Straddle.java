package org.stocksrin.ai;

public class Straddle {

	private String dte; // days to expiry

	// vix(inclusive) to next +1 execlusive
	// eg 14 to 14.99
	private String vix;
	// Map<Integer> vix= new HasMap;

	private int low;
	private int high;

	public String getDte() {
		return dte;
	}

	public void setDte(String dte) {
		this.dte = dte;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public String getVix() {
		return vix;
	}

	public void setVix(String vix) {
		this.vix = vix;
	}

	@Override
	public String toString() {
		return "Straddle [vix=" + vix + ", low=" + low + ", high=" + high + "]";
	}
}