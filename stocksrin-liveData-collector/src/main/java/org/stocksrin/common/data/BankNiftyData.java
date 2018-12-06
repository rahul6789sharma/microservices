package org.stocksrin.common.data;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.ComparatorBasedOnDate;

public class BankNiftyData {

	public static SortedSet<String> shortedExpiry = new TreeSet<>(new ComparatorBasedOnDate());
	// Expiry and its data

	// expiry And its live option chain
	public static Map<String, OptionModles> bnOptionData = new ConcurrentHashMap<>();

	// expiry and its maxpain
	public static Map<String, Double> maxPains = new HashMap<>();

	public static void clear() {
		shortedExpiry.clear();
		bnOptionData.clear();
		maxPains.clear();
	}

	public static String getlastDataUpdated(String expiry) {

		OptionModles data = bnOptionData.get(expiry);
		if (data != null) {
			return data.getLastDataUpdated();
		} else {
			return null;
		}

	}

	public static double getBNFSpot() {
		OptionModles data = bnOptionData.get(shortedExpiry.first());
		return data.getSpot();
	}

	public static Double getIV(double strike, OptionType optionType, String expiry) {
		OptionModles data = bnOptionData.get(expiry);
		Double iv = null;
		if (data == null) {
			return iv;
		}

		if (optionType.equals(OptionType.PUT)) {
			for (OptionModle optionModle : data.getOptionModle()) {
				if (optionModle.getStrike_price().equals(strike)) {
					iv = optionModle.getP_iv();
				}
			}
		} else if (optionType.equals(OptionType.CALL)) {

			for (OptionModle optionModle : data.getOptionModle()) {
				if (optionModle.getStrike_price().equals(strike)) {
					iv = optionModle.getC_iv();
				}
			}
		}
		return iv;
	}

	public static double getLtp(double strike, OptionType optionType, String expiry) {
		OptionModles data = bnOptionData.get(expiry);
		double ltp = 0.0;
		if (data == null) {
			return ltp;
		}

		if (optionType.equals(OptionType.PUT)) {
			for (OptionModle optionModle : data.getOptionModle()) {
				if (optionModle.getStrike_price().equals(strike)) {
					ltp = optionModle.getP_ltp();
				}
			}
		} else if (optionType.equals(OptionType.CALL)) {

			for (OptionModle optionModle : data.getOptionModle()) {
				if (optionModle.getStrike_price().equals(strike)) {
					ltp = optionModle.getC_ltp();
				}
			}
		}
		return ltp;
	}
}