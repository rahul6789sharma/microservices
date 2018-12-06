package org.stocksrin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.common.model.app.health.MemoryStatus;
import org.stocksrin.monitors.AppMonitor;
import org.stocksrin.restclient.ConsumeWebService;

@RestController
public class Controller {

	// Define the log object for this class
	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${logging.file}")
	private String name;

	@Value("${url.app.stocksrin.data.downloader}")
	private String data_downloader_url;

	@Autowired
	ConsumeWebService consumeWebService;

	@RequestMapping("/app")
	public List<MemoryStatus> getUsedMemory() {

		List<MemoryStatus> memoryStatus = new ArrayList<>();
		memoryStatus.add(AppMonitor.getDataDowloaderemoryStatus());
		memoryStatus.add(AppMonitor.getLiveData_Collector());
		memoryStatus.add(AppMonitor.getStrategies_Builder());

		return memoryStatus;
	}

	@RequestMapping("/hello2")
	public String sayHello() {
		return "hello";
	}

}
