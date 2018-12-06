package org.stocksrin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.utils.ComparatorBasedOnDate;

@RestController
@RequestMapping("/bnf")
public class BNFController {

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
		
		OptionModles data = restTemplate.getForObject(url2, OptionModles.class);
		Map<String, OptionModles> result = new HashMap<>();
		result.put(currentExpiry, data);
		return result;
	}

	@GetMapping("/strategies")
	public Map<String, Strategy> getStrategyResult() throws Exception {
		String url = uri_strategies + "strategies";
		Map<String, Strategy> result = restTemplate.getForObject(url, Map.class);
		return result;
	}
	

	@GetMapping("/strategiesIntraDay")
	public Map<String, Strategy> getIntraDayStrategyResult() throws Exception {
		String url = uri_strategies + "strategiesIntraDay";
		Map<String, Strategy> result = restTemplate.getForObject(url, Map.class);
		return result;
	}
}
