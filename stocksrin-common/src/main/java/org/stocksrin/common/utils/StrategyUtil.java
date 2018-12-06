package org.stocksrin.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.model.strategies.StrategyModel;

public class StrategyUtil {

	public static Map<String, Strategy> getStrategy2(String dir) {

		Map<String, List<StrategyModel>> map = getStrategy(dir);

		Map<String, Strategy> strategys = new HashMap<>();

		Set<String> keys = map.keySet();
		for (String string : keys) {
			List<StrategyModel> lst = map.get(string);
			StrategyModel data = null;
			if (!lst.isEmpty()) {
				data = lst.get(0);
			}

			String underlaying = string.split("_")[1];
			UnderLying underLyingName = UnderLying.OTHERS;
			if (underlaying.equals("BANKNIFTY.csv")) {
				underLyingName = UnderLying.BANKNIFTY;
			} else if (underlaying.equals("NIFTY.csv")) {
				underLyingName = UnderLying.NIFTY;
			}
			Strategy strategy = new Strategy(underLyingName);
			if (data != null) {
				strategy.setTradeDate(data.getTradeDate());
				strategy.setTradeSpotPrice(data.getSpot_close());
			}
			String name = string.split("-Strategy")[0];
			strategy.setStrategyModels(lst);
			strategy.setStrategyName(name);
			strategy.setDir(dir);
			strategy.setFileName(string);

			strategys.put(string, strategy);
		}

		return strategys;
	}

	public static Map<String, List<StrategyModel>> getStrategy(String dir) {
		Map<String, List<StrategyModel>> strategy = new HashMap<>();

		List<String> lst = FileUtils.listFilesForFolder(new File(dir));

		for (String string : lst) {

			String file = dir + string;
			List<StrategyModel> str = getStrategyModel(file);

			strategy.put(string, str);
		}

		return strategy;
	}

	public static synchronized List<StrategyModel> getStrategyModel(String file) {
		List<StrategyModel> result = new ArrayList<>(5);

		List<String[]> lst = CommonUtils.getCSVData(file);

		for (String[] strings : lst) {
			StrategyModel strategyModel = new StrategyModel();
			strategyModel.setStrategySerial(strings[0]);
			strategyModel.setExpiry(strings[1]);

			if (strings[2].equals("CALL")) {
				strategyModel.setType(OptionType.CALL);
			} else {
				strategyModel.setType(OptionType.PUT);
			}

			strategyModel.setStrike(Double.parseDouble(strings[3]));
			strategyModel.setAvgPrice(Double.parseDouble(strings[4]));
			strategyModel.setQuantity(Integer.parseInt(strings[5]));
			strategyModel.setTarget(Double.parseDouble(strings[6]));
			strategyModel.setStopLoss(Double.parseDouble(strings[7]));
			strategyModel.setSpot_close(Double.parseDouble(strings[8]));
			strategyModel.setDes(strings[9]);
			strategyModel.setStatus(Boolean.parseBoolean(strings[10]));
			strategyModel.setTraded_IV(Double.parseDouble(strings[11]));
			strategyModel.setTradeDate(strings[12]);
			result.add(strategyModel);

		}
		return result;
	}
}
