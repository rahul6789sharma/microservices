package org.stocksrin.strategies.builder.file;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.StrategyBuilderApplication;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.strategies.common.strategies.BNFStrategies;
import org.stocksrin.strategies.common.strategies.NiftyStrategies;

@Controller
public class IntraDayStrategyFileBuilder extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(IntraDayStrategyFileBuilder.class);

	@Autowired(required = true)
	private BNFStrategies bnfStrategies;

	@Autowired(required = true)
	private NiftyStrategies niftyStrategies;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		log.info("******IntraDayStrategyFileBuilder starting *****");
		try {

			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				// intra day new dir
				deleteExpiryFile();
				List<String> files = new ArrayList<>();

				files.add(niftyStrategies.callButterFlyNifty("CallButterFly100@INTRADAY~AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY));
				try {
					files.add(niftyStrategies.putButterFlyNifty("PutButterFly100@INTRADAY~AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY));
				} catch (Exception e) {
					SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
				}

				files.add(niftyStrategies.ironFlyNifty("IronFly150@INTRADAY~AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY, 150));
				files.add(niftyStrategies.ironFlyNifty("IronFly100@INTRADAY~AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY, 100));
				files.add(bnfStrategies.BuildStrategy3Straddle("Straddle3@INTRADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));
				files.add(bnfStrategies.ironButterfly("ITMIronFly@INTRADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));
				files.add(bnfStrategies.doubleCalender("DoubleCalendar@INTRADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));

				SendEmail.sentMail("IntraDay Strategy File created  : ", " files " + CommonUtils.getFilesName(files), "strategy Builder");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
		}

		log.info("****** IntraDayStrategyFileBuilder starting Completed *****");
	}

	private void deleteExpiryFile() {
		try {
			List<String> lst = FileUtils.listFilesForFolder(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY);
			for (String string : lst) {
				System.out.println(string);

				if (string.contains(AppConstant.EXPIRY) || string.contains(AppConstant.SECONDSESSIOON)) {
					String filePath = AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY + string;
					FileUtils.delete(filePath);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}