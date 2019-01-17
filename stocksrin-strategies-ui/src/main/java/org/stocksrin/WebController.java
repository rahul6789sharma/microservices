package org.stocksrin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/bnfOI")
	public String bnfOI() {
		return "bnfOI";
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
}