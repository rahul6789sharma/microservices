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
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.strategies.common.strategies.usdinr.USDINRStrategies;

@Controller
public class USDINRStrategyDailyBuilder extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(BNFStrategyBuilderDaily.class);

	@Autowired
	private USDINRStrategies usdINRStrategies;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		log.info("******USDINR IntraDayStrategyFileBuilder starting *****");
		try {

			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday() && DateUtils.getCurrentDay().equalsIgnoreCase("FRI")) {

				// intra day new dir
				List<String> files = new ArrayList<>();

				// build only on friday
				files.add(usdINRStrategies.BuildStrategy3Straddle("WeekLyStraddle3@AUTO-Strategy_USDINR", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_USDINR_Strategy_AutoMated));
				SendEmail.sentMail("USDINR File Created: ", " files " + CommonUtils.getFilesName(files), "strategy Builder");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
		}

		log.info("******USDINR IntraDayStrategyFileBuilder starting Completed *****");

	}

}
