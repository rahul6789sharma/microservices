package org.stocksrin;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stocksrin.email.SendEmail;

@SpringBootApplication
public class DataDownloaderMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataDownloaderMSApplication.class, args);
	}

	@PreDestroy
	public void destory() {
		SendEmail.sentMail("!!! CRITICAL !!!  shutdown ", " Data-Downloader ", "Data-Downloader");
	}
}
