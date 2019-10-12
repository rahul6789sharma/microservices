package org.stocksrin.services.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.strategy.db.model.StrategyEntity;

@RestController
@RequestMapping("/dbStrategyRestController")
public class DBStrategyRestController {

	private static final Logger log = LoggerFactory.getLogger(BNFController.class);

	@Value("${microservice.db-strategies-builder.url}")
	private String uri;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/intadayStrategies")
	public List<StrategyEntity> getStrategyResult() {
		List<StrategyEntity> result = new ArrayList<>();
		String url = uri + "strategies/intraday";
		try {
			result = restTemplate.getForObject(url, List.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/overnightStrategies")
	public List<StrategyEntity> getovernigthResult() {
		List<StrategyEntity> result = new ArrayList<>();
		String url = uri + "strategies/overnight";
		try {
			result = restTemplate.getForObject(url, List.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
