package org.stocksrin.indices.data;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.model.NSEIndice;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

public class IndicesDownloaderTask extends TimerTask {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run() {
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			try {
				List<NSEIndice> niftys = new ArrayList<>();
				boolean status = true;
				int count = 0;
				while (niftys.isEmpty() && status) {
					log.info("Retrying .." + count);
					niftys = NiftyIndicesDataColloctor.getAllData();
					if (count == 20) {
						status = false;
					}
					count++;
					Thread.sleep(100000);
				}
				// List<NSEIndice> niftys = NiftyIndicesDataColloctor.getAllData();
				for (NSEIndice data : niftys) {

					if (data.getName().equals("NIFTY 50")) {
						CommonUtils.appendData(data.toCsv(), AppConstant.STOCKSRIN_INDICES_NIFTY);
						log.info("Nifty50 daily data downloaded : " + data);
						SendEmail.sentMail("Nifty Data:", " " + data, "Data-Downloader");

					} else if (data.getName().equals("NIFTY BANK")) {
						CommonUtils.appendData(data.toCsv(), AppConstant.STOCKSRIN_INDICES_BANK_NIFTY);
						log.info("BNF daily data downloaded : " + data);
						SendEmail.sentMail("BNF Data:", " " + data, "Data-Downloader");
					}
				}
			} catch (Exception e) {
				SendEmail.sentMail("CRITICAL! Index Data is not downloaded !", "ERROR " + e.getMessage(), "Data-Downloader");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		IndicesDownloaderTask IndicesDownloaderTask = new IndicesDownloaderTask();
		IndicesDownloaderTask.run();
	}
}

// need to test on holiday