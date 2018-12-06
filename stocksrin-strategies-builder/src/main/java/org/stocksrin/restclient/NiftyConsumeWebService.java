package org.stocksrin.restclient;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.ComparatorBasedOnDate;

@Controller
public class NiftyConsumeWebService {

	@Autowired
	private Environment env;

	public SortedSet<String> getAllExpiry() {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/shortedExpiry";
		RestTemplate restTemplate = new RestTemplate();
		Set<String> data = restTemplate.getForObject(uri, Set.class);
		SortedSet<String> result = new TreeSet<>(new ComparatorBasedOnDate());
		result.addAll(data);
		return result;
	}

	public OptionModles getOptionModel(String expiry) {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/optionModel/" + expiry;
		RestTemplate restTemplate = new RestTemplate();
		OptionModles data = restTemplate.getForObject(uri, OptionModles.class);
		return data;
	}

	public Double getSpotPrice() {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/spotPrice";
		RestTemplate restTemplate = new RestTemplate();
		Double data = restTemplate.getForObject(uri, Double.class);
		return data;
	}

	public Double getOptionLtp(String expiry, double strike, OptionType optionType) {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/optionLtp/" + expiry + "/" + strike + "/" + optionType;
		RestTemplate restTemplate = new RestTemplate();
		Double data = restTemplate.getForObject(uri, Double.class);
		return data;
	}

	public Double getIV(String expiry, double strike, OptionType optionType) {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/iv/" + expiry + "/" + strike + "/" + optionType;
		RestTemplate restTemplate = new RestTemplate();
		Double data = restTemplate.getForObject(uri, Double.class);
		return data;
	}

	public String getLastDataUpdated(String expiry) {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/lastDataUpdated/" + expiry;
		RestTemplate restTemplate = new RestTemplate();
		String data = restTemplate.getForObject(uri, String.class);
		return data;
	}
}
