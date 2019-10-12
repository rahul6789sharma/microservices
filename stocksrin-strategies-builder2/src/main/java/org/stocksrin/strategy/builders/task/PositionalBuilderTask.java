package org.stocksrin.strategy.builders.task;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.strategy.builders.nifty.NiftyOverNightStrategyBuilder;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class PositionalBuilderTask extends TimerTask {

	@Autowired(required = true)
	private NiftyOverNightStrategyBuilder niftyOverNightStrategyBuilder;

	@Override
	public void run() {
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			
/*			niftyOverNightStrategyBuilder.ironFlyNifty("NiftyIronFly150", TradeHoldingTime.OVERNIGHT, 150);
			niftyOverNightStrategyBuilder.ironFlyNifty("NiftyIronFly200", TradeHoldingTime.OVERNIGHT, 200);
			niftyOverNightStrategyBuilder.straddle3Nifty("NiftyStraddle3", TradeHoldingTime.OVERNIGHT);
			niftyOverNightStrategyBuilder.straddle3NiftySafe("NiftyStraddle3Safe", TradeHoldingTime.OVERNIGHT);
			niftyOverNightStrategyBuilder.strangle("Strangle100", TradeHoldingTime.OVERNIGHT, 100);
			niftyOverNightStrategyBuilder.strangleSafe("Strangle100Safe50", TradeHoldingTime.OVERNIGHT, 100, 50);
			niftyOverNightStrategyBuilder.strangleSafe("Strangle100Safe100", TradeHoldingTime.OVERNIGHT, 100, 100);*/
		}

	}

}
