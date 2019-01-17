package org.stocksrin.collector.option.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.collector.option.data.utils.BankNiftyUtils;
import org.stocksrin.collector.option.data.utils.NiftyUtils;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.option.OptionModles;

public class PriceUtils {

	private PriceUtils() {
	}

	private  static final Logger log = LoggerFactory.getLogger(PriceUtils.class);

	public static void main(String[] args) throws Exception {
		fetchData();
	}

	public static synchronized void fetchData() throws Exception {
		loadBNLiveData();
		Thread.sleep(1);
		loadNiftyData();
		log.info("BNF : " + BankNiftyData.getBNFSpot());
		log.info("Nifty : " + NiftyData.getNFSpot());
	}

	// return trading date
	private static void loadBNLiveData() throws Exception {
		OptionModles optionModles = BankNiftyUtils.getBankNiftyOptionData(null);
		List<String> expiryList = optionModles.getExpiryList();
		BankNiftyData.shortedExpiry.addAll(expiryList);
		BankNiftyData.bnOptionData.put(optionModles.getExpiry(), optionModles);

		for (int i = 1; i < expiryList.size(); i++) {
			OptionModles data = BankNiftyUtils.getBankNiftyOptionData(expiryList.get(i));
			BankNiftyData.bnOptionData.put(expiryList.get(i), data);
		}

	}

	private static void loadNiftyData() throws Exception {
		NiftyData.clear();
		OptionModles optionModles = NiftyUtils.getNiftyOptionData(null);
		NiftyData.optionData.put(optionModles.getExpiry(), optionModles);
		NiftyData.shortedExpiry.addAll(optionModles.getExpiryList());
		NiftyData.maxPains.put(optionModles.getExpiry(), optionModles.getMaxPainStrick());
	}

}