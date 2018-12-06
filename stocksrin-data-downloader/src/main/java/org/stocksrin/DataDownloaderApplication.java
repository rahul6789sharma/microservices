package org.stocksrin;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stocksrin.email.SendEmail;

@SpringBootApplication
public class DataDownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataDownloaderApplication.class, args);
	}


	@PreDestroy
	public void destory() {
		System.out.println("*********** pre destory Construct ************");
		SendEmail.sentMail("!!CRITICAL!  shutdown ", " Data-Downloader " ,"Data-Downloader");
	}
}
