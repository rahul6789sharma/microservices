package org.stocksrin.strategy.builders.nifty;

import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class NiftyIntraDayStrategyBuilder {

	// private static final String SYMBOLE = "Nifty";

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired
	private StrategyDAOService strategyDAOService;

	@Autowired
	private NiftyCommonStrategyBuilder niftyCommonStrategyBuilder;

	// if todays file exists don't create
	public void ironFlyNifty(String strategyName, TradeHoldingTime tradeHoldingTime, int flydistance) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();

			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
				createIronFly(strategyName, tradeHoldingTime, flydistance);
			} else {
				// is this name startegy exists, check date if trade date if it is for today
				// dont create if it is for other then today then delete already existing
				// startegy and create new
				if (DateUtils.getTodayDate().equalsIgnoreCase(s.getTradedDate())) {
					System.out.println("Today intraday Strategy already exist : " + strategyName);
				} else {
					// delete any previous strategy exists
					strategyDAOService.delete(s);
					createIronFly(strategyName, tradeHoldingTime, flydistance);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private StrategyEntity createIronFly(String strategyName, TradeHoldingTime tradeHoldingTime, int flydistance) throws Exception {
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = allExpiry.first();
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		StrategyEntity strategyEntity = niftyCommonStrategyBuilder.ironFly(currentExpiry, optionModles, strategyName, tradeHoldingTime, flydistance);
		return strategyDAOService.save(strategyEntity);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getTodayDate());
	}
}
