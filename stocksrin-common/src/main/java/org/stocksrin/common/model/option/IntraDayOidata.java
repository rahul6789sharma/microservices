package org.stocksrin.common.model.option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.stocksrin.common.model.intraday.IntraDayOptionModles;

public class IntraDayOidata {

	public Map<Integer, IntraDayOptionModles> optionDataIntraDay = new ConcurrentHashMap<>();

	public Map<Integer, IntraDayOptionModles> getOptionDataIntraDay() {
		return optionDataIntraDay;
	}

	public void setOptionDataIntraDay(Map<Integer, IntraDayOptionModles> optionDataIntraDay) {
		this.optionDataIntraDay = optionDataIntraDay;
	}

	Map<Integer, List<Element>> oiStradelData = new HashMap<>();
}

class Element {

	private double atmStrike;
	
	private double callIV;
	private double putIV;
	
	private int callPremiumDecay;
	private int putPremiumDecay;
	
	private double callOI;
	private double putOI;
	
	private int avg_change_in_oi_ce; // of top 5 option contract by outstanding oi
	private int avg_change_in_oi_pe;

}