package org.stocksrin.strategy.builders.nifty;

import java.util.Date;
import java.util.SortedSet;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.strategy.builders.bnf.BNFStrategyBuilder;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class IntraDayBuilderTaskOld extends TimerTask {

	private static final String EXPIRY = "Expiry";

	@Autowired(required = true)
	private NiftyStrategyBuilder niftystrategyBuilder;

	@Autowired(required = true)
	private BNFStrategyBuilder bnfStrategyBuilder;

	@Autowired
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired
	private BNFConsumeWebService bnfConsumeWebService;

	@Override
	public void run() {

		String lastupdatedtime = null;
		try {
			SortedSet<String> allExpiry = niftyConsumeWebService.getAllExpiry();
			String currentExpiry = null;
			currentExpiry = allExpiry.first();
			lastupdatedtime = niftyConsumeWebService.getLastDataUpdated(currentExpiry);
			lastupdatedtime = parseDate(lastupdatedtime);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			niftystrategyBuilder.expiryDayCaladner(lastupdatedtime + "~" + EXPIRY + "~NiftyCalender6", TradeHoldingTime.INTRADAY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			bnfStrategyBuilder.expiryDayCaladner(lastupdatedtime + "~" + EXPIRY + "~BNFCalender6", TradeHoldingTime.INTRADAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String parseDate(String lastupdatedtime) throws Exception {
		Date d = DateUtils.stringToDate(lastupdatedtime, "MMM dd, yyyy HH:mm:ss");
		return DateUtils.dateToString(d, "dd~MMM~HH~mm");
	}
}
