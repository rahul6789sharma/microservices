package org.stocksrin.strategies.common.strategies.usdinr;

import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.email.SendEmail;
import org.stocksrin.restclient.USDINRConsumeWebService;
import org.stocksrin.strategies.common.utils.BNFStrategyUtils;
import org.stocksrin.strategies.common.utils.StrategyUtils;

@Controller
public class USDINRStrategies {

	@Autowired
	private USDINRConsumeWebService uSDINRConsumeWebService;

	private final int lot = 5;

	public String BuildStrategy3Straddle(String fileName, String dir) {
		String file = dir + fileName + ".csv";
		try {

			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = this.Strategy3Straddle();
				CommonUtils.createStrategyFile(strategy, dir, fileName);
			} else {
				SendEmail.sentMail("Intra DayFile strategy already exist " + file, "", "Strategies-BUilder");
			}

		} catch (Exception e) {
			e.printStackTrace();
			SendEmail.sentMail("Critical Error in startegy file " + file, "", "Strategies-BUilder");
		}
		return file;
	}

	private Strategy Strategy3Straddle() throws Exception {

		SortedSet<String> allexpiries = uSDINRConsumeWebService.getAllExpiry();
		String currentExpiry = BNFStrategyUtils.getExpiry(allexpiries);

		OptionModles optionModles = uSDINRConsumeWebService.getOptionModel(currentExpiry);

		double atmStrike = StrategyUtils.getATMStrike(optionModles, 0.25);

		double lowerStrike = atmStrike - 0.25;
		double uperStrike = atmStrike + 0.25;

		List<OptionModle> lst = optionModles.getOptionModle();
		Strategy strategy = new Strategy(UnderLying.USDINR);
		strategy.setStrategyName("USDINR");

		// int qnt = -40;

		Strategy legPut = BNFStrategyUtils.buildStrategy("USDINR", lst, lowerStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall = BNFStrategyUtils.buildStrategy("USDINR", lst, lowerStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy legPut2 = BNFStrategyUtils.buildStrategy("USDINR", lst, atmStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall2 = BNFStrategyUtils.buildStrategy("USDINR", lst, atmStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		Strategy legPut3 = BNFStrategyUtils.buildStrategy("USDINR", lst, uperStrike, OptionType.PUT, currentExpiry, optionModles.getSpot(), -lot);
		Strategy legCall3 = BNFStrategyUtils.buildStrategy("USDINR", lst, uperStrike, OptionType.CALL, currentExpiry, optionModles.getSpot(), -lot);

		strategy.getStrategyModels().addAll(legPut.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall.getStrategyModels());

		strategy.getStrategyModels().addAll(legPut2.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall2.getStrategyModels());

		strategy.getStrategyModels().addAll(legPut3.getStrategyModels());
		strategy.getStrategyModels().addAll(legCall3.getStrategyModels());

		return strategy;

	}
}
