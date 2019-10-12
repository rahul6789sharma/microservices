package org.stocksrin.collector.option.data.utils;

import org.stocksrin.common.model.InstrumentType;
import org.stocksrin.common.model.option.MaxPains;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.options.OptionUtils;



public class BankNiftyUtils {

	// can be passed null , null means current expiry
	public static OptionModles getBankNiftyOptionData(String expiry) throws Exception {

		OptionModles optionModles = getOptionChain(expiry);	
		MaxPains maxPain = Calculation.calMaxPain(optionModles.getOptionModle(), AppConstant.BNF_STRIKE_DIFF, optionModles.getExpiry());
		optionModles.setMaxPainStrick(maxPain.getMaxPainStrick());
		return optionModles;
	}
	
	private static synchronized OptionModles getOptionChain(String expiry) throws Exception {
		String url = null;
		if (expiry == null) {
			url = AppConstant.BANKNIFTY_WEEKLY_OPTION_URL;
		} else {
			url = AppConstant.BANKNIFTY_WEEKLY_OPTION_URL_BY_Expiry + expiry;
		}
		return OptionUtils.getOptionChain(url, expiry, InstrumentType.BNF, "BANKNIFTY");
	}


}