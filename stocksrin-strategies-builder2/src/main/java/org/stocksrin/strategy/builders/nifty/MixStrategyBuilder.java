package org.stocksrin.strategy.builders.nifty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.utils.StrategyUtils;

@Controller
public class MixStrategyBuilder {

	private static final String NIFTY_SYMBOLE = "Nifty";
	private static final String BANK_SYMBOLE = "BankNifty";

	public StrategyEntity mixstrangle(String expiry, OptionModles bnfOptionModles, OptionModles niftyOptionModles, String name, TradeHoldingTime tradeHoldingTime, int niftystrangelDistance,
			int bnfstrangelDistance) throws Exception {

		String tradedDate = StrategyUtils.getDateFromlastUpdatedTime(niftyOptionModles.getLastDataUpdated());
		StrategyEntity strategyEntity = new StrategyEntity(name, "NIFTY_BNF", tradeHoldingTime, tradedDate);

		// Long dte = DateUtils.getDte(expiry, "ddMMMyyyy");

		strategyEntity.setTradedPrice(niftyOptionModles.getSpot());
		strategyEntity.setTradedDay(StrategyUtils.getDayFromlastUpdatedTime(niftyOptionModles.getLastDataUpdated()));
		strategyEntity.setTradedTime(StrategyUtils.getTimeFromlastUpdatedTime(niftyOptionModles.getLastDataUpdated()));

		double atmStrike = niftyOptionModles.getAtmStrike();
		double lowerStrike = atmStrike - niftystrangelDistance;
		double uperStrike = atmStrike + niftystrangelDistance;

		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity optionTrade01 = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, niftyOptionModles, lowerStrike, expiry, 150, NIFTY_SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02 = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, niftyOptionModles, uperStrike, expiry, 150, NIFTY_SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01);
		trades.add(optionTrade02);

		double bnfatmStrike = bnfOptionModles.getAtmStrike();
		double bnflowerStrike = bnfatmStrike - bnfstrangelDistance;
		double bnfuperStrike = bnfatmStrike + bnfstrangelDistance;

		TradeEntity optionTrade01bnf = StrategyUtils.buildTradeOptionTrade(OptionType.PUT, bnfOptionModles, bnflowerStrike, expiry, -40, BANK_SYMBOLE, tradeHoldingTime);
		TradeEntity optionTrade02bnf = StrategyUtils.buildTradeOptionTrade(OptionType.CALL, bnfOptionModles, bnfuperStrike, expiry, -40, BANK_SYMBOLE, tradeHoldingTime);

		trades.add(optionTrade01bnf);
		trades.add(optionTrade02bnf);

		trades.forEach((i) -> i.setStrategyEntity(strategyEntity));
		strategyEntity.setTrades(trades);
		return strategyEntity;
	}

}
