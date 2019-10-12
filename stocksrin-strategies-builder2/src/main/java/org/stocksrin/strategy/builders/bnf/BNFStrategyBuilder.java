package org.stocksrin.strategy.builders.bnf;

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
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.utils.StrategyUtils;

@Controller
public class BNFStrategyBuilder {

	private static final Logger log = LoggerFactory.getLogger(BNFStrategyBuilder.class);

	private static final String SYMBOLE = "BankNifty";

	@Autowired
	private BNFConsumeWebService bnfConsumeWebService;

	@Autowired
	private StrategyDAOService strategyDAOService;

	public void expiryDayCaladner(String strategyName, TradeHoldingTime tradeHoldingTime) {

		try {

			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
				StrategyEntity strategyEntity = expiryDayCalander(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private StrategyEntity expiryDayCalander(String name, TradeHoldingTime tradeHoldingTime, int flydistance) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = new ArrayList<>(allExpiry).get(0);
		String nextExpiry = new ArrayList<>(allExpiry).get(1);

		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);
		OptionModles optionModles2 = bnfConsumeWebService.getOptionModel(nextExpiry);
		if (optionModles == null || optionModles2 == null) {
			return null;
		}
		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(optionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, SYMBOLE, tradeHoldingTime, tradedDate);
		strategyEntity.setName(name);
		strategyEntity.setStrategyHoldingTime(tradeHoldingTime);

		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, lowerStrike, currentExpiry, -20, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, lowerStrike, currentExpiry, -20, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade03 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, atmStrike, currentExpiry, -20, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade04 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, atmStrike, currentExpiry, -20, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade05 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles, uperStrike, currentExpiry, -20, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade06 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles, uperStrike, currentExpiry, -20, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade011 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles2, lowerStrike, nextExpiry, 20, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade022 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles2, lowerStrike, nextExpiry, 20, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade033 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles2, atmStrike, nextExpiry, 20, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade044 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles2, atmStrike, nextExpiry, 20, SYMBOLE, tradeHoldingTime);

		TradeEntity optionTrade055 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, optionModles2, uperStrike, nextExpiry, 20, SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade066 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, optionModles2, uperStrike, nextExpiry, 20, SYMBOLE, tradeHoldingTime);

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
