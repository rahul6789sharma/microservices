package org.stocksrin.strategies.common.strategies;

import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategies.common.utils.NityStrategyUtils;

@Controller
public class NiftyStrategies {

	private final Logger log = LoggerFactory.getLogger(NiftyStrategies.class);
	public static void main(String[] args) throws Exception {
		String currentExpiry="31JAN2019";
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		System.out.println(dte);
	}
	
	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	public String Strategy3StraddleNifty(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = straddleNifty();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("IntraDayFile already exist " + dir, "", "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String strangleOTM200PointFar(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangleNiftyOTM200PointFar();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("IntraDayFile already exist " + dir, "", "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String Strategy3StrangleOTM(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangleNiftyOTM();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("IntraDayFile already exist " + dir, "", "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String Strategy3StrangleITM(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangleNiftyITM();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("IntraDayFile already exist " + dir, "", "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	private Strategy strangleNiftyOTM() throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry, currentExpiry);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = NityStrategyUtils.getATMStrike(optionModles, 25);
		double lowerStrike1 = atmStrike - 100;
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;
		double uperStrike1 = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1put = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike1, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2put = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Put = NityStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg1Call = NityStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike1, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg1put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());

		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		return strategy;
	}

	private Strategy strangleNiftyOTM200PointFar() throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry, currentExpiry);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = NityStrategyUtils.getATMStrike(optionModles, 25);

		double lowerStrike1 = atmStrike - 200;
		double uperStrike1 = atmStrike + 200;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -150;
		Strategy leg1put = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike1, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3Call = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike1, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg1put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		return strategy;
	}

	private Strategy strangleNiftyITM() throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = allExpiry.first();

		currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry, currentExpiry);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = NityStrategyUtils.getATMStrike(optionModles, 25);
		double lowerStrike1 = atmStrike + 100;
		double lowerStrike = atmStrike + 50;
		double uperStrike = atmStrike - 50;
		double uperStrike1 = atmStrike - 100;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1put = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike1, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2put = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Put = NityStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg1Call = NityStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike1, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1put.getStrategyModels());

		return strategy;
	}

	private Strategy straddleNifty() throws Exception {

		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = allExpiry.first();
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		currentExpiry = NityStrategyUtils.getNiftyExpiryForDayTrading(allExpiry, currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = NityStrategyUtils.getATMStrike(optionModles, 25);
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1Put = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg1Call = NityStrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg2Put = NityStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = NityStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3Put = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = NityStrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg1Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg2Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		return strategy;
	}

}
