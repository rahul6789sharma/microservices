package org.stocksrin.intraday;

import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.option.AdvanceDecline;
import org.stocksrin.common.utils.AppConstant;

public class IntraDayAdvanceDeclinedData {

	private static String url = AppConstant.Live_Market_URL;
	private static RestTemplate restTemplate = new RestTemplate();

	public static AdvanceDecline collectData() throws Exception {
		String result = restTemplate.getForObject(url, String.class);
		return AdvanceDecline.parse(result);
	}

}
