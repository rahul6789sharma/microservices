package org.stocksrin.stocksrinEurekaServe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StocksrinEurekaServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksrinEurekaServeApplication.class, args);
	}
}
