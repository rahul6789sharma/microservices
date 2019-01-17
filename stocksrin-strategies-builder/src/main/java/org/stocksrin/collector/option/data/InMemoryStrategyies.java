package org.stocksrin.collector.option.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.StrategyModel;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.strategies.automation.LockObject;

public class InMemoryStrategyies {

	private static Map<String, Strategy> strategiesusdinr = new HashMap<>();
	private static Map<String, Strategy> strategies = new HashMap<>();
	private static Map<String, Strategy> strategiesIntraDay = new HashMap<>();

	public static synchronized void put(String key, Strategy strategy, String dir) {

		if (dir.equals(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_BNF) || dir.equals(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY)) {
			putStrategyy(key, strategy, strategiesIntraDay);
			// putIntraDay(key, strategy);
		} else if (dir.equals(AppConstant.STOCKSRIN_STRATEGY_DIR_DAILY_USDINR_Strategy_AutoMated)) {
			putStrategyy(key, strategy, strategiesusdinr);
		} else {
			// put(key, strategy);
			putStrategyy(key, strategy, strategies);
		}

	}

	public static synchronized void putStrategyy(String key, Strategy strategy, Map<String, Strategy> strategiesMap) {

		// already existing strategy
		Strategy data = strategiesMap.get(key);

		if (data == null) {
			strategiesMap.put(key, strategy);

		} else {
			strategy.setUnderlying_ltp(data.getUnderlying_ltp());
			strategy.setTotalPL(data.getTotalPL());
			strategy.setTotalPLMax(data.getTotalPLMax());
			strategy.setTotalPLMaxSpot(data.getTotalPLMaxSpot());
			strategy.setTotalPLMaxTime(data.getTotalPLMaxTime());
			strategy.setTotalPLMin(data.getTotalPLMin());
			strategy.setTotalPLMinSpot(data.getTotalPLMinSpot());
			strategy.setTotalPLMinTime(data.getTotalPLMinTime());
			strategy.setTarget(data.getTarget());
			strategy.setTargetLoss(data.getTargetLoss());

			List<StrategyModel> lst = data.getStrategyModels();

			for (StrategyModel strategyModel : lst) {
				for (StrategyModel s : strategy.getStrategyModels()) {

					if (strategyModel.getType().equals(s.getType()) && strategyModel.getStrike() == s.getStrike() && strategyModel.getExpiry().equals(s.getExpiry())) {
						s.setLtp(strategyModel.getLtp());
						s.setCurrent_IV(strategyModel.getCurrent_IV());
					}

				}
			}
			strategiesMap.put(key, strategy);
		}
	}

	public static Map<String, Strategy> getStrategiesUSDINR() {
		Map<String, Strategy> result;
		try {
			LockObject.getWriteLock();
			result = strategiesusdinr;
		} finally {
			LockObject.realseWriteLock();
		}
		return result;
	}

	public static Map<String, Strategy> getStrategies() {
		Map<String, Strategy> result;
		try {
			LockObject.getWriteLock();
			result = strategies;
			result.putAll(strategiesusdinr);
			//result = strategiesusdinr;
		} finally {
			LockObject.realseWriteLock();
		}
		return result;
	}

	public static Map<String, Strategy> getStrategiesIntraDay() {
		Map<String, Strategy> result;
		try {
			LockObject.getWriteLock();
			result = strategiesIntraDay;
		} finally {
			LockObject.realseWriteLock();
		}
		return result;
	}

	public static void clear() {

		try {
			LockObject.getWriteLock();
			strategiesIntraDay.clear();
			strategies.clear();
			strategiesusdinr.clear();
		} finally {
			LockObject.realseWriteLock();
		}
	}
}