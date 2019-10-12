package org.stocksrin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

	@RequestMapping(value = "/login")
	public String login(@RequestParam String name) {
		System.out.println("name :" + name);
		return "login";
	}

	@RequestMapping(value = "/ads.txt")
	public String ads() {
		return "ads.txt";
	}
	
	@RequestMapping(value = "/niftyOptionChainIntraDay")
	public String niftyOptionChainIntraDay() {
		return "niftyOptionChainIntraDay";
	}
	
	@RequestMapping(value = "/niftyOptionChainDaily")
	public String niftyOptionChainDaily() {
		return "niftyOptionChainDaily";
	}

	@RequestMapping(value = "/portfolioI")
	public String dbIntradayStrategy() {
		return "portfolioI";
	}

	@RequestMapping(value = "/portfolioO")
	public String dbovernightStrategy() {
		return "portfolioO";
	}

	@RequestMapping(value = "/niftyoc")
	public String niftyOptioncahin() {
		return "niftyoc";
	}

	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/bnfOI")
	public String bnfOI() {
		return "bnfOI";
	}

	@RequestMapping(value = "/portfolioIdayv2")
	public String portfolioIdayv2() {
		return "portfolioIdayv2";
	}

	@RequestMapping(value = "/portfoliov2")
	public String portfoliov2() {
		return "portfoliov2";
	}

	@RequestMapping(value = "/portfolio")
	public String portfolio() {
		return "portfolio";
	}

	@RequestMapping(value = "/portfolio2")
	public String portfolio2() {
		return "portfolio2";
	}

	@RequestMapping(value = "/nfOI")
	public String nfOI() {
		return "nfOI";
	}

	@RequestMapping(value = "/rangeCalculatorSD")
	public String rangeCalculatorSD() {
		return "rangeCalculatorSD";
	}

	@RequestMapping(value = "/training")
	public String training() {
		return "training";
	}

	@RequestMapping(value = "/poi")
	public String poi() {
		return "poi";
	}

	@RequestMapping(value = "/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping(value = "/niftyChain")
	public String niftyChain() {
		return "niftyChain";
	}

	@RequestMapping(value = "/poi2")
	public String poi2() {
		return "poi2";
	}

	@RequestMapping(value = "/iday")
	public String iday() {
		return "iday";
	}

	@RequestMapping(value = "/iday2")
	public String iday2() {
		return "iday2";
	}
}