package org.stocksrin.monitors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerEveningService {

	@Autowired
	private AppMonitor appMonitor;
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	@PostConstruct
	public void init() {
		System.out.println("SchedulerEveningService starting");
		scheduler.scheduleAtFixedRate(appMonitor, 1, 15, TimeUnit.MINUTES);
		System.out.println("SchedulerEveningService started");
	}
}
