package org.stocksrin.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.stocksrin.common.utils.Scheduler;
import org.stocksrin.indices.data.IndicesDownloaderTask;
import org.stocksrin.participant.oidata.ParticapentFnoDataDownloaderTask;
import org.stocksrin.participant.oidata.cash.FIIDIICashMarketTask;
import org.stocksrin.participant.oidata.fno.DerivativesBhavCopyOIDownloaderTask;

@Service
public class SchedulerEveningService {

	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {

		log.info("######### SchedulerEveningService Starting ############");

		try {
			Scheduler.scheduleTask(17, 31, new IndicesDownloaderTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(19, 31, new FIIDIICashMarketTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(20, 10, new DerivativesBhavCopyOIDownloaderTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(19, 55, new ParticapentFnoDataDownloaderTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("######### SchedulerEveningService Started ############");
	}
}
