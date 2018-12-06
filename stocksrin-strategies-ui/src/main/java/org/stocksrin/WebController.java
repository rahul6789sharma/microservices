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
}