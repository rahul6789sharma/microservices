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
import org.stocksrin.strategies.common.strategies.NiftyStrategies;

@Controller
public class BNFStrategyBuilderDaily extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(BNFStrategyBuilderDaily.class);

	@Autowired
	private BNFStrategies bnfStrategies;

	@Autowired
	private BNFStrangle bnfStrangle;

	@Autowired
	private NiftyStrategies niftyStrategies;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		log.info("Building Strategy Automatic");
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			List<String> files = new ArrayList<>();
			try {
				files.add(bnfStrategies.BuildStrategy3Straddle("Straddle3@AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_BNF_Strategy_AutoMated));
			} catch (Exception e) {
				SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
			}

			try {
				files.add(bnfStrategies.Strategy300PointAwaystrangle("Strangle300Away@AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_BNF_Strategy_AutoMated));
				// protected
				files.add(bnfStrangle.strangle_300PointAway_protected_build("Strangle300AwaySafe@AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_BNF_Strategy_AutoMated));
			} catch (Exception e) {
				SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
			}

			try {
				files.add(bnfStrangle.itmStrangle3("ITM3Strangle@AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_BNF_Strategy_AutoMated));
			} catch (Exception e) {
				SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
			}

			try {
				files.add(niftyStrategies.Strategy3StraddleNifty("Straddle3@AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_NIFTY_Strategy_AutoMated));
			} catch (Exception e) {
				SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
			}

			try {
				files.add(niftyStrategies.Strategy3StrangleOTM("OTM3Strangle@AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_NIFTY_Strategy_AutoMated));
			} catch (Exception e) {
				SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
			}

			try {
				files.add(niftyStrategies.Strategy3StrangleITM("ITM3Strangle@AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_NIFTY_Strategy_AutoMated));
			} catch (Exception e) {
				SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
			}
			// Nifty

			SendEmail.sentMail("Daily Strategy File created  : ", " files " + CommonUtils.getFilesName(files), "strategy Builder");
			log.info("Building Strategy Automatic completed");
		}
	}



	public static void main(String[] args) {
		try {
			NiftyStrategies NniftyStrategies = new NiftyStrategies();
		} catch (Exception e) {
			SendEmail.sentMail("Auto Build Strategy Failed", e.getMessage(), "Strategies-BUilder");
		}
	}
}