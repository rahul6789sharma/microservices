package org.stocksrin.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.trade.FutureTrade;
import org.stocksrin.common.model.trade.OptionTrade;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.model.trade.StrategyModel;
import org.stocksrin.common.model.trade.Trade;
import org.stocksrin.common.model.trade.TradeType;
import org.stocksrin.common.model.trade.UnderLyingInstrument;

public class StrategyUtils2 {

	private final static double target = 1000;



	public static Strategy buildStrategy(String name, List<OptionModle> optionModles, double strike, OptionType optionType, String expiry, double spotPrice, int qnt) throws Exception {
		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
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
		strategyModel.setTarget(target);
		strategyModel.setStopLoss(-target);
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
		strategyModel.setAvgPrice(optionModle.getC_ltp());
		strategyModel.setQuantity(qnt);
		strategyModel.setSpot_close(spotPrice);
		strategyModel.setTarget(target);
		strategyModel.setStopLoss(-target);
		strategyModel.setDes("Automated Trade");
		if (optionModle.getC_iv() != null) {
			strategyModel.setTraded_IV(optionModle.getC_iv());
		}

		strategyModel.setTradeDate(DateUtils.dateToString(new Date(), "ddMMMyyyy").toUpperCase());
		return strategyModel;
	}

	public static Strategy buildStrategy2(String name, List<OptionModle> optionModles, double strike, OptionType optionType, String expiry, double spotPrice, int qnt) throws Exception {
		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
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

	public static Trade buildTradeFutureTrade(double futureTradedPrice, OptionModles optionModles, String expiry, int quantity, String symbole) throws Exception {

		Trade trade = new Trade(symbole, TradeType.FUTURE);

		FutureTrade futureTrade = new FutureTrade();
		futureTrade.setExpiry(expiry);
		futureTrade.setFutureTradedPrice(futureTradedPrice);

		trade.setFutureTrade(futureTrade);
		trade.setQuantity(quantity);

		trade.setDataUpdatedAt(optionModles.getLastDataUpdated());
		trade.setTradeDate(getDate(optionModles.getLastDataUpdated()));
		trade.setTradeDay(getDay(optionModles.getLastDataUpdated()));
		trade.setTradeTime(getTime(optionModles.getLastDataUpdated()));

		trade.setTradePrice(optionModles.getSpot());

		// optionTrade.setUnderlayingPrice_at_trade(underlayingPrice_at_trade);
		return trade;
	}

	public static Trade buildTradeOptionTrade(OptionType optionType, OptionModles optionModles, double strike, String expiry, int quantity, String symbole) throws Exception {

		Trade optionTrade = null;
		for (OptionModle optionModle : optionModles.getOptionModle()) {
			if (optionModle.getStrike_price().equals(strike)) {
				optionTrade = build(optionType, optionModle, expiry, strike, optionModles.getSpot(), symbole, optionModles.getLastDataUpdated(), quantity);
			}
		}
		// optionTrade.setUnderlayingPrice_at_trade(underlayingPrice_at_trade);
		return optionTrade;
	}

	public static Trade build(OptionType optionType, OptionModle optionModle, String expiry, double strike, double spotPrice, String symbole, String tradeTime, int quantity) throws Exception {
		OptionTrade optionTrade = new OptionTrade(optionType, expiry);
		optionTrade.setStrike(strike);
		long dte = DateUtils.getDte(expiry, "ddMMMyyyy");
		optionTrade.setDte(dte);
		Trade trade = new Trade(symbole, TradeType.OPTION);
		if (OptionType.PUT.equals(optionType)) {
			optionTrade.setOptionTradedPrice(optionModle.getP_ltp());
			if (optionModle.getP_iv() != null) {
				optionTrade.setIv(optionModle.getP_iv());
			}
		} else if (OptionType.CALL.equals(optionType)) {
			optionTrade.setOptionTradedPrice(optionModle.getC_ltp());
			if (optionModle.getC_iv() != null) {
				optionTrade.setIv(optionModle.getC_iv());
			}
		}
		trade.setTradePrice(spotPrice);
		trade.setQuantity(quantity);
		trade.setDataUpdatedAt(tradeTime);
		trade.setTradeDate(getDate(tradeTime));
		trade.setTradeDay(getDay(tradeTime));
		trade.setTradeTime(getTime(tradeTime));
		trade.setOptionTrade(optionTrade);
		return trade;
	}

	private static String getDate(String time) throws Exception {
		time = time.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(time, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "ddMMMyyyy");
	}

	private static String getTime(String time) throws Exception {
		time = time.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(time, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "HH:mm:ss");
	}

	private static String getDay(String time) throws Exception {
		time = time.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(time, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "EEEEE");
	}

	public static void main(String[] args) throws Exception {
		String sDate = "Aug 30, 2019 15:30:30 IST";
		sDate = sDate.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(sDate, "MMM dd, yyyy HH:mm:ss");
		System.out.println(d);
		String mon = DateUtils.dateToString(d, "ddMMMyyyy");
		System.out.println(mon);
	}
}
