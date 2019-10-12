package org.stocksrin.result.writer;

import java.io.File;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class ResultWriterTask extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(ResultWriterTask.class);

	@Autowired
	private StrategyDAOService strategyDAOService;

	/*
	 * @Autowired private TradeDAOService tradeDAOService;
	 */

	@Override
	public void run() {
		List<StrategyEntity> strategyEntitys = strategyDAOService.findAll();
		String extension = "_" + DateUtils.getCurrentDate() + "-" + DateUtils.getCurrentMonth() + "-" + DateUtils.getCurrentYear() + "-" + DateUtils.getCurrentDay() + ".json";
		for (StrategyEntity strategyEntity : strategyEntitys) {

			List<TradeEntity> tradeEntitys = strategyEntity.getTrades();
			for (TradeEntity t : tradeEntitys) {
				t.setStrategyEntity(null);
			}
			if (TradeHoldingTime.INTRADAY.equals(strategyEntity.getStrategyHoldingTime())) {
				// only todays startegy needs to be written in DB
				if (DateUtils.getTodayDate().equalsIgnoreCase(strategyEntity.getTradedDate())) {
					csvStrategyWriter(strategyEntity);
					FileUtils.writeDataAsJson(strategyEntity, getDir(strategyEntity) + strategyEntity.getName() + extension);

				} else {
					log.error("not wrrting strategy result check " + strategyEntity);
				}
			} else if (TradeHoldingTime.OVERNIGHT.equals(strategyEntity.getStrategyHoldingTime())) {
				// will not write result for todays created startaegy
				if (!DateUtils.getTodayDate().equalsIgnoreCase(strategyEntity.getTradedDate())) {
					csvStrategyWriter(strategyEntity);
					FileUtils.writeDataAsJson(strategyEntity, getDir(strategyEntity) + strategyEntity.getName() + extension);
				} else {
					log.error("not wrrting strategy result check " + strategyEntity);
				}
			} else if (TradeHoldingTime.POSITIONAL.equals(strategyEntity.getStrategyHoldingTime())) {
				csvStrategyWriter(strategyEntity);
				FileUtils.writeDataAsJson(strategyEntity, getDir(strategyEntity) + strategyEntity.getName() + extension);
			}

		}

	}

	private void csvStrategyWriter(StrategyEntity strategyEntity) {

		try {
			String fileName = getDir(strategyEntity) + "CsvResult" + File.separator + strategyEntity.getName() + ".txt";
			File file = new File(fileName);
			if (!file.exists()) {
				FileUtils.makeFile(fileName);
			}
			FileUtils.appendData(StrategyCSVUtils.print(strategyEntity), fileName);
		} catch (Exception e) {
			log.error( "Writing ResultERROR " +strategyEntity.getName() +" "+ e.getMessage());
			e.printStackTrace();
		}
	}

	private static String getDir(StrategyEntity strategyEntity) {
		String dir = null;
		if (TradeHoldingTime.INTRADAY.equals(strategyEntity.getStrategyHoldingTime())) {
			if (strategyEntity.getSymbole().equalsIgnoreCase("Nifty")) {
				dir = AppConstant.STOCKSRIN_STRATEGY_DB__INTRADAY_NIFTY_DIR;
			} else if (strategyEntity.getSymbole().equalsIgnoreCase("BNF") || strategyEntity.getSymbole().equalsIgnoreCase("BankNifty")) {
				dir = AppConstant.STOCKSRIN_STRATEGY_DB__INTRADAY_BNF_DIR;
			}
		} else if (TradeHoldingTime.POSITIONAL.equals(strategyEntity.getStrategyHoldingTime())) {
			if (strategyEntity.getSymbole().equalsIgnoreCase("Nifty")) {
				dir = AppConstant.STOCKSRIN_STRATEGY_DB__POSITIONAL_NIFTY_DIR;
			} else if (strategyEntity.getSymbole().equalsIgnoreCase("BNF") || strategyEntity.getSymbole().equalsIgnoreCase("BankNifty")) {
				dir = AppConstant.STOCKSRIN_STRATEGY_DB__POSITIONAL_BNF_DIR;
			}
		} else if (TradeHoldingTime.OVERNIGHT.equals(strategyEntity.getStrategyHoldingTime())) {
			if (strategyEntity.getSymbole().equalsIgnoreCase("Nifty")) {
				dir = AppConstant.STOCKSRIN_STRATEGY_DB__OVERNIGHT_NIFTY_DIR;
			} else if (strategyEntity.getSymbole().equalsIgnoreCase("BNF") || strategyEntity.getSymbole().equalsIgnoreCase("BankNifty")) {
				dir = AppConstant.STOCKSRIN_STRATEGY_DB__OVERNIGHT_BNF_DIR;
			}
		}

		return dir;
	}

	public static void main(String[] args) {
		String extension = "_" + DateUtils.getCurrentDate() + "-" + DateUtils.getCurrentMonth() + "-" + DateUtils.getCurrentYear() + "-" + DateUtils.getCurrentDay() + ".json";
		System.out.println(extension);
	}
}
