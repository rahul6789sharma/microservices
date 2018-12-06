package org.stocksrin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
