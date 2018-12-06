package org.stocksrin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.collector.option.data.InMemoryStrategyies;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.restclient.BNFConsumeWebService;

@RestController
@RequestMapping("/strategiesBuilder")
public class Controller {

	// Define the log object for this class
	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${logging.file}")
	private String name;
	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	BNFConsumeWebService bnfConsumeWebService;

	@RequestMapping("/hello2")
	public String sayHello() {

		String response = restTemplate.exchange("http://stocskrin-demo/ltp/", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
		}).getBody();

		System.out.println("Response Received as " + response);

		return response;
	}

	@RequestMapping("/strategies")
	public Map<String, Strategy> getStrategyResult() {
		return InMemoryStrategyies.getStrategies();
	}

	@RequestMapping("/strategiesIntraDay")
	public Map<String, Strategy> getIntraDayStrategyResult() {
		return InMemoryStrategyies.getStrategiesIntraDay();
	}
}
