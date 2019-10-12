package org.stocksrin;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.collector.option.data.InMemoryStrategyies;
import org.stocksrin.common.model.trade.Strategy;
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
	private BNFConsumeWebService bnfConsumeWebService;

	// http://13.127.1.60:8088/strategiesBuilder/hello
	@RequestMapping("/hello")
	public String sayHello() {

		Set<String> daily = InMemoryStrategyies.getStrategies().keySet();
		Set<String> intraday = InMemoryStrategyies.getStrategiesIntraDay().keySet();
		StringBuilder s = new StringBuilder();
		for (String string : daily) {
			s.append(string);
			s.append("\n");
		}

		for (String string : intraday) {
			s.append(string);
			s.append("\n");
		}

		return s.toString();
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
