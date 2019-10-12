package org.stocksrin.strategy.builders.nifty;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.StrategyUtils2;
import org.stocksrin.common.utils.options.ExpiryUtils;
import org.stocksrin.common.utils.options.OptionUtils;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.utils.StrategyUtils;

@Controller
public class NiftyStrategyBuilder {

	private static final Logger log = LoggerFactory.getLogger(NiftyStrategyBuilder.class);

	private static final String SYMBOLE = "Nifty";

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired
	private StrategyDAOService strategyDAOService;

	public void straddle3Nifty(String strategyName, TradeHoldingTime tradeHoldingTime) {

		try {

			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
				StrategyEntity strategyEntity = straddle3NiftyBuild(strategyName, tradeHoldingTime);
				strategyDAOService.save(strategyEntity);
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void ironFlyNifty(String strategyName, TradeHoldingTime tradeHoldingTime) {

		try {

			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
				StrategyEntity strategyEntity = ironFly(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void expiryDayCaladner(String strategyName, TradeHoldingTime tradeHoldingTime) {

		try {

			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
				System.out.println("building " + strategyName);
				StrategyEntity strategyEntity = expiryDayCalander(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private StrategyEntity straddle3NiftyBuild(String name, TradeHoldingTime tradeHoldingTime) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = null;
		if (!ExpiryUtils.isTodayExpiry(allExpiry)) {
			currentExpiry = allExpiry.first();
		} else {
			// go to new Expiry
			List<String> expiries = new ArrayList<>(allExpiry);
			currentExpiry = expiries.get(1);
		}

		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);
		strategyEntity.setName(name);
		strategyEntity.setStrategyHoldingTime(tradeHoldingTime);

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 50;
		double upperStrike = atmStrike + 50;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, lowerStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade11 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade12 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade21 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, upperStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade22 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, upperStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.add(optionTrade11);
		trades.add(optionTrade12);

		trades.add(optionTrade21);
		trades.add(optionTrade22);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

	private StrategyEntity ironFly(String name, TradeHoldingTime tradeHoldingTime, int flydistance) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = null;

		if (!ExpiryUtils.isTodayExpiry(allExpiry)) {
			currentExpiry = allExpiry.first();
		} else {
			// go to new Expiry
			List<String> expiries = new ArrayList<>(allExpiry);
			currentExpiry = expiries.get(1);
		}
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);
		strategyEntity.setName(name);
		strategyEntity.setStrategyHoldingTime(tradeHoldingTime);

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - flydistance;
		double uperStrike = atmStrike + flydistance;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, currentExpiry, 75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade03 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade04 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, currentExpiry, 75, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.add(optionTrade03);
		trades.add(optionTrade04);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

	private StrategyEntity expiryDayCalander(String name, TradeHoldingTime tradeHoldingTime, int flydistance) throws Exception {

		StrategyEntity strategyEntity = new StrategyEntity();
		strategyEntity.setName(name);
		strategyEntity.setStrategyHoldingTime(tradeHoldingTime);

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = new ArrayList<>(allExpiry).get(0);
		String nextExpiry = new ArrayList<>(allExpiry).get(1);

		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		OptionModles optionModles2 = niftyConsumeWebService.getOptionModel(nextExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, lowerStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade03 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade04 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade05 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade06 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, uperStrike, currentExpiry, -75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade011 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles2, lowerStrike, nextExpiry, 75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade022 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles2, lowerStrike, nextExpiry, 75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade033 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles2, atmStrike, nextExpiry, 75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade044 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles2, atmStrike, nextExpiry, 75, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade055 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles2, uperStrike, nextExpiry, 75, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade066 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles2, uperStrike, nextExpiry, 75, SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		trades.add(optionTrade03);
		trades.add(optionTrade04);
		trades.add(optionTrade05);
		trades.add(optionTrade06);

		trades.add(optionTrade011);
		trades.add(optionTrade022);

		trades.add(optionTrade033);
		trades.add(optionTrade044);
		trades.add(optionTrade055);
		trades.add(optionTrade066);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}
}
