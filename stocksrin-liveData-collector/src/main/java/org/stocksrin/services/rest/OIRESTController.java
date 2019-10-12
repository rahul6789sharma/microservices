package org.stocksrin.services.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.OptionChainOIData;
import org.stocksrin.common.model.ParticipantOIModle;
import org.stocksrin.common.utils.options.ExpiryUtils;
import org.stocksrin.participant.oidata.ParticapentOIData;

@RestController
@RequestMapping("/oi")
public class OIRESTController {

	@RequestMapping("/hello")
	public String sayHello() {
		return "OIController";
	}

	@RequestMapping("/data")
	public Map<String, List<ParticipantOIModle>> getDat() {
		return ParticapentOIData.data;
	}

	@RequestMapping("/niftyOIData")
	public Map<String, List<OptionChainOIData>> getNiftyOIData() throws Exception {

		return getOIExpiryData(NiftyData.oiData, NiftyData.shortedExpiry);
	}

	@RequestMapping("/bnfOIData")
	public Map<String, List<OptionChainOIData>> getBnfOIData() throws Exception {
		return getOIExpiryData(BankNiftyData.oiData, BankNiftyData.shortedExpiry);

	}

	private static Map<String, List<OptionChainOIData>> getOIExpiryData(Map<String, List<OptionChainOIData>> oiData,
			SortedSet<String> shortedExpiry) throws Exception {
		List<String> sortedExpiry = new ArrayList<>(shortedExpiry);
		boolean status = ExpiryUtils.isTodayExpiry(sortedExpiry);

		List<String> expiryResult = new ArrayList<>();
		if (status) {

			if (ExpiryUtils.isMonthlyExpiry(sortedExpiry)) {
				// monthly
				expiryResult.add(shortedExpiry.first());
				expiryResult.add(sortedExpiry.get(1));
			} else {
				// weekly
				expiryResult.add(sortedExpiry.get(1));
				expiryResult.add(ExpiryUtils.getCurrentMonthExpiry(sortedExpiry));
			}
		} else {
			// if it is not expiry
			expiryResult.add(shortedExpiry.first());
			expiryResult.add(ExpiryUtils.getCurrentMonthExpiry(sortedExpiry));
		}

		Map<String, List<OptionChainOIData>> result = new HashMap<>();

		for (String string : expiryResult) {
			result.put(string, oiData.get(string));
		}
		// String nextMonthExpiry = ExpiryUtils.getNextMonthlyExpiry(sortedExpiry);

		return result;
	}
}
