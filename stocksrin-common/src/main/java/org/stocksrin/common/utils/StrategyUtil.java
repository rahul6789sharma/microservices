package org.stocksrin.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.model.trade.StrategyModel;
import org.stocksrin.common.model.trade.UnderLyingInstrument;

public class StrategyUtil {

	public static Map<String, Strategy> getStrategy2(String dir) throws Exception {

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
			UnderLyingInstrument underLyingName = UnderLyingInstrument.OTHERS;
			if (underlaying.equals("BANKNIFTY.csv")) {
				underLyingName = UnderLyingInstrument.BANKNIFTY;
			} else if (underlaying.equals("NIFTY.csv")) {
				underLyingName = UnderLyingInstrument.NIFTY;
			} else if (underlaying.equals("USDINR.csv")) {
				underLyingName = UnderLyingInstrument.USDINR;
			}
			Strategy strategy = new Strategy(underLyingName);
			if (data != null) {
				strategy.setTradeDate(data.getTradeDate());
				strategy.setTradeSpotPrice(data.getSpot_close());
			}
			Long dte = DateUtils.getDte(data.getExpiry(), "ddMMMyyyy");
			strategy.setDte(dte.toString());
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
