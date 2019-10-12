package org.stocksrin.strategies.common.strategies.common;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.model.trade.UnderLyingInstrument;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.restclient.RestService;
import org.stocksrin.strategies.common.utils.StrategyUtils;

@Controller
public class CommonStrategiesBuilder {

	private final Logger log = LoggerFactory.getLogger(CommonStrategiesBuilder.class);

	public String stradel300Calander(String fileName, String dir, RestService restService, UnderLyingInstrument underLyingInstrument, int instrumentStrikeDiff) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = straddle3Calender(restService, underLyingInstrument, instrumentStrikeDiff);
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String strangle(String fileName, String dir, RestService restService, UnderLyingInstrument underLyingInstrument, int instrumentStrikeDiff) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangle(restService, underLyingInstrument, instrumentStrikeDiff);
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String ironFlyFly(String fileName, String dir, RestService restService, UnderLyingInstrument underLyingInstrument, int distance) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = ironFly(restService, underLyingInstrument, distance);
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String callButterFly(String fileName, String dir, RestService restService, UnderLyingInstrument underLyingInstrument) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = callButterFly(restService, underLyingInstrument);
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String putButterFly(String fileName, String dir, RestService restService, UnderLyingInstrument underLyingInstrument) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = putButterFly(restService, underLyingInstrument);
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	private Strategy putButterFly(RestService restService, UnderLyingInstrument underLyingInstrument) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = restService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		/*
		 * if (!ExpiryUtils.isTodayExpiry(allExpiry)) { currentExpiry =
		 * allExpiry.first(); } else { // go to new Expiry List<String> expiries = new
		 * ArrayList<>(allExpiry); currentExpiry = expiries.get(1); }
		 */
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		// currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = restService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(underLyingInstrument);
		strategy.setStrategyName(underLyingInstrument.toString());
		strategy.setDte(dte.toString());

		Strategy leg1 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg2 = StrategyUtils.buildStrategy(underLyingInstrument.toString().toString(), lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -150);
		Strategy leg3 = StrategyUtils.buildStrategy(underLyingInstrument.toString().toString(), lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3.getStrategyModels());

		return strategy;
	}

	private Strategy callButterFly(RestService restService, UnderLyingInstrument underLyingInstrument) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = restService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		/*
		 * if (!ExpiryUtils.isTodayExpiry(allExpiry)) { currentExpiry =
		 * allExpiry.first(); } else { // go to new Expiry List<String> expiries = new
		 * ArrayList<>(allExpiry); currentExpiry = expiries.get(1); }
		 */
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		// currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = restService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(underLyingInstrument);
		strategy.setStrategyName(underLyingInstrument.toString());
		strategy.setDte(dte.toString());

		Strategy leg1 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -150);
		Strategy leg3 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3.getStrategyModels());

		return strategy;
	}

	private Strategy ironFly(RestService restService, UnderLyingInstrument underLyingInstrument, int distance) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = restService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		/*
		 * if (!ExpiryUtils.isTodayExpiry(allExpiry)) { currentExpiry =
		 * allExpiry.first(); } else { // go to new Expiry List<String> expiries = new
		 * ArrayList<>(allExpiry); currentExpiry = expiries.get(1); }
		 */
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		// currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = restService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - distance;
		double uperStrike = atmStrike + distance;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(underLyingInstrument);
		strategy.setStrategyName(underLyingInstrument.toString());
		strategy.setDte(dte.toString());

		Strategy leg1 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -75);
		Strategy leg3 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -75);
		Strategy leg4 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3.getStrategyModels());
		strategy.getStrategyModels().addAll(leg4.getStrategyModels());

		return strategy;
	}

	// instrumentStrikeDiff=50 -> for nifty
	// instrumentStrikeDiff=100 -> for bnfnifty
	private Strategy straddle3Calender(RestService restService, UnderLyingInstrument underLyingInstrument, int instrumentStrikeDiff) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = restService.getAllExpiry();

		String currentExpiry = allExpiry.first();
		List<String> expiries = new ArrayList<>(allExpiry);
		String nextExpiry = expiries.get(1);

		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		OptionModles optionModles = restService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		List<OptionModle> lst = optionModles.getOptionModle();
		double lowerStrike = atmStrike - instrumentStrikeDiff;
		double uperStrike = atmStrike + instrumentStrikeDiff;

		Strategy strategy = new Strategy(underLyingInstrument);
		strategy.setStrategyName(underLyingInstrument.toString());
		strategy.setDte(dte.toString());

		// next Week datae
		OptionModles optionModles2 = restService.getOptionModel(nextExpiry);
		List<OptionModle> lst2 = optionModles2.getOptionModle();

		int qnt = -75;
		int qnt2 = 75;
		Strategy leg1Put = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg1Call = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg1Put2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst2, lowerStrike, OptionType.PUT, nextExpiry, optionModles2.getSpot(), qnt2);
		Strategy leg1Call2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst2, lowerStrike, OptionType.CALL, nextExpiry, optionModles2.getSpot(), qnt2);

		Strategy leg2Put = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg2Put2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst2, atmStrike, OptionType.PUT, nextExpiry, optionModles2.getSpot(), qnt2);
		Strategy leg2Call2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst2, atmStrike, OptionType.CALL, nextExpiry, optionModles2.getSpot(), qnt2);

		Strategy leg3Put = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3Put2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst2, uperStrike, OptionType.PUT, nextExpiry, optionModles2.getSpot(), qnt2);
		Strategy leg3Call2 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst2, uperStrike, OptionType.CALL, nextExpiry, optionModles2.getSpot(), qnt2);

		strategy.getStrategyModels().addAll(leg1Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg1Put2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call2.getStrategyModels());

		strategy.getStrategyModels().addAll(leg2Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg2Put2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call2.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call2.getStrategyModels());

		return strategy;
	}

	private Strategy strangle(RestService restService, UnderLyingInstrument underLyingInstrument, int distance) throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = restService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		OptionModles optionModles = restService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - distance;
		double uperStrike = atmStrike + distance;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(underLyingInstrument);
		strategy.setStrategyName(underLyingInstrument.toString());
		strategy.setDte(dte.toString());

		Strategy leg1 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg4 = StrategyUtils.buildStrategy(underLyingInstrument.toString(), lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg4.getStrategyModels());

		return strategy;
	}
}