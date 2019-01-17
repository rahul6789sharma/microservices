package org.stocksrin;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stocksrin.ai.NIftyAIData;
import org.stocksrin.ai.Straddle;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.participant.oidata.Util;

@SpringBootApplication
public class LiveDataCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveDataCollectorApplication.class, args);
	}

	@PostConstruct
	public void init() {

		try {
			loadParticipantOIDataOn();
			Map<String, Map<String, Straddle>> data = (Map<String, Map<String, Straddle>>) FileUtils.fromJson(FileUtils.lasFilePath(AppConstant.STOCKSRIN_AI));
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

	private static void loadParticipantOIDataOn() {
		try {

			String date2 = DateUtils.dateToString(new Date(), "MMMyyyy");
			String dir = AppConstant.FO_OI_DIR + date2;

			List<String> files = FileUtils.listFilesForFolder(new File(dir));
			System.out.println(files);
			for (String string : files) {
				Util.collectAllDateForDay(dir + File.separator + string);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
