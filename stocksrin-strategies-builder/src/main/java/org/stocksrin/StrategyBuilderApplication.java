package org.stocksrin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class StrategyBuilderApplication {

	public static boolean status;

	public static void main(String[] args) {
		SpringApplication.run(StrategyBuilderApplication.class, args);
		System.out.println("******************* Started ****************");
		status = true;

	}

	public static void appWait() {
		while (!StrategyBuilderApplication.status) {
			try {
				System.out.println("wating");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
