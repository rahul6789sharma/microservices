package org.stocksrin.reports.delivery;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.model.VolatilitySymbol;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

// download 'https://www.nseindia.com/products/content/sec_bhavdata_full.csv'
//delivery Percintage report
public class DeliveryReoprtDownloader extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(DeliveryReoprtDownloader.class);
	private static final int RETRY = 5;
	private static String bhavCopyUrl = AppConstant.NSE_bhavdata_URL;

	public static void main(String[] args) throws Exception {
		DeliveryReoprtDownloader deliveryReoprtDownloader = new DeliveryReoprtDownloader();
		deliveryReoprtDownloader.run();

	}

	@Override
	public void run() {
		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				deliveryReportDownload();
				voalatilityReportDownload();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void voalatilityReportDownload() throws InterruptedException {

		boolean status = false;
		int retryCounter = 0;
		String url = "";
		while (!status && retryCounter < RETRY) {
			try {
				String date = DateUtils.dateToString(new Date(), "ddMMMyyyy");
				String fileName = "CMVOLT_" + date + ".csv";
				String fullFilename = AppConstant.REPORTS_VOLATILITY + File.separator + fileName;
				url = AppConstant.REPORTS_VOLATILITY_URL + "CMVOLT_" + DateUtils.dateToString(new Date(), "ddMMyyyy") + ".CSV";
				status = FileUtils.downloadFile(url, fullFilename);
				SendEmail.sentMail("Volatility report downloaded", "url " + url + "\n fileName", "Data-Downloader");
			} catch (Exception e) {
				SendEmail.sentMail("CRITICAL! Retry Bhav Copy is not downloaded", "url " + url + "\n completeFileName", "Data-Downloader");
				e.printStackTrace();
				// retry after 5 min
				Thread.sleep(500000);
			}
			retryCounter++;
		}
		try {
			emailIV();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	private static void emailIV() throws Exception {
		List<VolatilitySymbol> lst = VolatilitySymbol.getVolatilitySymbole();
		VolatilitySymbol.sortedByDayIV(lst);

		List<VolatilitySymbol> lst2 = VolatilitySymbol.fileter(lst, 2.0);
		StringBuilder str = new StringBuilder();
		int count = 0;
		for (VolatilitySymbol volatilitySymbol : lst2) {
			// System.out.println(volatilitySymbol);
			double vol= (volatilitySymbol.getVolatility() * 100);
			str.append(volatilitySymbol.getSymbol() + " IV " + df2.format(vol));
			str.append("\n");
			count++;
		}
		str.append("---------------- \n");
		str.append("total Volatile Stocks : " + count);
		System.out.println(str);
		SendEmail.sentMail("Volatility", str.toString(), "Data-Downloader");
	}

	public static void deliveryReportDownload() throws InterruptedException {

		boolean status = false;
		int retryCounter = 0;
		while (!status && retryCounter < RETRY) {
			try {
				String date = DateUtils.dateToString(new Date(), "dd-MMM-yyyy");
				String fileName = date + ".csv";
				String fullFilename = AppConstant.REPORTS_DELIVRY + File.separator + fileName;
				status = FileUtils.downloadFile(bhavCopyUrl, fullFilename);
				SendEmail.sentMail("Delivery % report downloaded", "url " + bhavCopyUrl + "\n fileName", "Data-Downloader");
			} catch (Exception e) {
				SendEmail.sentMail("CRITICAL! Retry Bhav Copy is not downloaded", "url " + bhavCopyUrl + "\n completeFileName", "Data-Downloader");
				e.printStackTrace();
				// retry after 5 min
				Thread.sleep(500000);
			}

			retryCounter++;
		}
	}
}
