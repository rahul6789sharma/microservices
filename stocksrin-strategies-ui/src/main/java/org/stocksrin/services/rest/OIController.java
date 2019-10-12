package org.stocksrin.services.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.OptionChainOIData;
import org.stocksrin.common.model.ParticipantOIModle;
import org.stocksrin.common.model.intraday.AllIntraDayData;
import org.stocksrin.common.model.option.OptionModles;

@RestController
@RequestMapping("/oi")
public class OIController {

	private static final Logger log = LoggerFactory.getLogger(BNFController.class);

	@Value("${microservice.liveData.url}")
	private String uri;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/expiryDayoptionChain")
	public List<OptionModles> expirydayoptionChain() {
		String url = uri + "/intaDayService/optionChain?expiry=12SEP2019";
		List<OptionModles> response = restTemplate.getForObject(url, List.class);

		return response;
	}
	
	@GetMapping("/intraDayData")
	public AllIntraDayData getIntraDayData() {
		String url = uri + "/intaDayService/data/";
		AllIntraDayData response = restTemplate.getForObject(url, AllIntraDayData.class);

		return response;
	}

	@GetMapping("/data")
	public Map<String, List<ParticipantOIModle>> getStrategyResult() {
		Map<String, List<ParticipantOIModle>> result = new HashMap<>();
		String url = uri + "/oi/data";
		try {
			System.out.println("uri : " + uri);
			result = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/niftyOIData")
	public Map<String, List<OptionChainOIData>> getniftyOIDataResult() {
		Map<String, List<OptionChainOIData>> result = new HashMap<>();
		String url = uri + "/oi/niftyOIData";
		try {
			System.out.println("uri : " + uri);
			result = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/bnfOIData")
	public Map<String, List<OptionChainOIData>> getbnfOIDataResult() {
		Map<String, List<OptionChainOIData>> result = new HashMap<>();
		String url = uri + "/oi/niftyOIData";
		try {
			System.out.println("uri : " + uri);
			result = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.error("Error, URL  " + url + " : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
