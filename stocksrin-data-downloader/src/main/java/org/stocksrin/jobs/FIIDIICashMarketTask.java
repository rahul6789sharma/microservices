package org.stocksrin.jobs;

import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.model.FIIDIIDataModle;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.participant.oidata.cash.FIIDIIDailyReportUtils;

public class FIIDIICashMarketTask extends TimerTask {
	private static final Logger log = LoggerFactory.getLogger(FIIDIICashMarketTask.class);

	@Override
	public void run() {

		if (!DateUtils.isWeekEndDay()  && !NSEHolidayUtils.isHoliday()) {

			FIIDIIDataModle data;
			try {
				data = FIIDIIDailyReportUtils.getData();
				String file = getFile();
				CommonUtils.appendData(data.toCsv(), file);
				FIIDIIDataModle fiiDIIDataModle = CommonUtils.getFIIModelFromCSV(data.toCsv());
				log.info("FII DII Cash Data downloaded to : " + file);
				SendEmail.sentMail("FII/DII[FII_NET " + fiiDIIDataModle.getFii_net() + " DII_NET " + fiiDIIDataModle.getDii_net() + " ]", fiiDIIDataModle.toString() + "\n" + "File name " + file, "Data-Downloader");
			} catch (Exception e) {
				SendEmail.sentMail("FIIDIITask Exception !", "ERROR " + e.getMessage(), "Data-Downloader");
				e.printStackTrace();
			}

		} else {
			log.info("Not Downloading FII data its weekEnd ... " + new Date());
		}

	}

	public static String getFile() {
		String currentMonthFile = AppConstant.FILE_NAME_FII_DIR_MONTHLY + DateUtils.getCurrentMonth() + "_" + DateUtils.getCurrentYear() + ".csv";

		boolean status = FileUtils.makeFile(currentMonthFile);
		if (status) {
			String firstINdex = "DateFII_buyValueFII_sellValueFII_netDII_buyValueDII_sellValueDII_netniftynifty_changestocks_Advancestocks_Decline";
			CommonUtils.appendData2(firstINdex + "\n", currentMonthFile);
		}
		return currentMonthFile;
	}

}