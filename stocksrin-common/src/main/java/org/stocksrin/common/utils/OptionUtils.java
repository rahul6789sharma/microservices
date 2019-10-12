package org.stocksrin.common.utils;

import java.util.List;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;

public interface OptionUtils {

	public static double getATMStrike(OptionModles optionModles, int strikediff) {

		double spot = optionModles.getSpot();
		List<OptionModle> lst = optionModles.getOptionModle();
		double atmStrike = 0.0;
		for (OptionModle optionModle : lst) {

			double diff = Math.abs(spot - optionModle.getStrike_price());
			if (diff < strikediff) {
				atmStrike = optionModle.getStrike_price();
				break;
			}
		}

		return atmStrike;
	}
}
