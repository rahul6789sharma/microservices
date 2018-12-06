package org.stocksrin.monitors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.app.health.AppStatus;
import org.stocksrin.common.model.app.health.MemoryStatus;
import org.stocksrin.common.model.app.health.Status;
import org.stocksrin.email.SendEmail;
import org.stocksrin.restclient.ConsumeWebService;

@Controller
public class AppMonitor implements Runnable {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static MemoryStatus dataDowloaderemoryStatus = new MemoryStatus("Data-Downloader");
	private static MemoryStatus liveData_Collector = new MemoryStatus("LiveData-Collector");
	private static MemoryStatus strategies_Builder = new MemoryStatus("Strategies_Builder");

	@Autowired
	private ConsumeWebService consumeWebService;

	@Value("${url.app.stocksrin.data.downloader}")
	private String data_downloader_url;

	@Value("${url.app.stocksrin.liveData.collector}")
	private String liveData_collector_url;

	@Value("${url.app.stocksrin.strategies.builder}")
	private String strategies_builder_url;

	private static int count = 0;

	public void dataDownloaderMonitor(String appurl, MemoryStatus memoryStatus) {

		Status st = consumeWebService.getUsedMemory(appurl);
		memoryStatus.setAppStatus(st.getAapStatus());
		memoryStatus.setCurrent(st.getValue());

		if (!st.getAapStatus().equals(AppStatus.UP) && memoryStatus.getEmailCount() < 5) {
			SendEmail.sentMail(memoryStatus.getAppStatus() + " " + memoryStatus.getAppName(), "", "App Monitor");
		}

		if (count == 0 && st.getAapStatus().equals(AppStatus.UP)) {
			memoryStatus.setMax(st.getValue());
			memoryStatus.setMin(st.getValue());
			count++;

		}

		if (st.getAapStatus().equals(AppStatus.UP)) {
			memoryStatus.setEmailCount(0);
		} else {
			int ecount = memoryStatus.getEmailCount();
			memoryStatus.setEmailCount(ecount + 1);
			count = 0;
		}

		if (memoryStatus.getMax() < st.getValue()) {
			memoryStatus.setMax(st.getValue());
		}

		if (memoryStatus.getMin() > st.getValue()) {
			memoryStatus.setMin(st.getValue());
		}

		log.info("status " + memoryStatus.getAppName() + ":" + st);
		log.info(memoryStatus.toString());
	}

	@Override
	public void run() {
		try {
			dataDownloaderMonitor(data_downloader_url, dataDowloaderemoryStatus);
			dataDownloaderMonitor(liveData_collector_url, liveData_Collector);
			dataDownloaderMonitor(strategies_builder_url, strategies_Builder);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static MemoryStatus getDataDowloaderemoryStatus() {
		return dataDowloaderemoryStatus;
	}

	public static MemoryStatus getLiveData_Collector() {
		return liveData_Collector;
	}

	public static MemoryStatus getStrategies_Builder() {
		return strategies_Builder;
	}

}
