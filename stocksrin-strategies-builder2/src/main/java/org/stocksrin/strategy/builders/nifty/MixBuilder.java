package org.stocksrin.strategy.builders.nifty;

import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class MixBuilder {

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired
	private BNFConsumeWebService bnfConsumeWebService;

	@Autowired
	private StrategyDAOService strategyDAOService;

	@Autowired
	private MixStrategyBuilder mixStrategyBuilder;

	// if todays file exists don't create
	public void mixStrangle(String strategyName, TradeHoldingTime tradeHoldingTime, int niftyDistacne, int bankniftyDistacne) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();

			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
				createMix(strategyName, tradeHoldingTime, niftyDistacne, bankniftyDistacne);
			} else {
				// is this name startegy exists, check date if trade date if it is for today
				// dont create if it is for other then today then delete already existing
				// startegy and create new
				if (DateUtils.getTodayDate().equalsIgnoreCase(s.getTradedDate())) {
					System.out.println("Today intraday Strategy already exist : " + strategyName);
				} else {
					// delete any previous strategy exists
					strategyDAOService.delete(s);
					createMix(strategyName, tradeHoldingTime, niftyDistacne, bankniftyDistacne);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private StrategyEntity createMix(String strategyName, TradeHoldingTime tradeHoldingTime, int niftyDistance, int bankNiftyDistance) throws Exception {
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = allExpiry.first();
		OptionModles niftyoptionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		OptionModles bnfoptionModles = bnfConsumeWebService.getOptionModel(currentExpiry);
		if (niftyoptionModles == null || bnfoptionModles == null) {
			return null;
		}
		StrategyEntity strategyEntity = mixStrategyBuilder.mixstrangle(currentExpiry, bnfoptionModles, niftyoptionModles, strategyName, tradeHoldingTime, niftyDistance, bankNiftyDistance);
		return strategyDAOService.save(strategyEntity);
	}
}
