package org.stocksrin.services.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.intraday.OptionChainFileReader;

@RestController
@RequestMapping("/optionChainService")
public class OptionChainRestController {

	// http://13.127.1.60:8082/optionChainService/optionChain?expiry=12SEP2019
	// expiry
	@RequestMapping("/optionChain")
	public ResponseEntity<List<OptionModles>> getNiftyExpiryDayChain(@RequestParam("expiry") String expiry) throws Exception {
		
		List<OptionModles> o = OptionChainFileReader.getNiftyOptionChainByExpiry(expiry);
		if (o == null) {
			ResponseEntity.status(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(o);
	}
}
