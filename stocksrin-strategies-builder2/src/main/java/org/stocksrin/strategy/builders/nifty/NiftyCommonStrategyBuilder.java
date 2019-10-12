package org.stocksrin.strategy.builders.nifty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.utils.StrategyUtils;

@Controller
public class NiftyCommonStrategyBuilder extends CommonStrategyBuilder {

	private static final String SYMBOLE = "Nifty";

	public StrategyEntity ironFly(String expiry, OptionModles optionModles, String name, TradeHoldingTime tradeHoldingTime, int flydistance) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);

		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(optionModles.getSpot());

		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(optionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(optionModles.getLastDataUpdated()));

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - flydistance;
		double uperStrike = atmStrike + flydistance;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, expiry, 75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade03 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade04 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, expiry, 75, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.add(optionTrade03);
		trades.add(optionTrade04);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

	public StrategyEntity straddle3(String expiry, OptionModles optionModles, String name, TradeHoldingTime tradeHoldingTime) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);

		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(optionModles.getSpot());

		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(optionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(optionModles.getLastDataUpdated()));

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, expiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, lowerStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade03 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, expiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade04 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade05 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, uperStrike, expiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade06 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.add(optionTrade03);
		trades.add(optionTrade04);

		trades.add(optionTrade05);
		trades.add(optionTrade06);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

	public StrategyEntity strangleSafe(String expiry, OptionModles optionModles, String name, TradeHoldingTime tradeHoldingTime, int strangelDistance, int safeDistance) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);

		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(optionModles.getSpot());

		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(optionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(optionModles.getLastDataUpdated()));

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - strangelDistance;
		double uperStrike = atmStrike + strangelDistance;
		double lowerStrikeSafe = lowerStrike - safeDistance;
		double uperStrikeSafe = uperStrike + safeDistance;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade011 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrikeSafe, expiry, 150, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, expiry, -150, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, expiry, -150, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade022 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrikeSafe, expiry, 150, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade011);
		trades.add(optionTrade01);
		trades.add(optionTrade02);
		trades.add(optionTrade022);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

	public StrategyEntity strangle(String expiry, OptionModles optionModles, String name, TradeHoldingTime tradeHoldingTime, int strangelDistance) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);

		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(optionModles.getSpot());

		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(optionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(optionModles.getLastDataUpdated()));

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - strangelDistance;
		double uperStrike = atmStrike + strangelDistance;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, expiry, -150, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, expiry, -150, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

	public StrategyEntity straddle3Safe(String expiry, OptionModles optionModles, String name, TradeHoldingTime tradeHoldingTime) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);

		Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(optionModles.getSpot());

		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(optionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(optionModles.getLastDataUpdated()));

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;

		double lowerStrikeSafe = atmStrike - 150;
		double uperStrikeSafe = atmStrike + 150;

		List<TradeEntity> trades = new ArrayList<>();
		TradeEntity optionTrade011 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrikeSafe, expiry, 225, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, expiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, lowerStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade03 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, expiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade04 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade05 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, uperStrike, expiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade06 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, expiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade066 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrikeSafe, expiry, 225, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade011);
		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.add(optionTrade03);
		trades.add(optionTrade04);

		trades.add(optionTrade05);
		trades.add(optionTrade06);
		trades.add(optionTrade066);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}
}
