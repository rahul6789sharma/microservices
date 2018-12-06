package org.stocksrin.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.stocksrin.collector.option.data.LiveDataCollectortask;
import org.stocksrin.common.utils.Scheduler;
import org.stocksrin.notifications.BankNiftyNotificationTask;
import org.stocksrin.notifications.NiftyNotificationTask;

@Service
public class SchedulerEveningService {

	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {

		log.info("######### SchedulerService Starting ############");

		try {
			Scheduler.scheduleTask(9, 18, new LiveDataCollectortask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 20, new BankNiftyNotificationTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 21, new NiftyNotificationTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("######### SchedulerService Started ############");
	}
}