package org.stocksrin.services.schedule;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stocksrin.ai.AIWriterTask;
import org.stocksrin.collector.option.data.LiveDataCollectortask;
import org.stocksrin.collector.option.data.LiveDataEveningUpdatedTask;
import org.stocksrin.collector.option.data.LiveIndicicesCollector;
import org.stocksrin.common.utils.Scheduler;
import org.stocksrin.intraday.IntraDataCollector;
import org.stocksrin.intraday.IntraDayAllDataTask;
import org.stocksrin.intraday.OptionChainDownloader;
import org.stocksrin.notifications.BankNiftyNotificationTask;
import org.stocksrin.notifications.NiftyNotificationTask;
import org.stocksrin.option.data.store.NiftyOptionDataDownloader;
import org.stocksrin.option.downloader.ChainDataFetcheture;
import org.stocksrin.participant.oidata.ParticipantOIReaderTask;

@Service
public class SchedulerEveningService {

	@Autowired
	// download option chain for all nifty 50 and fno stocks
	private NiftyOptionDataDownloader niftyOptionDataDownloader;

	@Autowired
	private LiveIndicicesCollector liveIndicicesCollector;

	@Autowired
	private ParticipantOIReaderTask participantOIReaderTask;

	@Autowired
	private ChainDataFetcheture expiryDatesUtils;

	@Autowired
	private IntraDataCollector intraDataCollector;

	@Autowired
	private LiveDataCollectortask liveDataCollectortask;

	@Autowired
	private OptionChainDownloader optionChainDownloader;

	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {

		log.info("######### SchedulerService Starting ############");

		try {
			// new boz we nned new instance daily
			Scheduler.scheduleTask(9, 20, new IntraDayAllDataTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// new boz we nned new instance daily
			Scheduler.scheduleTask(9, 20, optionChainDownloader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 18, liveDataCollectortask);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 30, intraDataCollector);
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
			// Scheduler.scheduleTask(9, 5, liveUSDINRDataCollector);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Scheduler.scheduleTask(9, 5, new USDINRNotificationTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(9, 16, liveIndicicesCollector);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Scheduler.scheduleTask(9, 25, new AI());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(15, 30, new AIWriterTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(20, 30, participantOIReaderTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scheduler.scheduleTask(17, 30, expiryDatesUtils);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// participantOIReaderTask

		log.info("######### SchedulerService Started ############");
	}
}