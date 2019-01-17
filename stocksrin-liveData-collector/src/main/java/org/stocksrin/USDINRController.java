package org.stocksrin;

import java.util.Map;
import java.util.SortedSet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.data.USDINRData;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;

@RestController
@RequestMapping("/usdinr")
public class USDINRController {

	
	@RequestMapping("/hello")
	public String sayHello() {
		return "USDINRController HI";
	}
	
	@RequestMapping("/lastDataUpdated/{expiry}")
	public ResponseEntity<String> getlastDataUpdated(@PathVariable("expiry") String expiry) {

		String data = USDINRData.getlastDataUpdated(expiry);
		if (data != null) {
			return ResponseEntity.ok(USDINRData.getlastDataUpdated(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping("/iv/{expiry}/{strike}/{type}")
	public ResponseEntity<Double> getIV(@PathVariable("expiry") String expiry, @PathVariable("strike") double strike, @PathVariable("type") OptionType type) {
		return ResponseEntity.ok(USDINRData.getIV(strike, type, expiry));
	}

	@RequestMapping("/optionLtp/{expiry}/{strike}/{type}")
	public ResponseEntity<Double> getOptionLtp(@PathVariable("expiry") String expiry, @PathVariable("strike") double strike, @PathVariable("type") OptionType type) {
		return ResponseEntity.ok(USDINRData.getLtp(strike, type, expiry));
	}

	@RequestMapping("/spotPrice")
	public ResponseEntity<Double> getSpotPrice() {
		return ResponseEntity.ok(USDINRData.getSpot());

	}

	// http://ec2-35-154-48-199.ap-south-1.compute.amazonaws.com:8080/bnf/shortedExpiry
	@RequestMapping("/shortedExpiry")
	public ResponseEntity<SortedSet<String>> getShortedExpiry() {
		SortedSet<String> data = USDINRData.shortedExpiry;
		if (!data.isEmpty()) {
			return ResponseEntity.ok(USDINRData.shortedExpiry);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	//http://localhost:8080/bnf/optionModel
	@GetMapping("/optionModel/{expiry}")
	public ResponseEntity<OptionModles> getOptionModel(@PathVariable("expiry") String expiry) {
		System.out.println("expiry " + expiry);
		OptionModles optionModles = USDINRData.optionData.get(expiry);
		if (optionModles != null) {
			return ResponseEntity.ok(USDINRData.optionData.get(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/maxpain/{expiry}")
	public ResponseEntity<Double> getOptionmaxPain(@PathVariable("expiry") String expiry) {
		System.out.println("expiry " + expiry);
		Double data = USDINRData.maxPains.get(expiry);
		if (data != null) {
			return ResponseEntity.ok(USDINRData.maxPains.get(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	// for ui
	//http://localhost:8080/bnf/bankNiftyWeeklyOptionChain
	@GetMapping("/bankNiftyWeeklyOptionChain")
	public ResponseEntity<Map<String, OptionModles>> getOptionChain() {

		Map<String, OptionModles> data = USDINRData.optionData;
		if (data != null) {
			return ResponseEntity.ok(data);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
