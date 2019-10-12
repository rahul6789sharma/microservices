package org.stocksrin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeType;

//@Component
public class MyRunner implements CommandLineRunner {
	@Autowired
	private StrategyDAOService strategyDAOService;

	/*
	 * @Autowired private StrategyRepository strategyRepository;
	 * 
	 * @Autowired private TradeRepository tradeRepository;
	 */

	@Override
	public void run(String... args) throws Exception {
		StrategyEntity strategyEntity = new StrategyEntity();
		strategyEntity.setName("NIFTY");
		List<TradeEntity> trades = new ArrayList<>();

		TradeEntity tradeEntity = new TradeEntity();
		tradeEntity.setDataUpdatedAt("3pm");
		tradeEntity.setTradeType(TradeType.EQUITY);
		tradeEntity.setStrategyEntity(strategyEntity);

		trades.add(tradeEntity);

		TradeEntity tradeEntity2 = new TradeEntity();
		tradeEntity2.setStrategyEntity(strategyEntity);
		tradeEntity2.setDataUpdatedAt("3pm");
		tradeEntity2.setTradeType(TradeType.OPTION);
		trades.add(tradeEntity2);

		strategyEntity.setTrades(trades);
		strategyDAOService.save(strategyEntity);
		List<StrategyEntity> lst = strategyDAOService.findAll();
		lst.forEach((i) -> System.out.println(i.getTrades()));

	}

}
