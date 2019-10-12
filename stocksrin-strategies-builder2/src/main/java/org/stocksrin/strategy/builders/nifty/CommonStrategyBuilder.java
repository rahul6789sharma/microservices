package org.stocksrin.strategy.builders.nifty;

import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.utils.StrategyUtils;

public class CommonStrategyBuilder {

	private static final String NIFTY = "Nifty";

	public StrategyEntity straddle3(String expiry, OptionModles optionModles, String name, TradeHoldingTime tradeHoldingTime) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, NIFTY, tradeHoldingTime, tradedDate);

		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(optionModles.getSpot());

		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(optionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(optionModles.getLastDataUpdated()));

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, expiry, -75, NIFTY, tradeHoldingTime);
		TradeEntity optionTrade06 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, expiry, -75, NIFTY, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade06);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}
}
