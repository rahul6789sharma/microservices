package org.stocksrin.strategy.builders.task;

import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Controller
public class DBCleanTask extends TimerTask {

	@Autowired
	private StrategyDAOService strategyDAOService;

	@Override
	public void run() {
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			List<StrategyEntity> strategyEntitys = strategyDAOService.findAll();
			for (StrategyEntity strategyEntity : strategyEntitys) {
				if (strategyEntity.getStrategyHoldingTime().equals(TradeHoldingTime.OVERNIGHT)) {
					// delete all overnigh strategy that does not creted today
					if (!DateUtils.getTodayDate().equalsIgnoreCase(strategyEntity.getTradedDate())) {
						strategyDAOService.delete(strategyEntity);
					}
				}
			}
		}
	}
}