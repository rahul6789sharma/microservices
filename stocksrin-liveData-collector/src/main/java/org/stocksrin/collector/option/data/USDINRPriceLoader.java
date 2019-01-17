package org.stocksrin.collector.option.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.collector.option.data.utils.USDINRUtils;
import org.stocksrin.common.data.USDINRData;
import org.stocksrin.common.model.option.OptionModles;

public class USDINRPriceLoader {

	private static final Logger log = LoggerFactory.getLogger(USDINRPriceLoader.class);

	public static synchronized void fetchData() throws Exception {
		loadLiveData();
		log.info("USDINR : " + USDINRData.getSpot());
	}

	// return trading date
	private static void loadLiveData() throws Exception {
		//log.info("USDINR ");
		OptionModles optionModles = USDINRUtils.getOptionData(null);
		List<String> expiryList = optionModles.getExpiryList();
		USDINRData.shortedExpiry.addAll(expiryList);
		USDINRData.optionData.put(optionModles.getExpiry(), optionModles);

		for (int i = 1; i < expiryList.size(); i++) {
			OptionModles data = USDINRUtils.getOptionData(expiryList.get(i));
			USDINRData.optionData.put(expiryList.get(i), data);
		}

	}
}
