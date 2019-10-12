package org.stocksrin.services.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.utils.ComparatorBasedOnDate;

@RestController
@RequestMapping("/bnf")
public class BNFController {
	private static final Logger log = LoggerFactory.getLogger(BNFController.class);

	@Value("${microservice.liveData.url}")
	private String uri;

	@Value("${microservice.strategies-builder.url}")
	private String uri_strategies;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/currentWeekOptionChain")
	public Map<String, OptionModles> getBankNiftyWeeklyOptionChain() throws Exception {
		String url = uri + "/bnf/shortedExpiry/";

		Set<String> expiry = restTemplate.getForObject(url, SortedSet.class);
		SortedSet<String> expirySet = new TreeSet<>(new ComparatorBasedOnDate());
		expirySet.addAll(expiry);

		String currentExpiry = expirySet.first();
		String url2 = uri + "/bnf/optionModel/" + currentExpiry;
		// System.out.println("url2 "+url2);
		OptionModles data = restTemplate.getForObject(url2, OptionModles.class);
		Map<String, OptionModles> result = new HashMap<>();
		result.put(currentExpiry, data);
		return result;
	}

	@GetMapping("/strategies")
	public Map<String, Strategy> getStrategyResult() {
		Map<String, Strategy> result = new HashMap<>();
		String url = uri_strategies + "strategies";
		try {
			result = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/strategiesIntraDay")
	public Map<String, Strategy> getIntraDayStrategyResult() {
		Map<String, Strategy> result = new HashMap<>();
		String url = uri_strategies + "strategiesIntraDay";
		try {
			result = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;

	}
}
