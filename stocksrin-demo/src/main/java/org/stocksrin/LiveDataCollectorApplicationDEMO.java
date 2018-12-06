package org.stocksrin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class LiveDataCollectorApplicationDEMO {

	public static void main(String[] args) {
		SpringApplication.run(LiveDataCollectorApplicationDEMO.class, args);
	}
}
