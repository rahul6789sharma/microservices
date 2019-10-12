package org.stocksrin;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stocksrin.ai.NIftyAIData;
import org.stocksrin.ai.Straddle;
import org.stocksrin.collector.option.data.PriceUtils;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.email.SendEmail;

@SpringBootApplication
public class LiveDataCollectorApplication {
	public static boolean status = false;

	public static void main(String[] args) {
		SpringApplication.run(LiveDataCollectorApplication.class, args);

	}

	public static void appWait() throws Exception {

		while (!LiveDataCollectorApplication.status) {
			try {
				PriceUtils.fetchData();
				System.out.println(
						"wating .. " + NiftyData.shortedExpiry.size() + ", " + BankNiftyData.shortedExpiry.size());
				if (!NiftyData.shortedExpiry.isEmpty() && !BankNiftyData.shortedExpiry.isEmpty()) {
					status = true;
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	@PostConstruct
	public void init() {
		try {
			DataLoaderOnStartUP.loadOIData();
			DataLoaderOnStartUP.loadParticipantOIDataOn();
			Map<String, Map<String, Straddle>> data = (Map<String, Map<String, Straddle>>) FileUtils
					.fromJson(FileUtils.lasFilePath(AppConstant.STOCKSRIN_AI));
			// System.out.println(data);
			NIftyAIData.add(data);
			// System.out.println(NIftyAIData.straddleData);
		} catch (Exception e) {
			throw new IllegalStateException("Liva-Data-Collector can not started");
		}
	}

	@PreDestroy
	public void destory() {
		System.out.println("*********** pre destory Construct ************");
		SendEmail.sentMail("!!CRITICAL!  shutdown ", " Liva-Data-Collector ", "Data-Downloader");
	}

}
