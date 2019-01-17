package org.stocksrin.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stocksrin.ai.AI;
import org.stocksrin.ai.AIWriterTask;
import org.stocksrin.collector.option.data.LiveDataCollectortask;
import org.stocksrin.collector.option.data.LiveDataEveningUpdatedTask;
import org.stocksrin.collector.option.data.LiveIndicicesCollector;
import org.stocksrin.collector.option.data.LiveUSDINRDataCollector;
import org.stocksrin.common.utils.Scheduler;
import org.stocksrin.notifications.BankNiftyNotificationTask;
import org.stocksrin.notifications.NiftyNotificationTask;
import org.stocksrin.notifications.USDINRNotificationTask;
import org.stocksrin.option.data.store.NiftyOptionDataDownloader;
import org.stocksrin.participant.oidata.ParticipantOIReaderTask;

@Service
public class SchedulerEveningService {

	@Autowired
	private NiftyOptionDataDownloader niftyOptionDataDownloader;

	@Autowired
	private LiveUSDINRDataCollector liveUSDINRDataCollector;

	@Autowired
	private LiveIndicicesCollector liveIndicicesCollector;

	@Autowired
	private ParticipantOIReaderTask participantOIReaderTask;

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

		try {
			Scheduler.scheduleTask(19, 01, new LiveDataEveningUpdatedTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(19, 10, niftyOptionDataDownloader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 5, liveUSDINRDataCollector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Scheduler.scheduleTask(9, 5, new USDINRNotificationTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 16, liveIndicicesCollector);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 25, new AI());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(15, 30, new AIWriterTask());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Scheduler.scheduleTask(20, 30,participantOIReaderTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//participantOIReaderTask

		log.info("######### SchedulerService Started ############");
	}
}