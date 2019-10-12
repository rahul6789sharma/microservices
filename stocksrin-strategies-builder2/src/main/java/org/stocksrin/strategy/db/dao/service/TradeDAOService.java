package org.stocksrin.strategy.db.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stocksrin.strategy.db.model.TradeEntity;

@Repository
@Transactional
public class TradeDAOService {

	@Autowired
	private TradeRepository radeRepository;

	public void save(TradeEntity tradeEntity) {
		radeRepository.save(tradeEntity);
	}

	public List<TradeEntity> findAll() {
		return radeRepository.findAll();
	}
}
