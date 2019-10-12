package org.stocksrin.option.data.store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.stocksrin.collector.option.data.PriceUtils;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.InstrumentType;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.ExpiryDatesUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.common.utils.options.OptionUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.rules.nifty.TestOptionChain;

// Nifty and BNK monthly and weekly full option chain for current week and month data download
@Controller
public class NiftyOptionDataDownloader extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(NiftyOptionDataDownloader.class);

	/*
	 * @Autowired private NiftyClient niftyClient;
	 */

	private String currentExpiry;
	private int i = 0;
	private String date;

	private CopyOnWriteArrayList<String> notDownloaded;

	public static void main(String[] args) throws Exception {

		try {
			PriceUtils.fetchData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		NiftyOptionDataDownloader obj = new NiftyOptionDataDownloader();
		obj.run();
		// obj.downLoadStocksOptionChain(AppConstant.STOCKSRIN_OPTION_DATA_DIR_FNO);
		// System.out.println(FileUtils.listdir(AppConstant.STOCKSRIN_OPTION_DATA_DIR_STOCKS));
		// niftyOptionDataDownloader.downLoadOptionChain(AppConstant.STOCKSRIN_OPTION_DATA_DIR_STOCKS,
		// "SBIN", null);
	}

	private void reset() {
		currentExpiry = null;
		i = 0;
		notDownloaded = new CopyOnWriteArrayList<>();
		date = null;
	}

	@Override
	public void run() {

		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				// download nifty option chain
				downloadNiftyWeeklyandMonthlyOptioncahin();
				downloadBNFWeeklyandMonthlyOptioncahin();
				// download nifty stocks optipon chain
				downLoadStocksOptionChain(AppConstant.STOCKSRIN_OPTION_DATA_DIR_STOCKS);
				downLoadStocksOptionChain(AppConstant.STOCKSRIN_OPTION_DATA_DIR_FNO);
				try {
					String result = TestOptionChain.analysis(AppConstant.STOCKSRIN_OPTION_DATA_DIR_STOCKS);
					SendEmail.sentMail("Nifty 50 Analysis", result, "Data-Downloader");
					String result2 = TestOptionChain.analysis(AppConstant.STOCKSRIN_OPTION_DATA_DIR_FNO);
					SendEmail.sentMail("NFO 200 Analysis", result2, "Data-Downloader");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			SendEmail.sentMail("CRITICAL! Nifty Option Data Downaloader", "ERROR " + e.getMessage(), "Data-Downloader");
		}
	}

	private void downLoadStocksOptionChain(String path) {
		reset();
		List<String> lst = FileUtils.listdir(path);

		for (String string : lst) {
			try {
				log.info("Checking .. " + string);
				downLoadOptionChain(path, string, null);
				Thread.sleep(100);
			} catch (Exception e) {
				notDownloaded.add(string);
				e.printStackTrace();
			}
		}
		retry(path);

	}

	private void downLoadOptionChain(String path, String symbole, String expiry) throws Exception {

		// OptionModles optionModles = getOptionData(expiry, symbole);
		// String date = optionModles.getDate();
		if (currentExpiry == null && date == null) {

			OptionModles optionModles = getOptionData(expiry, symbole);
			date = optionModles.getDate();
			expiry = optionModles.getExpiry();
			currentExpiry = optionModles.getExpiry();
		}

		String dir = path + File.separator + symbole + File.separator + currentExpiry;
		boolean makedirstatus = FileUtils.makeDir(dir);
		if (makedirstatus) {
			log.info("New Expiry Start ");
			log.info("Dir is created " + dir);
		}

		String optionFileDir = dir + File.separator + date + ".json";

		boolean isFileExist = FileUtils.isFileExits(optionFileDir);
		log.info(optionFileDir + ", isFileExist : " + isFileExist);

		if (!isFileExist) {
			log.info("Downloading .. " + symbole);
			OptionModles optionModles = getOptionData(expiry, symbole);
			FileUtils.writeDataAsJson(optionModles, optionFileDir);
		} else {
			log.info("already data downloaded");
		}
	}

	private void downloadBNFWeeklyandMonthlyOptioncahin() {
		try {
			String monthlyExpiry = ExpiryDatesUtils.getMonthlyExpiry(new ArrayList<>(NiftyData.shortedExpiry));
			String firstExpiry = BankNiftyData.shortedExpiry.first();
			OptionModles optionModlesweekly = BankNiftyData.bnOptionData.get(firstExpiry);
			downloadNifty(optionModlesweekly, AppConstant.STOCKSRIN_OPTION_DATA_DIR_BNF, firstExpiry);
			OptionModles optionModlesMonthly = BankNiftyData.bnOptionData.get(monthlyExpiry);
			downloadNifty(optionModlesMonthly, AppConstant.STOCKSRIN_OPTION_DATA_DIR_BNF, monthlyExpiry);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void downloadNiftyWeeklyandMonthlyOptioncahin() {
		try {
			String monthlyExpiry = ExpiryDatesUtils.getMonthlyExpiry(new ArrayList<>(NiftyData.shortedExpiry));
			String firstExpiry = NiftyData.shortedExpiry.first();
			OptionModles optionModlesweekly = NiftyData.optionData.get(firstExpiry);
			downloadNifty(optionModlesweekly, AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY, firstExpiry);
			OptionModles optionModlesMonthly = NiftyData.optionData.get(monthlyExpiry);
			downloadNifty(optionModlesMonthly, AppConstant.STOCKSRIN_OPTION_DATA_DIR_NIFTY, monthlyExpiry);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private OptionModles downloadNifty(OptionModles optionModles, String path, String expiry) {

		// OptionModles optionModles = NiftyData.optionData.get(expiry);
		boolean makedirstatus = FileUtils.makeDir(path + expiry);

		if (makedirstatus) {
			log.info("New Expiry Start " + expiry);
		}

		// OptionModles optionModles = niftyClient.getOptionModel(firstExpiry);
		String date = optionModles.getDate();
		String optionFileDir = path + expiry + File.separator + date + ".json";

		boolean isFileExist = FileUtils.isFileExits(optionFileDir);
		log.info(optionFileDir + ", isFileExist : " + isFileExist);

		if (!isFileExist) {
			FileUtils.writeDataAsJson(optionModles, optionFileDir);
			log.info("Downloaded " + optionFileDir);
		} else {
			log.info("already data downloaded , " + optionFileDir);
		}
		return optionModles;
	}

	private void retry(String path) {
		// List<String> notDownloaded1 = notDownloaded;
		log.info("******* RETRYING ***** size" + notDownloaded.size() + " -" + notDownloaded);
		while (i < 5) {
			log.info("Retrying ....." + i + " with stocks " + notDownloaded);
			CopyOnWriteArrayList<String> notDownloaded1 = notDownloaded;
			for (String string : notDownloaded1) {

				try {
					log.info("Checking .. " + string);
					downLoadOptionChain(path, string, null);
					Thread.sleep(100);
					notDownloaded.remove(string);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			i++;
		}
		// if (!notDownloaded.isEmpty()) {
		SendEmail.sentMailimp("CRITICAL! Final Stocksrin Option Data Not downloaded", notDownloaded.toString(), "Data-Downloader");
		/// }
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

	public static OptionModles getOptionData(String expiry, String symbole) throws Exception {
		try {
			String url = buildOptionChainURL(symbole, expiry);
			return OptionUtils.getOptionChain(url, expiry, InstrumentType.OTHER, symbole);
		} catch (Exception e) {
			throw new Exception("Cant download option Chain for : " + symbole);
		}
	}

	private static String buildOptionChainURL(String symbole, String expiry) {
		symbole.replace("&", "%26");
		if (expiry == null) {
			return "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbolCode=2212&symbol=" + symbole
					+ "&instrument=-&date=-&segmentLink=17&symbolCount=2&segmentLink=17";
		} else {
			return "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?segmentLink=17&instrument=OPTSTK&symbol=" + symbole + "&date=" + expiry;
		}
	}
}