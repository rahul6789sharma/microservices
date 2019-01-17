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
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.model.strategies.StrategyModel;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.strategies.common.utils.BNFStrategyUtils;

@Controller
public class BNFStrategies {

	@Autowired
	private BNFConsumeWebService bnfConsumeWebService;

	private final Logger log = LoggerFactory.getLogger(BNFStrategies.class);
	private final int lot = 20;

	public String Strategy300PointAwaystrangle(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = this.buildStrategy300PointAwaystrangle();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				log.warn("Intra DayFile strategy already exist " + file );
				//SendEmail.sentMail("Intra DayFile strategy already exist " + file, "", "Strategies-BUilder");
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "Strategies-BUilder");
		}
		return file;
	}

	public String BuildStrategy3Straddle(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = this.Strategy3Straddle();
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

	public String ironButterfly(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = this.buildStrategyIronFly();
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

	public String doubleCalender(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = this.buildStrategyDoubleCalender();
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

	private Strategy buildStrategyIronFly() throws Exception {
		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);

		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);

		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double peLeg = atmStrike - 200;
		double ceLeg = atmStrike + 200;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy legPeBuy = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg, OptionType.PUT, currentExpiry, optionModles.getSpot(), lot);
		Strategy legPeSell = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);

		Strategy legCeSell = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCeBuy = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg, OptionType.CALL, currentExpiry, optionModles.getSpot(), lot);

		// order is imp to visulization
		strategy.getStrategyModels().addAll(legPeBuy.getStrategyModels());
		strategy.getStrategyModels().addAll(legPeSell.getStrategyModels());
		strategy.getStrategyModels().addAll(legCeSell.getStrategyModels());
		strategy.getStrategyModels().addAll(legCeBuy.getStrategyModels());

		return strategy;
	}

	public List<StrategyModel> getStradelByStrikeStrategy(double strike) throws Exception {
		List<StrategyModel> strategyModels = new ArrayList<>();

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();

		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);

		OptionModles data = bnfConsumeWebService.getOptionModel(currentExpiry);
		// OptionModles data =
		// BankNiftyData.bnOptionData.get(BankNiftyData.shortedExpiry.first());

		List<OptionModle> lst = data.getOptionModle();
		Strategy altPall = BNFStrategyUtils.buildStrategy("BNF", lst, strike, OptionType.PUT, currentExpiry, data.getSpot(), -20);
		Strategy altCall = BNFStrategyUtils.buildStrategy("BNF", lst, strike, OptionType.CALL, currentExpiry, data.getSpot(), -20);
		strategyModels.addAll(altCall.getStrategyModels());
		strategyModels.addAll(altPall.getStrategyModels());

		return strategyModels;
	}

	public Strategy StrategyATMStraddle(String strategyName) throws Exception {
		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();

		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);

		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName(strategyName);
		strategy.setDte(dte.toString());

		Strategy legPut2 = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall2 = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		strategy.getStrategyModels().addAll(legPut2.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall2.getStrategyModels());

		return strategy;

	}

	private Strategy Strategy3Straddle() throws Exception {
		// String currentExpiry =
		// BNFStrategyUtils.getExpiry(BankNiftyData.shortedExpiry);
		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);

		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double lowerStrike = atmStrike - 100;
		double uperStrike = atmStrike + 100;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		// int qnt = -40;
		Strategy legPut1 = BNFStrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall1 = BNFStrategyUtils.buildStrategy("BNF", lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy legPut2 = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall2 = BNFStrategyUtils.buildStrategy("BNF", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy legPut3 = BNFStrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall3 = BNFStrategyUtils.buildStrategy("BNF", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		strategy.getStrategyModels().addAll(legPut1.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall1.getStrategyModels());

		strategy.getStrategyModels().addAll(legPut2.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall2.getStrategyModels());

		strategy.getStrategyModels().addAll(legPut3.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall3.getStrategyModels());

		return strategy;

	}

	private Strategy buildStrategy300PointAwaystrangle_protected() throws Exception {

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

	private Strategy buildStrategy300PointAwaystrangle() throws Exception {

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);

		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double putStrike = atmStrike - 300;
		double callStrike = atmStrike + 300;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy putleg = BNFStrategyUtils.buildStrategy("BNF", lst, putStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -(lot * 3));

		Strategy callLeg = BNFStrategyUtils.buildStrategy("BNF", lst, callStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -(lot * 3));
		strategy.getStrategyModels().addAll(putleg.getStrategyModels());
		strategy.getStrategyModels().addAll(callLeg.getStrategyModels());

		return strategy;
	}

	private Strategy buildStrategyDoubleCalender() throws Exception {

		SortedSet<String> allexpiries = bnfConsumeWebService.getAllExpiry();

		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);
		String nextExpiry = BNFStrategyUtils.getNextExpiry(allexpiries);
		// getNextExpiry
		Long dte = DateUtils.getDte(currentExpiry, "ddMMMyyyy");
		// OptionModles optionModles =
		// BankNiftyData.bnOptionData.get(currentExpiry);
		OptionModles optionModles = bnfConsumeWebService.getOptionModel(currentExpiry);

		List<OptionModle> lst = optionModles.getOptionModle();

		// OptionModles optionModles2 =
		// BankNiftyData.bnOptionData.get(nextExpiry);
		OptionModles optionModles2 = bnfConsumeWebService.getOptionModel(nextExpiry);
		List<OptionModle> lst2 = optionModles2.getOptionModle();

		double atmStrike = BNFStrategyUtils.getATMStrike(optionModles, 50);

		double peLeg = atmStrike - 200;
		double ceLeg = atmStrike + 200;

		Strategy strategy = new Strategy(UnderLying.BANKNIFTY);
		strategy.setStrategyName("BNF");
		strategy.setDte(dte.toString());

		Strategy legPeBuy = BNFStrategyUtils.buildStrategy("BNF", lst2, peLeg, OptionType.PUT, nextExpiry, optionModles.getSpot(), lot);
		Strategy legPeSell = BNFStrategyUtils.buildStrategy("BNF", lst, peLeg, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);

		Strategy legCeSell = BNFStrategyUtils.buildStrategy("BNF", lst, ceLeg, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCeBuy = BNFStrategyUtils.buildStrategy("BNF", lst2, ceLeg, OptionType.CALL, nextExpiry, optionModles.getSpot(), lot);

		// order is imp to visualization
		strategy.getStrategyModels().addAll(legPeBuy.getStrategyModels());
		strategy.getStrategyModels().addAll(legPeSell.getStrategyModels());
		strategy.getStrategyModels().addAll(legCeSell.getStrategyModels());
		strategy.getStrategyModels().addAll(legCeBuy.getStrategyModels());

		return strategy;
	}

}