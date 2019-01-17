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

	public SortedSet<String> getAllExpiry() throws Exception {
		SortedSet<String> result = new TreeSet<>(new ComparatorBasedOnDate());
		String url = uri + "/bnf/shortedExpiry";
		try {
			Set<String> data = restTemplate.getForObject(url, Set.class);
			result.addAll(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return result;
	}

	public OptionModles getOptionModel(String expiry) throws Exception {
		OptionModles data = null;
		String url = uri + "/bnf/optionModel/" + expiry;
		try {
			data = restTemplate.getForObject(url, OptionModles.class);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getSpotPrice() throws Exception {
		String url = uri + "/bnf/spotPrice";
		Double data = null;
		try {
			data = restTemplate.getForObject(url, Double.class);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getOptionLtp(String expiry, double strike, OptionType optionType) throws Exception {

		String url = uri + "/bnf/optionLtp/" + expiry + "/" + strike + "/" + optionType;
		Double data = null;
		try {
			data = restTemplate.getForObject(url, Double.class);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public Double getIV(String expiry, double strike, OptionType optionType) throws Exception {

		String url = uri + "/bnf/iv/" + expiry + "/" + strike + "/" + optionType;
		Double data = null;
		try {
			data = restTemplate.getForObject(url, Double.class);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}

	public String getLastDataUpdated(String expiry) throws Exception {

		String url = uri + "/bnf/lastDataUpdated/" + expiry;
		String data = null;
		try {
			data = restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("Error url :" + uri);
		}
		return data;
	}
}