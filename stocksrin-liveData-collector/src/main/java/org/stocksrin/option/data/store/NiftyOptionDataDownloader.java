package org.stocksrin.option.data.store;

import java.io.File;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

@Controller
public class NiftyOptionDataDownloader extends TimerTask {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/*
	 * @Autowired private NiftyClient niftyClient;
	 */

	public static void main(String[] args) {
		NiftyOptionDataDownloader niftyOptionDataDownloader = new NiftyOptionDataDownloader();
		niftyOptionDataDownloader.run();
	}

	@Override
	public void run() {

		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				OptionModles optionModles = downloadNifty(AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY);
				String result = mailBody(optionModles);
				SendEmail.sentMail("Nifty Option Data Downloaded", result, "Data-Downloader");
			}
		} catch (Exception e) {
			SendEmail.sentMail("CRITICAL! Nifty Option Data Downaloader", "ERROR " + e.getMessage(), "Data-Downloader");
		}
	}
	
	private OptionModles downloadNifty(String path) {

		String firstExpiry = NiftyData.shortedExpiry.first();
		boolean makedirstatus = FileUtils.makeDir(path + firstExpiry);

		if (makedirstatus) {
			log.info("New Expiry Start");
		}
		OptionModles optionModles = NiftyData.optionData.get(firstExpiry);
		// OptionModles optionModles = niftyClient.getOptionModel(firstExpiry);
		String date = optionModles.getDate();
		String optionFileDir = path + firstExpiry + File.separator + date + ".json";

		boolean isFileExist = FileUtils.isFileExits(optionFileDir);
		log.info("isFileExist " + isFileExist);

		if (!isFileExist) {
			FileUtils.writeDataAsJson(optionModles, optionFileDir);
		} else {
			log.info("already data downloaded");
		}
		return optionModles;
	}

	private String mailBody(OptionModles optionModles) {

		StringBuilder result = new StringBuilder();
		double totalCE = (double) optionModles.getTotal_ce_oi() / 100000;
		double totalPE = (double) optionModles.getTotal_pe_oi() / 100000;
		double pcr = totalPE / totalCE;
		double roundOff = (double) Math.round(pcr * 100) / 100;
		result.append(" \n");
		result.append("Total CE OI: " + totalCE + " Lacks \n");
		result.append("Total PE OI: " + totalPE + " Lacks \n");
		result.append("PCR: " + roundOff + "  \n");

		double totalCE_chnage = (double) optionModles.getTotal_ce_oi_change() / 100000;
		double totalPE_chnage = (double) optionModles.getTotal_pe_oi_change() / 100000;
		result.append(" \n");
		result.append("Total Change in  CE OI: " + totalCE_chnage + " Lacks \n");
		result.append("Total Change in  PE OI: " + totalPE_chnage + " Lacks \n");

		result.append(highestChangeInOI(optionModles));
		log.info(" result" + result);
		return result.toString();
	}

	private String highestChangeInOI(OptionModles optionModles) {
		StringBuilder result = new StringBuilder();
		List<OptionModle> lst = optionModles.getOptionModle();
		Integer highestChangeinOICE = Integer.MIN_VALUE;
		Double highestChangeinOICE_strike = null;

		Integer highestChangeinOIPE = Integer.MIN_VALUE;
		Double highestChangeinOIPE_strike = null;

		for (OptionModle optionModle : lst) {

			if (optionModle.getC_change_oi() != null && highestChangeinOICE < optionModle.getC_change_oi()) {
				highestChangeinOICE = optionModle.getC_change_oi();
				highestChangeinOICE_strike = optionModle.getStrike_price();
			}

			if (optionModle.getP_change_oi() != null && highestChangeinOIPE < optionModle.getP_change_oi()) {
				highestChangeinOIPE = optionModle.getP_change_oi();
				highestChangeinOIPE_strike = optionModle.getStrike_price();
			}

		}

		Double highestChangeinOICEValue = (double) highestChangeinOICE / 100000;
		log.info(" highestChangeinOICE : " + highestChangeinOICEValue);
		log.info(" highestChangeinOICE_strike : " + highestChangeinOICE_strike);

		result.append("\n");
		result.append("Highest Change in CE OI " + highestChangeinOICEValue + " at " + highestChangeinOICE_strike);

		Double highestChangeinOIPEValue = (double) highestChangeinOIPE / 100000;
		log.info(" highestChangeinOIPE : " + highestChangeinOIPEValue);
		log.info(" highestChangeinOIPE_strike : " + highestChangeinOIPE_strike);
		result.append("\n");
		result.append("Highest Change in PE OI " + highestChangeinOIPEValue + " at " + highestChangeinOIPE_strike);

		return result.toString();
	}


}
