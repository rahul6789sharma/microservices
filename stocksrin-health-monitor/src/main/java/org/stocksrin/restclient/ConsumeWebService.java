package org.stocksrin.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.stocksrin.common.model.app.health.AppStatus;
import org.stocksrin.common.model.app.health.Measurements;
import org.stocksrin.common.model.app.health.Memory;
import org.stocksrin.common.model.app.health.Status;

@Controller
public class ConsumeWebService {

	private static final Logger log = LoggerFactory.getLogger(ConsumeWebService.class);

	private RestTemplate restTemplate = new RestTemplate();

	public Status getUsedMemory(String appurl) {
		Status status = new Status("jvm.memory.used");

		try {
			String url = appurl + "/actuator/metrics/jvm.memory.used";
			Memory data = restTemplate.getForObject(url, Memory.class);

			Measurements[] measurements = data.getMeasurements();
			Measurements measurement = measurements[0];
			Double bytes = Double.parseDouble(measurement.getValue());
			double mb = bytes / (1024.0 * 1024.0);
			status.setValue(mb);
			status.setAapStatus(AppStatus.UP);
		} catch (Exception e) {
			if (e instanceof org.springframework.web.client.ResourceAccessException) {
				status.setAapStatus(AppStatus.DOWN);
			} else {
				e.printStackTrace();
				status.setAapStatus(AppStatus.UNKNOWN);
			}
			// e.printStackTrace();
		}
		return status;

	}
}