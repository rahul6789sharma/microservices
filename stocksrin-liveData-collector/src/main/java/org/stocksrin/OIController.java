package org.stocksrin;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.model.ParticipantOIModle;
import org.stocksrin.participant.oidata.ParticapentOIData;

@RestController
@RequestMapping("/oi")
public class OIController {

	@RequestMapping("/hello")
	public String sayHello() {
		return "OIController";
	}

	@RequestMapping("/data")
	public Map<String, List<ParticipantOIModle>> getDat() {
		return ParticapentOIData.data;
	}
}
