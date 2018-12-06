package org.stocksrin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${logging.file}")
	private String name;

	@RequestMapping("/ltp")
	public Double getltp() {
		System.out.println("called");
		return 100.0;
	}
}