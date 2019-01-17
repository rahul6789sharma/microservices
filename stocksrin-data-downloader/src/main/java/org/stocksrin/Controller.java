package org.stocksrin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	// Define the log object for this class
	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${logging.file}")
	private String name;

	@RequestMapping("/hello2")
	public String sayHello() {

		return "hello";
	}

}
