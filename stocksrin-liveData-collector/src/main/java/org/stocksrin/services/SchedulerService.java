package org.stocksrin.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {
		log.info("######### SchedulerService Started ############");

		/*try {
			Scheduler.scheduleTask(9, 0, new WishTask("Hello Morning 9:00"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(21, 0, new WishTask("Hello Evening 21:00"));
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}
}
