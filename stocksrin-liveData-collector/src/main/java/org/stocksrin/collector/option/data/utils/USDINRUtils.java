package org.stocksrin.collector.option.data.utils;

import java.util.List;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.options.OptionUtils;

public class USDINRUtils {

	public static void main(String[] args) throws Exception {

		OptionModles data = getOptionData(null);
		List<OptionModle> lst = data.getOptionModle();
		for (OptionModle optionModle : lst) {
			System.out.println(optionModle);
		}
		System.out.println(data);
	}

	// can be passed null , null means current expiry
	public static OptionModles getOptionData(String expiry) throws Exception {

		OptionModles optionModles = getOptionChain(expiry);
		return optionModles;
	}

	private static synchronized OptionModles getOptionChain(String expiry) throws Exception {
		String url = null;
		if (expiry != null) {
			url = AppConstant.USDINR_URL_BY_Expiry + expiry;

		} else {
			url = AppConstant.USDINR_URL;
		}
		return OptionUtils.getUSDINROptionChain(url, expiry);
	}
}
