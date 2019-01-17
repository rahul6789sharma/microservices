package org.stocksrin.strategies.automation;

import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.StrategyBuilderApplication;
import org.stocksrin.collector.option.data.InMemoryStrategyies;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.model.strategies.StrategyModel;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.restclient.USDINRConsumeWebService;
import org.stocksrin.strategies.common.utils.PNLMail;
import org.stocksrin.strategies.utils.result.StrategyPrinterConsoleUSDINR;
import org.stocksrin.strategies.utils.result.StrategyResult;

@Controller
public class InMemeoryStrategyDataUpdaterUSDINR extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(InMemeoryStrategyDataUpdaterUSDINR.class);
	private static long timeInteval = 120000;

	 //static long timeInteval = 12000;
	@Autowired(required = true)
	private USDINRConsumeWebService uSDINRConsumeWebService;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			while (CommonUtils.getEveningTimeUSDINR()) {

				try {
					try {
						LockObject.getWriteLock();

						// update option ltp and spot price
						Set<String> keys = InMemoryStrategyies.getStrategiesUSDINR().keySet();

						for (String string2 : keys) {
							Strategy strategy = InMemoryStrategyies.getStrategiesUSDINR().get(string2);
							updatePrice(strategy);
							StringBuilder result = StrategyPrinterConsoleUSDINR.print(strategy);
							log.info(result.toString());
							PNLMail.targetMail(strategy);
						}

					} finally {
						LockObject.realseWriteLock();
					}
					Thread.sleep(timeInteval);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// only write result between 3:15 to 3:35
			if (CommonUtils.isTimeLessThen(17, 30)) {
				log.info("USDINR Writing result to file ");
				StrategyResult.writeResultUSDINR();
			}
		}

	}

	private synchronized void updatePrice(Strategy strategy) throws Exception {

		if (strategy.getUnderlying().equals(UnderLying.USDINR)) {
			List<StrategyModel> strategyModels = strategy.getStrategyModels();

			double totalPL = 0;
			Double ltp = null;
			Double iv = null;
			String lastDataUpdateTime = "";

			for (StrategyModel strategyModel : strategyModels) {

				ltp = uSDINRConsumeWebService.getOptionLtp(strategyModel.getExpiry(), strategyModel.getStrike(), strategyModel.getType());

				iv = uSDINRConsumeWebService.getIV(strategyModel.getExpiry(), strategyModel.getStrike(), strategyModel.getType());

				lastDataUpdateTime = uSDINRConsumeWebService.getLastDataUpdated(strategyModel.getExpiry());

				if (ltp != null && ltp != 0.0 && ltp != 0) {
					strategyModel.setLtp(ltp);
				}
				if (iv != null) {
					strategyModel.setCurrent_IV(iv);
				}

				strategy.setUnderlying_ltp(uSDINRConsumeWebService.getSpotPrice());
				totalPL = totalPL + (strategyModel.getQuantity() * 1000 * (strategyModel.getLtp() - strategyModel.getAvgPrice()));
			}
			updateData(strategy, lastDataUpdateTime, totalPL);

		} else {
			throw new Exception("Underlying is not set");
		}
	}

	private void updateData(Strategy strategy, String lastDataUpdateTime, double totalPL) throws Exception {

		strategy.setDataUpdatedAt(lastDataUpdateTime);
		strategy.setTotalPL(totalPL);

		// starting
		if (strategy.getTotalPLMax() == 0 && strategy.getTotalPLMin() == 0) {
			strategy.setTotalPLMax(strategy.getTotalPL());
			strategy.setTotalPLMin(strategy.getTotalPL());
			strategy.setTotalPLMaxSpot(strategy.getUnderlying_ltp());
			strategy.setTotalPLMinSpot(strategy.getUnderlying_ltp());
			strategy.setTotalPLMaxTime(lastDataUpdateTime);
			strategy.setTotalPLMinTime(lastDataUpdateTime);

		}

		if (strategy.getTotalPL() > strategy.getTotalPLMax()) {
			strategy.setTotalPLMax(strategy.getTotalPL());
			strategy.setTotalPLMaxSpot(strategy.getUnderlying_ltp());
			strategy.setTotalPLMaxTime(lastDataUpdateTime);

		}
		if (strategy.getTotalPL() < strategy.getTotalPLMin()) {
			strategy.setTotalPLMin(strategy.getTotalPL());
			strategy.setTotalPLMinSpot(strategy.getUnderlying_ltp());
			strategy.setTotalPLMinTime(lastDataUpdateTime);
		}

	}

}