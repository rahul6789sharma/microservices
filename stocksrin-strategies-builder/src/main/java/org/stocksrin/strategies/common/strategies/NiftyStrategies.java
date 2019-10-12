package org.stocksrin.strategies.common.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.model.trade.UnderLyingInstrument;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.options.ExpiryUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategies.common.utils.StrategyUtils;

@Controller
public class NiftyStrategies {

	private final Logger log = LoggerFactory.getLogger(NiftyStrategies.class);

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	public static void main(String[] args) throws Exception {
		String currentExpiry = "31JAN2019";
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		System.out.println(dte);
	}
	
	public String itmCalender(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = itmCalander();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String strangle100(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangle100();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String ironFlyNifty(String fileName, String dir, int flydistance) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = ironFly(flydistance);
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String putButterFlyNifty(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = putButterFly();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String callButterFlyNifty(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = callButterFly();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String Strategy3StraddleNiftySafe(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = straddleNiftySafe();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "strategyBuilder");
		}
		return file;
	}

	public String Strategy3StraddleNifty(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = straddleNifty();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
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
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
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
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
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
				log.warn("Intra DayFile strategy already exist " + file);
				// SendEmail.sentMail("IntraDayFile already exist " + dir, "",
				// "strategyBuilder");
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
		String currentExpiry = null;

		if (!ExpiryUtils.isTodayExpiry(allExpiry)) {
			currentExpiry = allExpiry.first();
		} else {
			// go to new Expiry
			List<String> expiries = new ArrayList<>(allExpiry);
			currentExpiry = expiries.get(1);
		}

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike1 = atmStrike - 100;
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;
		double uperStrike1 = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1put = StrategyUtils.buildStrategy("BNF", lst, lowerStrike1, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2put = StrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Put = StrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg1Call = StrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = StrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = StrategyUtils.buildStrategy("BNF", lst, uperStrike1, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

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
		// String currentExpiry = allExpiry.first();

		String currentExpiry = null;

		if (!ExpiryUtils.isTodayExpiry(allExpiry)) {
			currentExpiry = allExpiry.first();
		} else {
			// go to new Expiry
			List<String> expiries = new ArrayList<>(allExpiry);
			currentExpiry = expiries.get(1);
		}

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();

		double lowerStrike1 = atmStrike - 200;
		double uperStrike1 = atmStrike + 200;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -150;
		Strategy leg1put = StrategyUtils.buildStrategy("BNF", lst, lowerStrike1, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3Call = StrategyUtils.buildStrategy("BNF", lst, uperStrike1, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg1put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		return strategy;
	}

	private Strategy strangleNiftyITM() throws Exception {

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
		// String currentExpiry = allExpiry.first();

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike1 = atmStrike + 100;
		double lowerStrike = atmStrike + 50;
		double uperStrike = atmStrike - 50;
		double uperStrike1 = atmStrike - 100;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1put = StrategyUtils.buildStrategy("BNF", lst, lowerStrike1, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2put = StrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Put = StrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg1Call = StrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = StrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = StrategyUtils.buildStrategy("BNF", lst, uperStrike1, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1put.getStrategyModels());

		return strategy;
	}

	private Strategy itmCalander() throws Exception {
		// String currentExpiry = NiftyData.shortedExpiry.first();
		SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
		String currentExpiry = null;
		String nextExpiry = null;
		nextExpiry = ExpiryUtils.getCurrentMonthExpiry(new ArrayList<>(allExpiry));
		// on expiryDay select next expiry
		if (!ExpiryUtils.isTodayExpiry(allExpiry)) {
			currentExpiry = allExpiry.first();

		} else {
			// go to new Expiry
			if (ExpiryUtils.isMonthlyExpiry(new ArrayList<>(allExpiry))) {
				List<String> expiries = new ArrayList<>(allExpiry);
				currentExpiry = expiries.get(2);
				nextExpiry = ExpiryUtils.getNextMonthlyExpiry(new ArrayList<>(allExpiry));
			}
			List<String> expiries = new ArrayList<>(allExpiry);
			currentExpiry = expiries.get(1);
		}
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		OptionModles optionModlesNext = niftyConsumeWebService.getOptionModel(nextExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();

		List<OptionModle> lst = optionModles.getOptionModle();
		List<OptionModle> lstNext = optionModlesNext.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;

		Strategy leg2Put = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Put2 = StrategyUtils.buildStrategy("NF", lstNext, atmStrike, OptionType.PUT, nextExpiry, optionModlesNext.getSpot(), 75);
		Strategy leg2Call2 = StrategyUtils.buildStrategy("NF", lstNext, atmStrike, OptionType.CALL, nextExpiry, optionModlesNext.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg2Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());
		
		strategy.getStrategyModels().addAll(leg2Put2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call2.getStrategyModels());

		return strategy;
	}

	private Strategy straddleNiftySafe() throws Exception {

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

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;

		double lowerStrikeSafe = atmStrike - 150;
		double uperStrikeSafe = atmStrike + 150;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1PutLowerSafe = StrategyUtils.buildStrategy("NF", lst, lowerStrikeSafe, OptionType.PUT, currentExpiry, optionModles.getSpot(), 225);

		Strategy leg1Put = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg1Call = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg2Put = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3Put = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3CallSafe = StrategyUtils.buildStrategy("NF", lst, uperStrikeSafe, OptionType.CALL, currentExpiry, optionModles.getSpot(), 225);

		strategy.getStrategyModels().addAll(leg1PutLowerSafe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg2Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3CallSafe.getStrategyModels());
		return strategy;
	}

	private Strategy strangle100() throws Exception {

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

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();

		double lowerStrikeSafe = atmStrike - 100;
		double uperStrikeSafe = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -150;

		Strategy putleg = StrategyUtils.buildStrategy("NF", lst, lowerStrikeSafe, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);

		Strategy callleg = StrategyUtils.buildStrategy("NF", lst, uperStrikeSafe, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(putleg.getStrategyModels());
		strategy.getStrategyModels().addAll(callleg.getStrategyModels());

		return strategy;
	}

	private Strategy straddleNifty() throws Exception {

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

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 50;
		double uperStrike = atmStrike + 50;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		Strategy leg1Put = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg1Call = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg2Put = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg2Call = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		Strategy leg3Put = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt);
		Strategy leg3Call = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt);

		strategy.getStrategyModels().addAll(leg1Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg2Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2Call.getStrategyModels());

		strategy.getStrategyModels().addAll(leg3Put.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3Call.getStrategyModels());
		return strategy;
	}

	private Strategy putButterFly() throws Exception {

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

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;

		Strategy leg1 = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg2 = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -150);
		Strategy leg3 = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3.getStrategyModels());

		return strategy;
	}

	private Strategy callButterFly() throws Exception {

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

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		Strategy leg1 = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg2 = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -150);
		Strategy leg3 = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3.getStrategyModels());

		return strategy;
	}

	private Strategy ironFly(int flydistance) throws Exception {

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

		// currentExpiry = StrategyUtils.getNiftyExpiryForDayTrading(allExpiry,
		// currentExpiry);

		// OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);
		if (optionModles == null) {
			return null;
		}
		double atmStrike = optionModles.getAtmStrike();
		double lowerStrike = atmStrike - flydistance;
		double uperStrike = atmStrike + flydistance;

		List<OptionModle> lst = optionModles.getOptionModle();

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		Strategy leg1 = StrategyUtils.buildStrategy("NF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), 75);
		Strategy leg2 = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -75);
		Strategy leg3 = StrategyUtils.buildStrategy("NF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -75);
		Strategy leg4 = StrategyUtils.buildStrategy("NF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), 75);

		strategy.getStrategyModels().addAll(leg1.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3.getStrategyModels());
		strategy.getStrategyModels().addAll(leg4.getStrategyModels());

		return strategy;
	}
}
