package org.stocksrin.strategy.builders.task;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.strategy.builders.nifty.MixBuilder;
import org.stocksrin.strategy.builders.nifty.NiftyIntraDayStrategyBuilder;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class IntraDayBuilderTask extends TimerTask {

	@Autowired
	private NiftyIntraDayStrategyBuilder niftyIntraDayStrategyBuilder;

	@Autowired
	private MixBuilder mixBuilder;

	@Override
	public void run() {
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			//niftyIntraDayStrategyBuilder.ironFlyNifty("NiftyIronFly150", TradeHoldingTime.INTRADAY, 150);
			//niftyIntraDayStrategyBuilder.ironFlyNifty("NiftyIronFly200", TradeHoldingTime.INTRADAY, 200);
			mixBuilder.mixStrangle("MxStrangel", TradeHoldingTime.INTRADAY, 100, 300);
		}

	}
}
