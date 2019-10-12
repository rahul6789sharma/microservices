package org.stocksrin.collector.option.data;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiveDataEveningUpdatedTask extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(LiveDataCollectortask.class);

	@Override
	public void run() {
		log.info("******* LiveDataEveningUpdatedTask Started**********");
		try {
			PriceUtils.fetchData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
