package org.stocksrin.strategies.common.utils;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.email.SendEmail;
import org.stocksrin.strategies.utils.result.StrategyPrinterConsole;

public class PNLMail {
	private static final Logger log = LoggerFactory.getLogger(PNLMail.class);
	/*
	 * private double targetProfit = 1000; private double targetLoss = -1000;
	 */

	public static void targetMail(Strategy strategy) throws Exception {

		String underlying;
		if (UnderLying.NIFTY.equals(strategy.getUnderlying())) {
			underlying = "NF";
		} else {
			underlying = "BNF";
		}

		// double targetloss = 0.0;
		if (strategy.getTotalPL() > strategy.getTarget()) {
			strategy.setTarget(strategy.getTotalPL() + 500);
			log.info("target Achived " + strategy.getTotalPL());
			log.info("Next target Achived " + strategy.getTarget());
			StringBuilder result = StrategyPrinterConsole.print(strategy);
			log.info(result.toString());
			SendEmail.sentMail("P[" + foramtePL(strategy.getTotalPL()) + "] " + underlying + "-" + strategy.getStrategyName(), StrategyPrinterConsole.print(strategy).toString(), "strategy Builder");
		} else if (strategy.getTotalPL() < strategy.getTargetLoss()) {

			SendEmail.sentMail("L[" + foramtePL(strategy.getTotalPL()) + "] " + underlying + "-" + strategy.getStrategyName(), StrategyPrinterConsole.print(strategy).toString(), "strategy Builder");

			strategy.setTargetLoss(strategy.getTotalPL() - 500);
			log.info("stop Achived " + strategy.getTotalPL());
			log.info("Next stop Achived " + strategy.getTargetLoss());
		}

	}

	private static String foramtePL(Double price) {
		DecimalFormat signFormate = new DecimalFormat("+#,##0000;-#");
		return signFormate.format(price);
	}
}
