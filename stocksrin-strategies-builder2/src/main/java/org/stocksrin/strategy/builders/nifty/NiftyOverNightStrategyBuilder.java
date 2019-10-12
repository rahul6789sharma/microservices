package org.stocksrin.strategy.builders.nifty;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.options.ExpiryUtils;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;
import org.stocksrin.strategy.utils.StrategyUtils;

// on expiry Day make current expiry as next expiry
@Controller
public class NiftyOverNightStrategyBuilder {

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired
	private StrategyDAOService strategyDAOService;

	@Autowired
	private NiftyCommonStrategyBuilder niftyCommonStrategyBuilder;

	public StrategyEntity ironFlyNifty(String strategyName, TradeHoldingTime tradeHoldingTime, int flydistance) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();
			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
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
				strategyName = strategyName + "_" + tradedDate;
				StrategyEntity strategyEntity = niftyCommonStrategyBuilder.ironFly(currentExpiry, optionModles, strategyName, tradeHoldingTime, flydistance);
				// StrategyEntity strategyEntity = ironFly(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
				return strategyEntity;
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public StrategyEntity strangleSafe(String strategyName, TradeHoldingTime tradeHoldingTime, int stangeleDistance, int safeDistance) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();
			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
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
				strategyName = strategyName + "_" + tradedDate;
				StrategyEntity strategyEntity = niftyCommonStrategyBuilder.strangleSafe(currentExpiry, optionModles, strategyName, tradeHoldingTime, stangeleDistance, safeDistance);
				// StrategyEntity strategyEntity = ironFly(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
				return strategyEntity;
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public StrategyEntity strangle(String strategyName, TradeHoldingTime tradeHoldingTime, int stangeleDistance) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();
			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
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
				strategyName = strategyName + "_" + tradedDate;
				StrategyEntity strategyEntity = niftyCommonStrategyBuilder.strangle(currentExpiry, optionModles, strategyName, tradeHoldingTime, stangeleDistance);
				// StrategyEntity strategyEntity = ironFly(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
				return strategyEntity;
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public StrategyEntity straddle3Nifty(String strategyName, TradeHoldingTime tradeHoldingTime) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();
			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
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
				strategyName = strategyName + "_" + tradedDate;
				StrategyEntity strategyEntity = niftyCommonStrategyBuilder.straddle3(currentExpiry, optionModles, strategyName, tradeHoldingTime);
				// StrategyEntity strategyEntity = ironFly(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
				return strategyEntity;
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public StrategyEntity straddle3NiftySafe(String strategyName, TradeHoldingTime tradeHoldingTime) {

		try {
			strategyName = strategyName + "_" + tradeHoldingTime.toString();
			StrategyEntity s = strategyDAOService.get(strategyName);
			if (s == null) {
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
				strategyName = strategyName + "_" + tradedDate;
				StrategyEntity strategyEntity = niftyCommonStrategyBuilder.straddle3Safe(currentExpiry, optionModles, strategyName, tradeHoldingTime);
				// StrategyEntity strategyEntity = ironFly(strategyName, tradeHoldingTime, 150);
				strategyDAOService.save(strategyEntity);
				return strategyEntity;
			} else {
				System.out.println("Strategy already exist");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
}
