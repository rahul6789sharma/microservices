package org.stocksrin;

import java.util.Map;
import java.util.SortedSet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.ai.NIftyAIData;
import org.stocksrin.ai.Straddle;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;

@RestController
@RequestMapping("/nifty")
public class NiftyController {

	// Define the log object for this class
	//private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/hello")
	public String sayHello() {
		return "Nifty HI";
	}

	@RequestMapping("/straddleData/{dte}")
	public  Map<String, Straddle> straddleData(@PathVariable("dte") String dte) {
		return NIftyAIData.getData(dte);
	}

	
	@RequestMapping("/lastDataUpdated/{expiry}")
	public ResponseEntity<String> getlastDataUpdated(@PathVariable("expiry") String expiry) {
		return ResponseEntity.ok(NiftyData.getlastDataUpdated(expiry));
	}

	@RequestMapping("/iv/{expiry}/{strike}/{type}")
	public ResponseEntity<Double> getIV(@PathVariable("expiry") String expiry, @PathVariable("strike") double strike, @PathVariable("type") OptionType type) {
		return ResponseEntity.ok(NiftyData.getIV(strike, type, expiry));
	}

	@RequestMapping("/optionLtp/{expiry}/{strike}/{type}")
	public ResponseEntity<Double> getOptionLtp(@PathVariable("expiry") String expiry, @PathVariable("strike") double strike, @PathVariable("type") OptionType type) {
		return ResponseEntity.ok(NiftyData.getLtp(strike, type, expiry));
	}

	@RequestMapping("/spotPrice")
	public ResponseEntity<Double> getSpotPrice() {
		return ResponseEntity.ok(NiftyData.getNFSpot());

	}

	@RequestMapping("/shortedExpiry")
	public ResponseEntity<SortedSet<String>> getShortedExpiry() {
		SortedSet<String> data = NiftyData.shortedExpiry;
		if (!data.isEmpty()) {
			return ResponseEntity.ok(NiftyData.shortedExpiry);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/optionModel/{expiry}")
	public ResponseEntity<OptionModles> getOptionModel(@PathVariable("expiry") String expiry) {
		OptionModles optionModles = NiftyData.optionData.get(expiry);
		if (optionModles != null) {
			return ResponseEntity.ok(NiftyData.optionData.get(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/maxpain/{expiry}")
	public ResponseEntity<Double> getOptionmaxPain(@PathVariable("expiry") String expiry) {
		Double data = NiftyData.maxPains.get(expiry);
		if (data != null) {
			return ResponseEntity.ok(NiftyData.maxPains.get(expiry));
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
