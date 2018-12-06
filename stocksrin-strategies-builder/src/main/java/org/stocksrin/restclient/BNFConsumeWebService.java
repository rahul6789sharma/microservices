package org.stocksrin.restclient;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.ComparatorBasedOnDate;

@Controller
public class BNFConsumeWebService {

	private static final Logger log = LoggerFactory.getLogger(BNFConsumeWebService.class);

	@Value("${microservice.liveData.url}")
	private String uri;

	private RestTemplate restTemplate = new RestTemplate();

	public SortedSet<String> getAllExpiry() {

		String url = uri + "/bnf/shortedExpiry";

		Set<String> data = restTemplate.getForObject(url, Set.class);
		SortedSet<String> result = new TreeSet<>(new ComparatorBasedOnDate());
		result.addAll(data);
		return result;
	}

	public OptionModles getOptionModel(String expiry) {

		String url = uri + "/bnf/optionModel/" + expiry;

		OptionModles data = restTemplate.getForObject(url, OptionModles.class);
		return data;
	}

	public Double getSpotPrice() {
		String url = uri + "/bnf/spotPrice";

		Double data = restTemplate.getForObject(url, Double.class);
		return data;
	}

	public Double getOptionLtp(String expiry, double strike, OptionType optionType) {

		String url = uri + "/bnf/optionLtp/" + expiry + "/" + strike + "/" + optionType;

		Double data = restTemplate.getForObject(url, Double.class);
		return data;
	}

	public Double getIV(String expiry, double strike, OptionType optionType) {

		String url = uri + "/bnf/iv/" + expiry + "/" + strike + "/" + optionType;

		Double data = restTemplate.getForObject(url, Double.class);
		return data;
	}

	public String getLastDataUpdated(String expiry) {

		String url = uri + "/bnf/lastDataUpdated/" + expiry;
		
		String data = restTemplate.getForObject(url, String.class);
		return data;
	}
}