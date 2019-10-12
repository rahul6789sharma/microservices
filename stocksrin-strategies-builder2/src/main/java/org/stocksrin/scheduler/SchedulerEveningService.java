package org.stocksrin.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stocksrin.common.utils.Scheduler;
import org.stocksrin.result.writer.ResultWriterTask;
import org.stocksrin.strategy.builders.task.DBCleanTask;
import org.stocksrin.strategy.builders.task.IntraDayBuilderTask;
import org.stocksrin.strategy.builders.task.PositionalBuilderTask;
import org.stocksrin.updater.StrategyPriceUpdate;

@Service
public class SchedulerEveningService {

	@Autowired(required = true)
	private PositionalBuilderTask positionalBuilderTask;

	@Autowired(required = true)
	private IntraDayBuilderTask intraDayBuilderTask;

	@Autowired(required = true)
	private StrategyPriceUpdate strategyPriceUpdate;

	@Autowired(required = true)
	private ResultWriterTask resultWriterTask;

	@Autowired(required = true)
	private DBCleanTask dbCleanTask;

	@PostConstruct
	public void init() {

		// dynamica
		try {
			Scheduler.scheduleTask(9, 22, intraDayBuilderTask);
			Scheduler.scheduleTask(9, 23, strategyPriceUpdate);
			Scheduler.scheduleTask(15, 10, positionalBuilderTask);
			Scheduler.scheduleTask(15, 30, resultWriterTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Scheduler.scheduleTask(23, 30, dbCleanTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
