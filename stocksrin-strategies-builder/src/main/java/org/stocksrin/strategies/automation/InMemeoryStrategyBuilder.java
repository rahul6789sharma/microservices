package org.stocksrin.strategies.automation;

import java.util.TimerTask;

import org.stocksrin.collector.option.data.InMemoryStrategyies;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;

public class InMemeoryStrategyBuilder extends TimerTask {

	private static long timeInteval = 120000;

	// static long timeInteval = 50000;

	@Override
	public void run() {

		InMemoryStrategyies.clear();

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {

			while (CommonUtils.getEveningTimeForStrategy()) {
				try {

					// intradaya
					StrategyFileReader.startManualStrategies(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF);
					StrategyFileReader.startManualStrategies(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY);

					// daily
					StrategyFileReader.startManualStrategies(AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_BNF_Strategy);
					StrategyFileReader.startManualStrategies(AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_BNF_Strategy_AutoMated);
					StrategyFileReader.startManualStrategies(AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_NIFTY_Strategy);
					StrategyFileReader.startManualStrategies(AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_NIFTY_Strategy_AutoMated);

					Thread.sleep(timeInteval);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
