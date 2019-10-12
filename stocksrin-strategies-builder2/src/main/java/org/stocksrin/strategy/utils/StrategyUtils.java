package org.stocksrin.strategy.utils;

import java.util.Date;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.strategy.db.model.DerivativeTrade;
import org.stocksrin.strategy.db.model.DerivativeType;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.db.model.TradeType;

public class StrategyUtils {

	public static TradeEntity buildTradeOptionTrade(OptionType optionType, OptionModles optionModles, double strike, String expiry, int quantity, String symbole, TradeHoldingTime tradeHoldingTime)
			throws Exception {

		TradeEntity tradeEntity = null;
		for (OptionModle optionModle : optionModles.getOptionModle()) {
			if (optionModle.getStrike_price().equals(strike)) {
				tradeEntity = buildOptionTrade(optionType, optionModle, expiry, strike, optionModles.getSpot(), symbole, optionModles.getLastDataUpdated(), quantity);
			}
		}
		tradeEntity.setTradeHoldingTime(tradeHoldingTime);
		// optionTrade.setUnderlayingPrice_at_trade(underlayingPrice_at_trade);
		return tradeEntity;
	}

	public static TradeEntity buildOptionTrade(OptionType optionType, OptionModle optionModle, String expiry, double strike, double spotPrice, String symbole, String tradeTime, int quantity)
			throws Exception {
		DerivativeTrade optionTrade = new DerivativeTrade();
		optionTrade.setStrike(strike);
		optionTrade.setExpiry(expiry);

		long dte = DateUtils.getDte(expiry, "ddMMMyyyy");
		optionTrade.setDte(dte);
		// Trade trade = new Trade(symbole, TradeType.OPTION);
		TradeEntity trade = new TradeEntity();
		if (OptionType.PUT.equals(optionType)) {
			optionTrade.setOptionType(DerivativeType.PUT);
			trade.setTradedPrice(optionModle.getP_ltp());
			trade.setLtp(optionModle.getP_ltp());
			if (optionModle.getP_iv() != null) {
				optionTrade.setIv(optionModle.getP_iv());
			}
		} else if (OptionType.CALL.equals(optionType)) {
			optionTrade.setOptionType(DerivativeType.CALL);
			trade.setTradedPrice(optionModle.getC_ltp());
			trade.setLtp(optionModle.getC_ltp());
			if (optionModle.getC_iv() != null) {
				optionTrade.setIv(optionModle.getC_iv());
			}
		}
		optionTrade.setUnderlyingTradedprice(spotPrice);
		optionTrade.setUnderlyingltp(spotPrice);

		trade.setDataUpdatedAt(tradeTime);
		trade.setTradedDate(getDateFromlastUpdatedTime(tradeTime));

		trade.setTradedDay(getDayFromlastUpdatedTime(tradeTime));
		trade.setTradedTime(getTimeFromlastUpdatedTime(tradeTime));

		trade.setQuantity(quantity);
		trade.setDerivativeTrade(optionTrade);
		trade.setTradeType(TradeType.OPTION);
		trade.setSymbole(symbole);
		return trade;
	}

	public static String getDateFromlastUpdatedTime(String time) throws Exception {
		time = time.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(time, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "ddMMMyyyy");
	}

	public static String getTimeFromlastUpdatedTime(String time) throws Exception {
		time = time.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(time, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "HH:mm:ss");
	}

	public static String getDayFromlastUpdatedTime(String time) throws Exception {
		time = time.replace("IST", "").trim();
		Date d = DateUtils.stringToDate(time, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "EEEEE");
	}
}
