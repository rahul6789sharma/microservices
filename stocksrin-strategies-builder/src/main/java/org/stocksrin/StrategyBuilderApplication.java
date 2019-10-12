package org.stocksrin;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stocksrin.email.SendEmail;

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

				e.printStackTrace();
			}
		}
	}

	@PreDestroy
	public void destory() {
		System.out.println("*********** pre destory Construct ************");
		SendEmail.sentMail("!!CRITICAL!  shutdown ", " Strategy-Builder " ,"Strategy-Builder");
	}
}
