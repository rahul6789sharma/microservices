package org.stocksrin.strategies.common.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

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
public class DynamicStrategy {

	private final Logger log = LoggerFactory.getLogger(DynamicStrategy.class);

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	public String straddleNiftyDynamicHighestTimevalue(String fileName, String dir) {

		String file = dir + fileName + ".csv";
		try {
			boolean status = FileUtils.isTodayFileExist(file);
			if (!status) {
				Strategy strategy = straddleNiftyDynamicHighestTimevalue();
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

	private Strategy straddleNiftyDynamicHighestTimevalue() throws Exception {

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

		OptionModles optionModles = niftyConsumeWebService.getOptionModel(currentExpiry);

		if (optionModles == null) {
			return null;
		}
		Double spot = optionModles.getSpot();
		double atmStrike = optionModles.getAtmStrike();

		double callsideSupport = 0.0;
		double putsideSupport = 0.0;
		List<OptionModle> om = optionModles.getOptionModle();
		for (OptionModle optionModle : om) {
			// call
			if (optionModle.getStrike_price() <= atmStrike && optionModle.getC_ltp() != null) {

				double internsicValue = spot - optionModle.getStrike_price();
				double timeValue = optionModle.getC_ltp() - internsicValue;
				if (timeValue > (internsicValue - 10)) {
	
					callsideSupport = optionModle.getStrike_price();
				}

			}
			if (optionModle.getStrike_price() >= atmStrike && optionModle.getP_ltp() != null) {
		
				double internsicValue = optionModle.getStrike_price() - spot;
				double timeValue = optionModle.getP_ltp() - internsicValue;
				if (timeValue > (internsicValue - 10)) {

					putsideSupport = optionModle.getStrike_price();
				}
			}
		}
		final double callsideSupport1 = callsideSupport;
		final double putsideSupport1 = putsideSupport;
		List<OptionModle> result = om.stream().filter(i -> i.getStrike_price() >= callsideSupport1 && i.getStrike_price() <= putsideSupport1).collect(Collectors.toList());

		Strategy strategy = new Strategy(UnderLyingInstrument.NIFTY);
		strategy.setStrategyName("NIFTY");
		strategy.setDte(dte.toString());

		int qnt = -75;
		for (OptionModle optionModle : result) {

			strategy.getStrategyModels()
					.addAll(StrategyUtils.buildStrategy("NF", result, optionModle.getStrike_price(), OptionType.CALL, currentExpiry, optionModles.getSpot(), qnt).getStrategyModels());
			strategy.getStrategyModels()
					.addAll(StrategyUtils.buildStrategy("NF", result, optionModle.getStrike_price(), OptionType.PUT, currentExpiry, optionModles.getSpot(), qnt).getStrategyModels());

		}

		return strategy;
	}
}
