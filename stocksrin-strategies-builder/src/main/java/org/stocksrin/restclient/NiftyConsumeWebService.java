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
public class NiftyConsumeWebService implements RestService{

	@Autowired
	private Environment env;

	public SortedSet<String> getAllExpiry() throws Exception {
		SortedSet<String> result = new TreeSet<>(new ComparatorBasedOnDate());
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/shortedExpiry";
		try {
			RestTemplate restTemplate = new RestTemplate();
			Set<String> data = restTemplate.getForObject(uri, Set.class);
			result.addAll(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return result;
	}

	public OptionModles getOptionModel(String expiry) throws Exception {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/optionModel/" + expiry;
		OptionModles data = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			data = restTemplate.getForObject(uri, OptionModles.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getFuturePrice() throws Exception {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/futurePrice";
		Double data = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			data = restTemplate.getForObject(uri, Double.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getSpotPrice() throws Exception {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/spotPrice";
		Double data = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			data = restTemplate.getForObject(uri, Double.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getOptionLtp(String expiry, double strike, OptionType optionType) throws Exception {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/optionLtp/" + expiry + "/" + strike + "/" + optionType;
		Double data = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			// System.out.println("uri " + uri);
			data = restTemplate.getForObject(uri, Double.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getIV(String expiry, double strike, OptionType optionType) throws Exception {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/iv/" + expiry + "/" + strike + "/" + optionType;
		Double data = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			data = restTemplate.getForObject(uri, Double.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public String getLastDataUpdated(String expiry) throws Exception {
		String uri = env.getProperty("microservice.liveData.url");
		uri = uri + "/nifty/lastDataUpdated/" + expiry;
		String data = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			data = restTemplate.getForObject(uri, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}
}
