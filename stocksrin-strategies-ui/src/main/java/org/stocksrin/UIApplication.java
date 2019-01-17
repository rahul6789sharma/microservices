package org.stocksrin;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stocksrin.email.SendEmail;

@SpringBootApplication

public class UIApplication {

	public static boolean status;

	public static void main(String[] args) {
		SpringApplication.run(UIApplication.class, args);
		System.out.println("******************* Started ****************");
		status = true;

	}

	public static void appWait() {
		while (!UIApplication.status) {
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
		SendEmail.sentMail("!!CRITICAL!  shutdown ", " UI ", "Data-Downloader");
	}
}
