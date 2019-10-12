package org.stocksrin.services.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.trade.Strategy2;

@RestController
@RequestMapping("/strategies")
public class StrategiesREST {

	private static final Logger log = LoggerFactory.getLogger(BNFController.class);

	@Value("${microservice.strategies-builder.url}")
	private String uri_strategies;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/strategies")
	public Map<String, Strategy2> getStrategiesv2() {
		Map<String, Strategy2> result = new HashMap<>();
		String url = uri_strategies + "strategiesv2";
		try {
			result = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
