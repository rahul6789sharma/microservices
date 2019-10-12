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
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.model.trade.StrategyModel;
import org.stocksrin.common.model.trade.UnderLyingInstrument;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategies.common.utils.PNLMail;
import org.stocksrin.strategies.utils.result.StrategyResult;

// update price of in memeories Startegies
@Controller
public class InMemeoryStrategyDataUpdater extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(InMemeoryStrategyDataUpdater.class);
	static long timeInteval = 120000;

	// static long timeInteval = 12000;
	@Autowired(required = true)
	private BNFConsumeWebService bnfConsumeWebService;

	@Autowired(required = true)
	private NiftyConsumeWebService niftyConsumeWebService;

	@Override
	public void run() {
		StrategyBuilderApplication.appWait();
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			while (CommonUtils.getEveningTimeForStrategy()) {

				try {
					try {
						LockObject.getWriteLock();

						// update option ltp and spot price
						Set<String> keys = InMemoryStrategyies.getStrategies().keySet();

						for (String string2 : keys) {
							Strategy strategy = InMemoryStrategyies.getStrategies().get(string2);
							updatePrice(strategy);
							PNLMail.targetMail(strategy);
						}

						Set<String> keys2 = InMemoryStrategyies.getStrategiesIntraDay().keySet();

						for (String string2 : keys2) {
							Strategy strategy = InMemoryStrategyies.getStrategiesIntraDay().get(string2);
							updatePrice(strategy);
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
			if (CommonUtils.isTimeLessThen(15, 55)) {
				log.info("Writing result to file ");
				StrategyResult.writeResult();
			}
		}

	}

	private synchronized void updatePrice(Strategy strategy) throws Exception {

		if (strategy.getUnderlying().equals(UnderLyingInstrument.BANKNIFTY)) {
			List<StrategyModel> strategyModels = strategy.getStrategyModels();
			strategy.setUnderlying_ltp(bnfConsumeWebService.getSpotPrice());

			double totalPL = 0;
			Double ltp = null;
			Double iv = null;
			String lastDataUpdateTime = "";

			for (StrategyModel strategyModel : strategyModels) {
				ltp = bnfConsumeWebService.getOptionLtp(strategyModel.getExpiry(), strategyModel.getStrike(), strategyModel.getType());

				iv = bnfConsumeWebService.getIV(strategyModel.getExpiry(), strategyModel.getStrike(), strategyModel.getType());

				lastDataUpdateTime = bnfConsumeWebService.getLastDataUpdated(strategyModel.getExpiry());

				if (ltp != null && ltp != 0.0 && ltp != 0) {
					strategyModel.setLtp(ltp);
				}

				if (iv != null) {
					strategyModel.setCurrent_IV(iv);
				}

				totalPL = totalPL + (strategyModel.getQuantity() * (strategyModel.getLtp() - strategyModel.getAvgPrice()));
			}
			updateData(strategy, lastDataUpdateTime, totalPL);

		} else if (strategy.getUnderlying().equals(UnderLyingInstrument.NIFTY)) {
			List<StrategyModel> strategyModels = strategy.getStrategyModels();

			strategy.setUnderlying_ltp(niftyConsumeWebService.getSpotPrice());

			double totalPL = 0;
			Double iv = null;
			Double ltp = null;
			String lastDataUpdateTime = "";

			for (StrategyModel strategyModel : strategyModels) {

				ltp = niftyConsumeWebService.getOptionLtp(strategyModel.getExpiry(), strategyModel.getStrike(), strategyModel.getType());

				iv = niftyConsumeWebService.getIV(strategyModel.getExpiry(), strategyModel.getStrike(), strategyModel.getType());

				if (ltp != null && ltp != 0.0 && ltp != 0) {
					strategyModel.setLtp(ltp);
				}

				if (iv != null) {
					strategyModel.setCurrent_IV(iv);
				}

				totalPL = totalPL + (strategyModel.getQuantity() * (strategyModel.getLtp() - strategyModel.getAvgPrice()));
				lastDataUpdateTime = niftyConsumeWebService.getLastDataUpdated(strategyModel.getExpiry());
			}
			updateData(strategy, lastDataUpdateTime, totalPL);
			// strategy.setTotalPL(totalPL);
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