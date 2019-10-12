package org.stocksrin.services.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.data.IntraDayData;
import org.stocksrin.common.model.intraday.AllIntraDayData;
import org.stocksrin.common.model.intraday.IntaDayData;
import org.stocksrin.common.model.intraday.IntraDayOptionModles;
import org.stocksrin.common.model.option.IntraDayOidata;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.intraday.OptionChainFileReader;

@RestController
@RequestMapping("/intaDayService")
public class IntraDayRestController {

	// http://13.127.1.60:8082/intaDayService/data
	// expiry
	@RequestMapping("/optionChain")
	public ResponseEntity<List<OptionModles>> getNiftyExpiryDayChain(@RequestParam("expiry") String expiry, @RequestParam("date") String date) throws Exception {
		System.out.println("expiry " + expiry);
		List<OptionModles> o = OptionChainFileReader.getNiftyOptionChainExpiryIntraday(expiry, date);
		return ResponseEntity.ok(o);
	}

	// http://13.127.1.60:8082/intaDayService/data
	@RequestMapping("/data")
	public ResponseEntity<AllIntraDayData> sayHello() {
		System.out.println(IntaDayData.allIntraDayData);
		if (IntaDayData.allIntraDayData == null) {
			return ResponseEntity.ok(new AllIntraDayData());
		}
		return ResponseEntity.ok(IntaDayData.allIntraDayData);
	}

	@GetMapping("/intraDayOptionChain/{delayDataInMins}")
	public ResponseEntity<IntraDayOidata> getIntraDayOptionChain(@PathVariable("delayDataInMins") int delayDataInMins) {
		Map<Integer, IntraDayOptionModles> data = null;
		if (delayDataInMins == 5) {
			data = IntraDayData.niftyOptionDataIntraDay5Min;
		} else if (delayDataInMins == 10) {
			data = IntraDayData.niftyOptionDataIntraDay10Min;
		} else if (delayDataInMins == 15) {
			data = IntraDayData.niftyOptionDataIntraDay15Min;
		}

		IntraDayOidata intraDayOidata = new IntraDayOidata();
		intraDayOidata.setOptionDataIntraDay(data);

		System.out.println("numbers " + data.keySet());
		if (intraDayOidata.getOptionDataIntraDay() != null) {
			return ResponseEntity.ok(intraDayOidata);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	public static void main(String[] args) {
		String response = "Nifty" + " <br>" + "---------------------" + "Call OI" + "1234";
		System.out.println(response);
	}
}
