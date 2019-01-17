package org.stocksrin.participant.oidata;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

public class ParticapentFnoDataDownloaderTask extends TimerTask {

	// need to trst on holy day 0kb files being downloanded when market is not
	// open
	private static final Logger log = LoggerFactory.getLogger(ParticapentFnoDataDownloaderTask.class);

	@Override
	public void run() {
		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				String file = download();
				// Util.collectAllDateForDay(file);
				log.info("ParticapentFnoDataDownloaded : " + file);
				SendEmail.sentMail("FNO OI All Particapent Data SUCCESS", "SUCCESS file " + file, "Data-Downloader");
			}
		} catch (Exception e) {
			SendEmail.sentMail("CRITICAL! FO OI All Particapent Data", "ERROR " + e.getMessage(), "Data-Downloader");
			e.printStackTrace();
		}
	}

	// return file dir
	private static String download() throws Exception {

		String date = DateUtils.dateToString(new Date(), "ddMMyyyy");
		String fileName = "fao_participant_oi_" + date + ".csv";
		String url = AppConstant.NSE_FO_OI_ALLPARTICAPENT + fileName;
		// String
		// url="https://www.nseindia.com/content/nsccl/fao_participant_oi_07092018.csv";
		log.info("url : " + url);
		String date2 = DateUtils.dateToString(new Date(), "MMMyyyy");
		String dir = AppConstant.FO_OI_DIR + date2;

		boolean status = FileUtils.makeDir(dir);

		if (status) {
			log.info("New Dir is create for Month " + dir);
		}

		String file = dir + File.separator + fileName;

		if (!FileUtils.isFileExits(file)) {
			FileUtils.downloadFile(url, file);
			return file;
			/*
			 * if (matchFileDate(file)) {
			 * 
			 * }
			 */
		} else {
			log.info("Already downloaded " + file);
		}
		return file;
	}

	/*
	 * private static boolean matchFileDate(String file) throws Exception { String[]
	 * line = CommonUtils.getCSVData_FirstLine(file); String[] a =
	 * line[0].split("as on"); String dateMatch = DateUtils.dateToString(new Date(),
	 * "MMMM dd"); System.out.println("dateMatch " + dateMatch);
	 * System.out.println("a[1].trim() " + a[1].trim()); boolean status =
	 * dateMatch.equalsIgnoreCase(a[1].trim()); if (!status) {
	 * FileUtils.delete(file); throw new
	 * Exception("Market is closed or its holiday Today or File is not generated in NSE! file "
	 * + file + ", will be delted"); } return status; }
	 */
}
