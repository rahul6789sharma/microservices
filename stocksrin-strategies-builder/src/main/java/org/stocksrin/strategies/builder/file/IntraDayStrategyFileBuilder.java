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
import org.stocksrin.strategies.common.strategies.BNFStrangle;
import org.stocksrin.strategies.common.strategies.BNFStrategies;

@Controller
public class IntraDayStrategyFileBuilder extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(IntraDayStrategyFileBuilder.class);

	@Autowired
	private BNFStrategies bnfStrategies;

	@Autowired
	private BNFStrangle bnfStrangle;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		log.info("******IntraDayStrategyFileBuilder starting *****");
		try {

			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				// intra day new dir
				List<String> files = new ArrayList<>();
				/*files.add(bnfStrangle.itmStrangle3("ITMStrangle3@INTADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));*/
				files.add(bnfStrangle.itm2Strangle3("ITM2Strangle3@INTADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));
				/*files.add(bnfStrangle.otmStrangle3("OTMStrangle3@INTADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));*/
				files.add(bnfStrategies.BuildStrategy3Straddle("Straddle3@INTADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));
				files.add(bnfStrategies.ironButterfly("ITMIronFly@INTADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));

				files.add(bnfStrategies.doubleCalender("DoubleCalendar@INTADAY~AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF));

				SendEmail.sentMail("IntraDay Strategy File created  : ", " files " + CommonUtils.getFilesName(files), "strategy Builder");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
		}

		log.info("****** IntraDayStrategyFileBuilder starting Completed *****");
	}

}