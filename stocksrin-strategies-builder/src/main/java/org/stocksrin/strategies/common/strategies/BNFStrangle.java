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
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.strategies.common.utils.BNFStrategyUtils;

@Controller
public class BNFStrangle {
	private final Logger log = LoggerFactory.getLogger(BNFStrangle.class);
	
	@Autowired
	private BNFConsumeWebService bnfConsumeWebService;

	private static final int lot = 20;

	public String strangle_300PointAway_protected_build(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangle_300PointAway_protected();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("Intra DayFile strategy already exist " + file, "", "Strategies-BUilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "Strategies-BUilder");
		}
		return file;
	}

	public String otmStrangle3(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangle_3OTM();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("DayFile strategy already exist " + file, "", "Strategies-BUilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "","Strategies-BUilder");
		}
		return file;
	}

	public String itmStrangle3(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangle_3ITM();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("DayFile strategy already exist " + file, "", "Strategies-BUilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "Strategies-BUilder");
		}
		return file;
	}

	public String itm2Strangle3(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = strangle_3ITM2();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("DayFile strategy already exist " + file, "", "Strategies-BUilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "Strategies-BUilder");
		}
		return file;
	}

	private Strategy strangle_3ITM() throws Exception {

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);
		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double ceLeg1 = atmStrike - 300;
		double ceLeg2 = atmStrike - 200;
		double ceLeg3 = atmStrike - 100;

		double peLeg1 = atmStrike + 100;
		double peLeg2 = atmStrike + 200;
		double peLeg3 = atmStrike + 300;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy leg1ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg1, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg2ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg2, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg3ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg3, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy leg4pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg1, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg5pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg2, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg6pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg3, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);

		// order is imp to visulization
		strategy.getStrategyModels().addAll(leg1ce.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2ce.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3ce.getStrategyModels());

		strategy.getStrategyModels().addAll(leg4pe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg5pe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg6pe.getStrategyModels());

		return strategy;
	}

	private Strategy strangle_3ITM2() throws Exception {

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);
		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double ceLeg1 = atmStrike + 100;
		double ceLeg2 = atmStrike + 200;
		// double ceLeg3 = atmStrike + 300;

		double peLeg1 = atmStrike - 100;
		double peLeg2 = atmStrike - 200;
		// double peLeg3 = atmStrike - 300;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy leg1ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg1, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg2ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg2, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg3ce = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy leg1pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg1, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg2pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg2, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg3pe = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);

		strategy.getStrategyModels().addAll(leg2pe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1pe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3pe.getStrategyModels());

		// order is imp to visulization
		strategy.getStrategyModels().addAll(leg3ce.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1ce.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2ce.getStrategyModels());

		return strategy;
	}

	private Strategy strangle_300PointAway_protected() throws Exception {

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);
		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double putStrike = atmStrike - 300;
		double callStrike = atmStrike + 300;

		double putStrikeProtection = atmStrike - 500;
		double callStrikeProtection = atmStrike + 500;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy putlegProtect = BNFStrategyUtils.buildStrategy("BNF", lst, putStrikeProtection, OptionType.PUT, currentExpiry, optionModles.getSpot(), (lot * 3));
		Strategy putleg = BNFStrategyUtils.buildStrategy("BNF", lst, putStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -(lot * 3));

		Strategy callLeg = BNFStrategyUtils.buildStrategy("BNF", lst, callStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -(lot * 3));
		Strategy callLegProtected = BNFStrategyUtils.buildStrategy("BNF", lst, callStrikeProtection, OptionType.CALL, currentExpiry, optionModles.getSpot(), (lot * 3));

		strategy.getStrategyModels().addAll(putlegProtect.getStrategyModels());
		strategy.getStrategyModels().addAll(putleg.getStrategyModels());
		strategy.getStrategyModels().addAll(callLeg.getStrategyModels());
		strategy.getStrategyModels().addAll(callLegProtected.getStrategyModels());

		return strategy;
	}

	private Strategy strangle_3OTM() throws Exception {

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);
		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double ceLeg1 = atmStrike + 100;
		double ceLeg2 = atmStrike + 200;
		double ceLeg3 = atmStrike + 300;

		double peLeg1 = atmStrike - 100;
		double peLeg2 = atmStrike - 200;
		double peLeg3 = atmStrike - 300;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy leg1ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg1, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg2ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg2, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg3ce = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg3, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy leg1pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg1, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg2pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg2, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy leg3pe = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg3, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);

		strategy.getStrategyModels().addAll(leg3pe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2pe.getStrategyModels());
		strategy.getStrategyModels().addAll(leg1pe.getStrategyModels());

		// order is imp to visulization
		strategy.getStrategyModels().addAll(leg1ce.getStrategyModels());
		strategy.getStrategyModels().addAll(leg2ce.getStrategyModels());
		strategy.getStrategyModels().addAll(leg3ce.getStrategyModels());

		return strategy;
	}

}