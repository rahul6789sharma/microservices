package org.stocksrin.services.rest;

import java.util.Map;
import java.util.SortedSet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;

@RestController
@RequestMapping("/bnf")
public class BNFRESTController {

	// Define the log object for this class
	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/hello")
	public String sayHello() {
		return "bank Nifty HI";
	}

	@RequestMapping("/lastDataUpdated/{expiry}")
	public ResponseEntity<String> getlastDataUpdated(@PathVariable("expiry") String expiry) {

		String data = BankNiftyData.getlastDataUpdated(expiry);
		if (data != null) {
			return ResponseEntity.ok(BankNiftyData.getlastDataUpdated(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@RequestMapping("/iv/{expiry}/{strike}/{type}")
	public ResponseEntity<Double> getIV(@PathVariable("expiry") String expiry, @PathVariable("strike") double strike, @PathVariable("type") OptionType type) {
		return ResponseEntity.ok(BankNiftyData.getIV(strike, type, expiry));
	}

	@RequestMapping("/optionLtp/{expiry}/{strike}/{type}")
	public ResponseEntity<Double> getOptionLtp(@PathVariable("expiry") String expiry, @PathVariable("strike") double strike, @PathVariable("type") OptionType type) {
		return ResponseEntity.ok(BankNiftyData.getLtp(strike, type, expiry));
	}

	@RequestMapping("/spotPrice")
	public ResponseEntity<Double> getSpotPrice() {
		return ResponseEntity.ok(BankNiftyData.getBNFSpot());

	}

	@RequestMapping("/futurePrice")
	public ResponseEntity<Double> getFuturePrice() {
		return ResponseEntity.ok(BankNiftyData.getFuturePrice());
	}

	// http://ec2-35-154-48-199.ap-south-1.compute.amazonaws.com:8080/bnf/shortedExpiry
	@RequestMapping("/shortedExpiry")
	public ResponseEntity<SortedSet<String>> getShortedExpiry() {
		SortedSet<String> data = BankNiftyData.shortedExpiry;
		if (!data.isEmpty()) {
			return ResponseEntity.ok(BankNiftyData.shortedExpiry);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	// http://localhost:8080/bnf/optionModel
	@GetMapping("/optionModel/{expiry}")
	public ResponseEntity<OptionModles> getOptionModel(@PathVariable("expiry") String expiry) {
		System.out.println("expiry " + expiry);
		OptionModles optionModles = BankNiftyData.bnOptionData.get(expiry);
		if (optionModles != null) {
			return ResponseEntity.ok(BankNiftyData.bnOptionData.get(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/maxpain/{expiry}")
	public ResponseEntity<Double> getOptionmaxPain(@PathVariable("expiry") String expiry) {
		System.out.println("expiry " + expiry);
		Double data = BankNiftyData.maxPains.get(expiry);
		if (data != null) {
			return ResponseEntity.ok(BankNiftyData.maxPains.get(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	// for ui
	// http://localhost:8080/bnf/bankNiftyWeeklyOptionChain
	@GetMapping("/bankNiftyWeeklyOptionChain")
	public ResponseEntity<Map<String, OptionModles>> getOptionChain() {

		Map<String, OptionModles> data = BankNiftyData.bnOptionData;
		if (data != null) {
			return ResponseEntity.ok(data);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}
