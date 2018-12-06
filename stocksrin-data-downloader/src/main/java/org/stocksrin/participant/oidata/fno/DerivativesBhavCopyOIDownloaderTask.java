package org.stocksrin.participant.oidata.fno;

import java.io.File;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.common.utils.UnzipUtility;
import org.stocksrin.email.SendEmail;

public class DerivativesBhavCopyOIDownloaderTask extends TimerTask {
	private static final Logger log = LoggerFactory.getLogger(DerivativesBhavCopyOIDownloaderTask.class);
	private static final int RETRY = 5;

	public static void main(String[] args) {
		download();
	}

	@Override
	public void run() {
		download();
	}

	public static void download() {

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			try {
				String yr = DateUtils.getCurrentYear();
				String month = DateUtils.getCurrentMonth().toUpperCase();
				String date = DateUtils.getCurrentDatetwoDigit();
				String fileName = "fo" + date + month + yr + "bhav.csv.zip";
				String urlPart = yr + "/" + month + "/" + fileName;
				String yearDir = AppConstant.STOCKSRIN_NSE_CONF_DIR_DERIVATIVES_OI + yr;
				String url = AppConstant.NSE_DERIVATIVES_OI_URL + urlPart;

				String completedir = AppConstant.STOCKSRIN_NSE_CONF_DIR_DERIVATIVES_OI + yr + File.separator;
				String completeFileName = completedir + fileName;
				boolean status = false;
				int retryCounter = 0;
				url = "https://www.nseindia.com/content/historical/DERIVATIVES/2018/DEC/fo05DEC2018bhav.csv.zip";
				while (!status && retryCounter < RETRY) {
					System.out.println("retrying.. " +retryCounter);
					System.out.println("url.." + url);
					System.out.println("completeFileName.." + completeFileName);
					try {
						status = FileUtils.downloadFile(url, completeFileName);
						
					} catch (Exception e) {
						SendEmail.sentMail("CRITICAL! Retry Bhav Copy is not downloaded", "url " + url + "\n completeFileName" + completeFileName, "Data-Downloader");
						e.printStackTrace();
				// retry after 5 min
						Thread.sleep(500000);
					}

					System.out.println("retrying.. " + status);
					retryCounter++;
				}

				FileUtils.makeDir(yearDir);
				log.info("OI Bhav Copy is downloaded url " + url + "\n completeFileName" + completeFileName);
				SendEmail.sentMail("OI Bhav Copy is downloaded", "url " + url + "\n completeFileName" + completeFileName, "Data-Downloader");
				if (status) {
					UnzipUtility.unzip(completeFileName, completedir);
					FileUtils.delete(completeFileName);
					log.info("Deleted " + completeFileName);
				} else {
					SendEmail.sentMail("CRITICAL! Bhav Copy is not downloaded", "url " + url + "\n completeFileName" + completeFileName, "Data-Downloader");
				}
			} catch (Exception e) {
				e.printStackTrace();
				SendEmail.sentMail("CRITICAL! Bhav Copy is not downloaded", "ERROR ", "Data-Downloader");
			}
		}
	}

}