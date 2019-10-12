package org.stocksrin.serivce.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@RestController
@RequestMapping("/strategies")
public class StrategyRestController {

	@Autowired
	private StrategyDAOService strategyDAOService;

	@GetMapping("/intraday")
	public ResponseEntity<List<StrategyEntity>> getIntraday() {
		List<StrategyEntity> strategyEntitys = getStrategy(TradeHoldingTime.INTRADAY);
		return new ResponseEntity<List<StrategyEntity>>(strategyEntitys, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/overnight")
	public ResponseEntity<List<StrategyEntity>> getOvewrnight() {
		List<StrategyEntity> strategyEntitys = getStrategy(TradeHoldingTime.OVERNIGHT);
		return new ResponseEntity<List<StrategyEntity>>(strategyEntitys, new HttpHeaders(), HttpStatus.OK);
	}

	private List<StrategyEntity> getStrategy(TradeHoldingTime tradeHoldingTime) {
		List<StrategyEntity> strategyEntitys = strategyDAOService.getByHoldingTime(tradeHoldingTime);

		for (StrategyEntity strategyEntity : strategyEntitys) {
			List<TradeEntity> tradeEntitys = strategyEntity.getTrades();
			for (TradeEntity t : tradeEntitys) {
				t.setStrategyEntity(null);
			}
		}
		return strategyEntitys;
	}
}