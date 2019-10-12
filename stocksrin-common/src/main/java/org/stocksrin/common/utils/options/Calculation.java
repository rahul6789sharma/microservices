package org.stocksrin.common.utils.options;

import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.model.option.MaxPain;
import org.stocksrin.common.model.option.MaxPains;
import org.stocksrin.common.model.option.OptionModle;

public class Calculation {
	private Calculation() {

	}

	public static MaxPains calMaxPain(List<OptionModle> optionModle, Double strickDiff, String expiry) {

		MaxPains maxPains = new MaxPains();
		List<MaxPain> maxPainList = new ArrayList<>();

		for (int i = 0; i < optionModle.size(); i++) {
			Double strikePrice = optionModle.get(i).getStrike_price();

			Integer ceOI = optionModle.get(i).getC_oi();
			Integer peOI = optionModle.get(i).getP_oi();

			MaxPain maxPain = new MaxPain(strikePrice, ceOI, peOI);
			Double total;

			Double callCuresult = 0.0;
			for (int j = 0; j < i; j++) {
				Integer a1 = optionModle.get(j).getC_oi();
				if (a1 != null) {
					callCuresult = callCuresult + (a1 * ((strickDiff * i) - (strickDiff * j)));
					maxPain.setCumulativeCe(callCuresult);
				}
			}

			Double putCuresult = 0.0;
			for (int j = i; j < optionModle.size(); j++) {
				Integer a1 = optionModle.get(j).getP_oi();
				if (a1 != null) {
					putCuresult = putCuresult + (a1 * ((strickDiff * j) - (strickDiff * i)));
					maxPain.setCumulativePe(putCuresult);
				}

			}

			total = putCuresult + callCuresult;
			maxPain.setTotal(total);
			maxPain.setStrickPrice(strikePrice);
			maxPainList.add(maxPain);

		}

		maxPains.setDataSet(maxPainList);
		Double maxPainStrick = findMaxPain(maxPainList);
		maxPains.setMaxPainStrick(maxPainStrick);
		maxPains.setExpiry(expiry);
		return maxPains;

	}

	public static Double findMaxPain(List<MaxPain> maxPainList) {
		Double smallest = maxPainList.get(0).getTotal();
		Double strickPrice = 0.0;
		for (MaxPain maxPain : maxPainList) {

			if (smallest > maxPain.getTotal()) {
				smallest = maxPain.getTotal();
				strickPrice = maxPain.getStrickPrice();
			}
		}
		return strickPrice;
	}
}