package org.stocksrin.strategies.builder.file;

import java.util.Date;
import java.util.SortedSet;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.StrategyBuilderApplication;
import org.stocksrin.common.model.trade.UnderLyingInstrument;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.common.utils.options.ExpiryUtils;
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategies.common.strategies.BNFStrategies;
import org.stocksrin.strategies.common.strategies.NiftyStrategies;
import org.stocksrin.strategies.common.strategies.common.CommonStrategiesBuilder;

@Controller
public class IntraDayStrategyBuilder2 extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(IntraDayStrategyFileBuilder.class);

	@Autowired(required = true)
	private BNFStrategies bnfStrategies;

	@Autowired
	private BNFConsumeWebService bnfConsumeWebService;

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired
	private CommonStrategiesBuilder commonStrategiesBuilder;

	@Autowired(required = true)
	private NiftyStrategies niftyStrategies;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		log.info("****** Dynamic Startegy ***** at ");
		try {

			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {

				SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();

				String extrname = AppConstant.SECONDSESSIOON;
				if (ExpiryUtils.isTodayExpiry(allExpiry)) {
					extrname = AppConstant.EXPIRY;
				}
				String currentExpiry = null;
				currentExpiry = allExpiry.first();
				String lastupdatedtime = niftyConsumeWebService.getLastDataUpdated(currentExpiry);
				lastupdatedtime = parseDate(lastupdatedtime);

				// FileUtils.deletedContaningfiles(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY,
				// lastupdatedtime + "~" + EXPIRY);
				// add keyword Expiry to deleted the file after result rite so next day it ll
				// not be picked in morning only

				commonStrategiesBuilder.callButterFly(lastupdatedtime + "~" + extrname + "~CallButterFly100@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY,
						niftyConsumeWebService, UnderLyingInstrument.NIFTY);

				commonStrategiesBuilder.putButterFly(lastupdatedtime + "~" + extrname + "~PutButterFly100@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY,
						niftyConsumeWebService, UnderLyingInstrument.NIFTY);

				commonStrategiesBuilder.ironFlyFly(lastupdatedtime + "~" + extrname + "~IronFly150@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY,
						niftyConsumeWebService, UnderLyingInstrument.NIFTY, 150);

				commonStrategiesBuilder.stradel300Calander(lastupdatedtime + "~" + extrname + "~StradelCalander300@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY,
						niftyConsumeWebService, UnderLyingInstrument.NIFTY, 50);

				commonStrategiesBuilder.strangle(lastupdatedtime + "~" + extrname + "~Strangle100@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY,
						niftyConsumeWebService, UnderLyingInstrument.NIFTY, 100);

				niftyStrategies.Strategy3StraddleNifty(lastupdatedtime + "~" + extrname + "~Straddle3@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY);

				niftyStrategies.itmCalender(lastupdatedtime + "~" + extrname + "~ITMCalender@INTRADAY-AUTO-Strategy_NIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY);

				// banknifty
				commonStrategiesBuilder.stradel300Calander(lastupdatedtime + "~" + extrname + "~StradelCalander300@INTRADAY-AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF,
						bnfConsumeWebService, UnderLyingInstrument.BANKNIFTY, 100);

				bnfStrategies.StrategyStrangle(lastupdatedtime + "~" + extrname + "~Strangle300Away@INTRADAY-AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF, 300, 2);

				bnfStrategies.StrategyStrangle(lastupdatedtime + "~" + extrname + "~Strangle500Away@INTRADAY-AUTO-Strategy_BANKNIFTY", AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF, 500, 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String parseDate(String lastupdatedtime) throws Exception {
		Date d = DateUtils.stringToDate(lastupdatedtime, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "dd~MMM~HH~mm");
	}
}
