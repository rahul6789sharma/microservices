package org.stocksrin.restclient;

import org.springframework.web.client.RestTemplate;

public class ConsumeWebService {

	public static void getEmployees() {
		final String uri = "http://localhost:8080/bnf/shortedExpiry";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		System.out.println("result" + result);

	}
}