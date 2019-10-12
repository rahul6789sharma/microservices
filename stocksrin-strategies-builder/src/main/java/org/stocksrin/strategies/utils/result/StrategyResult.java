package org.stocksrin.strategies.utils.result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.collector.option.data.InMemoryStrategyies;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;

public class StrategyResult {

	private static final Logger log = LoggerFactory.getLogger(StrategyResult.class);

	public static void main(String[] args) throws Exception {
		// String dir = APPConstant.STOCKSRIN__STRATEGY_AUTO_DIR;
		// System.out.println(dir);
		// writeStrategyFile("demo", dir);
	}

	public static synchronized void writeResultUSDINR() {
		Set<String> keys = InMemoryStrategyies.getStrategiesUSDINR().keySet();
		for (String string : keys) {
			Strategy lst = InMemoryStrategyies.getStrategiesUSDINR().get(string);
			try {
				StrategyResult.writeStrategyFileUSDINR(lst);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static synchronized void writeResult() {
		// intraDay
		Set<String> keys = InMemoryStrategyies.getStrategiesIntraDay().keySet();
		for (String string : keys) {
			Strategy lst = InMemoryStrategyies.getStrategiesIntraDay().get(string);
			try {
				StrategyResult.writeStrategyFile(lst);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// dailye
		Set<String> keysDaily = InMemoryStrategyies.getStrategies().keySet();
		for (String string : keysDaily) {
			Strategy lst = InMemoryStrategyies.getStrategies().get(string);
			try {
				StrategyResult.writeStrategyFile(lst);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void writeStrategyFileUSDINR(Strategy strategy) throws Exception {
		String date = DateUtils.dateToString(new Date(), "MMMyyyy");
		// String dir = APPConstant.STOCKSRIN__STRATEGY_DIR_RESULT + date +
		// File.separator;
		String dir1 = strategy.getDir();
		String dir = dir1.substring(0, dir1.length() - 1);// removing last //

		String name = strategy.getFileName().split(".csv")[0];
		String resultDir = dir + "_Result" + File.separator + date + File.separator;
		String file = resultDir + name + ".txt";

		FileUtils.makeDir(resultDir);
		FileUtils.makeFile(file);

		StringBuilder result = StrategyPrinterConsoleUSDINR.print(strategy);

		if (result != null) {
			appendData("\n********************************************************************************************\n", file);
			appendData(result.toString(), file);
		}
		log.info("------------");
		log.info(result.toString());
		log.info("------------");
	}

	public static void writeStrategyFile(Strategy strategy) throws Exception {
		String date = DateUtils.dateToString(new Date(), "MMMyyyy");
		// String dir = APPConstant.STOCKSRIN__STRATEGY_DIR_RESULT + date +
		// File.separator;
		String dir1 = strategy.getDir();
		String dir = dir1.substring(0, dir1.length() - 1);// removing last //

		String name = strategy.getFileName().split(".csv")[0];
		String resultDir = dir + "_Result" + File.separator + date + File.separator;
		String file = resultDir + name + ".txt";

		FileUtils.makeDir(resultDir);
		FileUtils.makeFile(file);

		StringBuilder result = StrategyPrinterConsole.print(strategy);

		if (result != null) {
			appendData("\n********************************************************************************************\n", file);
			appendData(result.toString(), file);
		}
	}

	private static void appendData(String data, String fileName) {

		File file = new File(fileName);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			throw new RuntimeException(fileName + " File not exist");
		}
		try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw);) {
			bw.write("\n" + data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}