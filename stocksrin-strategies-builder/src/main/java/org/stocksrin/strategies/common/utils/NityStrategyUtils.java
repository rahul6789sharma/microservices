package org.stocksrin.strategies.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TimeZone;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.model.strategies.StrategyModel;
import org.stocksrin.common.utils.DateUtils;

public class NityStrategyUtils {

	
	public static String getNiftyExpiryForDayTrading(SortedSet<String> shortedExpiry, String currentExpiry) throws Exception {

		Calendar time = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		int hr = time.get(Calendar.HOUR);
		if (hr < 14) {
			return currentExpiry;
		} else {
			String today = DateUtils.dateToString(new Date(), "ddMMMyyyy");
			if (currentExpiry.equalsIgnoreCase(today)) {
				System.out.println("Expiry Day Moving to new Expiry");
				int i = 0;
				for (String item : shortedExpiry) {
					if (i == 1) {
						currentExpiry = item;
						break;
					}
					i++;
				}
			}
			return currentExpiry;
		}

	}

	public static double getATMStrike(OptionModles optionModles, int strikediff) {

		double spot = optionModles.getSpot();
		// System.out.println(" getATMStrike spot : " + spot);
		List<OptionModle> lst = optionModles.getOptionModle();

		double atmStrike = 0.0;
		for (OptionModle optionModle : lst) {

			double diff = Math.abs(spot - optionModle.getStrike_price());
			if (diff < strikediff) {
				atmStrike = optionModle.getStrike_price();
				break;
			}
		}

		return atmStrike;
	}

	public static Strategy buildStrategy(String name, List<OptionModle> optionModles, double strike, OptionType optionType, String expiry, double spotPrice, int qnt) throws Exception {
		Strategy strategy = new Strategy(UnderLying.NIFTY);
		strategy.setStrategyName(name);

		for (OptionModle optionModle : optionModles) {
			if (optionModle.getStrike_price().equals(strike)) {
				if (OptionType.PUT.equals(optionType)) {
					StrategyModel strategyModel = StrategyModelBuilderPUT(optionModle, expiry, strike, spotPrice, qnt);
					strategy.getStrategyModels().add(strategyModel);
				} else if (OptionType.CALL.equals(optionType)) {
					StrategyModel strategyModel = StrategyModelBuilderCALL(optionModle, expiry, strike, spotPrice, qnt);
					strategy.getStrategyModels().add(strategyModel);
				}
			}
		}
		return strategy;
	}

	private static StrategyModel StrategyModelBuilderPUT(OptionModle optionModle, String expiry, double strike, double spotPrice, int qnt) throws Exception {
		StrategyModel strategyModel = new StrategyModel();
		strategyModel.setStrategySerial("1");
		strategyModel.setExpiry(expiry);
		strategyModel.setType(OptionType.PUT);
		strategyModel.setStrike(strike);
		strategyModel.setAvgPrice(optionModle.getP_ltp());
		strategyModel.setQuantity(qnt);
		strategyModel.setTarget(800);
		strategyModel.setStopLoss(-1000);
		strategyModel.setDes("Automated Trade");
		strategyModel.setSpot_close(spotPrice);
		if (optionModle.getP_iv() != null) {
			strategyModel.setTraded_IV(optionModle.getP_iv());
		}

		strategyModel.setTradeDate(DateUtils.dateToString(new Date(), "ddMMMyyyy").toUpperCase());
		return strategyModel;
	}

	private static StrategyModel StrategyModelBuilderCALL(OptionModle optionModle, String expiry, double strike, double spotPrice, int qnt) throws Exception {
		StrategyModel strategyModel = new StrategyModel();
		strategyModel.setStrategySerial("2");
		strategyModel.setExpiry(expiry);
		strategyModel.setType(OptionType.CALL);
		strategyModel.setStrike(strike);
		System.out.println(optionModle.getC_ltp());
		strategyModel.setAvgPrice(optionModle.getC_ltp());
		strategyModel.setQuantity(qnt);
		strategyModel.setSpot_close(spotPrice);
		strategyModel.setTarget(800);
		strategyModel.setStopLoss(-1000);
		strategyModel.setDes("Automated Trade");
		if (optionModle.getC_iv() != null) {
			strategyModel.setTraded_IV(optionModle.getC_iv());
		}

		strategyModel.setTradeDate(DateUtils.dateToString(new Date(), "ddMMMyyyy").toUpperCase());
		return strategyModel;
	}
}
