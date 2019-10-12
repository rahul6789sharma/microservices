package org.stocksrin.services.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.option.OptionModles;

@RestController
@RequestMapping("/optionChainRestController")
public class OptionChainRestController {

	@Value("${microservice.liveData.url}")
	private String uri;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/optionChain")
	public List<OptionModles> optionChain(@RequestParam("expiry") String expiry) {
		String url = uri + "/optionChainService/optionChain?expiry=" + expiry;
		System.out.println("url" + url);
		List<OptionModles> response = restTemplate.getForObject(url, List.class);
		return response;
	}

	@GetMapping("/optionChainIntraday")
	public List<OptionModles> optionChainIntraday(@RequestParam("expiry") String expiry, @RequestParam("date") String date) {
		String url = uri + "/intaDayService/optionChain?expiry=" + expiry + "&date=" + date;
		System.out.println("url" + url);
		List<OptionModles> response = restTemplate.getForObject(url, List.class);
		return response;
	}
}
