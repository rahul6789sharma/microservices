package org.stocksrin;

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
import org.stocksrin.common.model.ParticipantOIModle;

@RestController
@RequestMapping("/oi")
public class OIController {

	private static final Logger log = LoggerFactory.getLogger(BNFController.class);

	@Value("${microservice.liveData.url}")
	private String uri;

	private RestTemplate restTemplate = new RestTemplate();

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
}
