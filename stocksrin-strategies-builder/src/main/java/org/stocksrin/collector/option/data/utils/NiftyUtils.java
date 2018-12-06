package org.stocksrin.collector.option.data.utils;

import org.stocksrin.common.model.option.MaxPains;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.options.OptionUtils;

public class NiftyUtils {

	// can be passed null , null means current expiry
	public static OptionModles getNiftyOptionData(String expiry) throws Exception {

		OptionModles optionModles = NiftyUtils.getOptionChain(expiry);
		MaxPains maxPain = Calculation.calMaxPain(optionModles.getOptionModle(), AppConstant.NF_STRIKE_DIFF, optionModles.getExpiry());
		optionModles.setMaxPainStrick(maxPain.getMaxPainStrick());
		return optionModles;
	}

	private static synchronized OptionModles getOptionChain(String expiry) throws Exception {
		String url = null;
		if (expiry == null) {
			url = AppConstant.NIFTY_URL;
		} else {
			url = AppConstant.NIFTY_URL_URL_BY_Expiry + expiry;
		}
		return OptionUtils.getOptionChain(url, expiry);
	}

}
