package org.stocksrin.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stocksrin.common.utils.Scheduler;
import org.stocksrin.strategies.automation.InMemeoryStrategyBuilder;
import org.stocksrin.strategies.automation.InMemeoryStrategyDataUpdater;
import org.stocksrin.strategies.builder.file.BNFStrategyBuilderDaily;
import org.stocksrin.strategies.builder.file.IntraDayStrategyBuilder2;
import org.stocksrin.strategies.builder.file.IntraDayStrategyFileBuilder;

@Service
public class SchedulerEveningService {

	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = true)
	private IntraDayStrategyFileBuilder intraDayStrategyFileBuilder;

	@Autowired(required = true)
	private BNFStrategyBuilderDaily bnfStrategyBuilderDaily;

	@Autowired(required = true)
	private InMemeoryStrategyDataUpdater inMemeoryStrategyDataUpdater;

	@Autowired(required = true)
	private IntraDayStrategyBuilder2 intraDayStrategyBuilder2;

	@PostConstruct
	public void init() {

		log.info("######### SchedulerService Starting ############");

		// second Session
		try {
			Scheduler.scheduleTask(14, 35, intraDayStrategyBuilder2);
			Scheduler.scheduleTask(14, 50, intraDayStrategyBuilder2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			Scheduler.scheduleTask(9, 20, intraDayStrategyFileBuilder);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// update the price of in Memories startegies
			// Scheduler.scheduleTask(9, 22, inMemeoryStrategyDataUpdaterUSDINR);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// update the price of in Memories startegies
			Scheduler.scheduleTask(9, 22, inMemeoryStrategyDataUpdater);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// read files from dir and update startegy in memory
			Scheduler.scheduleTask(9, 23, new InMemeoryStrategyBuilder());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(15, 25, bnfStrategyBuilderDaily);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Scheduler.scheduleTask(10, 10, usdINRStrategyDailyBuilder);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("######### SchedulerService Started ############");
	}
}
