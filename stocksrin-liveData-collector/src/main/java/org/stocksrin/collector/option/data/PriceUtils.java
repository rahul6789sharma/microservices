package org.stocksrin.collector.option.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.collector.option.data.utils.BankNiftyUtils;
import org.stocksrin.collector.option.data.utils.FutureDataCollector;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.future.Future;
import org.stocksrin.common.model.option.AdvanceDecline;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.ExpiryDatesUtils;
import org.stocksrin.common.utils.options.NiftyUtils;
import org.stocksrin.intraday.IntraDayAdvanceDeclinedData;

public class PriceUtils {

	private PriceUtils() {
	}

	private static final Logger log = LoggerFactory.getLogger(PriceUtils.class);

	public static void main(String[] args) throws Exception {
		fetchData();
	}

	public static synchronized void fetchData() throws Exception {
		loadBNLiveData();
		Thread.sleep(1);
		loadNiftyData();
		AdvanceDecline advanceDecline = IntraDayAdvanceDeclinedData.collectData();
		NiftyData.advanceDecline = advanceDecline;
		log.info("BNF : " + BankNiftyData.getBNFSpot());
		log.info("Nifty : " + NiftyData.getNFSpot());
		log.info("Advance Declined : " + advanceDecline);
		log.info("Nifty : " + NiftyData.future);
		log.info("Nifty : " + BankNiftyData.future);

	}

	// return trading date
	private static void loadBNLiveData() throws Exception {
		BankNiftyData.clear();

		OptionModles optionModles = BankNiftyUtils.getBankNiftyOptionData(null);
		List<String> expiryList = optionModles.getExpiryList();
		BankNiftyData.shortedExpiry.addAll(expiryList);
		String monthlyExpiry = ExpiryDatesUtils.getMonthlyExpiry(expiryList);
		Future future = FutureDataCollector.getFutureData("BANKNIFTY", monthlyExpiry);

		BankNiftyData.future.put(monthlyExpiry, future);
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
		String monthlyExpiry = ExpiryDatesUtils.getMonthlyExpiry(optionModles.getExpiryList());
		Future future = FutureDataCollector.getFutureData("NIFTY", monthlyExpiry);
		NiftyData.future.put(monthlyExpiry, future);
		List<String> expiryList = optionModles.getExpiryList();
		NiftyData.maxPains.put(optionModles.getExpiry(), optionModles.getMaxPainStrick());

		for (int i = 1; i < expiryList.size(); i++) {
			OptionModles data = NiftyUtils.getNiftyOptionData(expiryList.get(i));
			NiftyData.optionData.put(expiryList.get(i), data);
		}
	}

}