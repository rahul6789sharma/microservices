package org.stocksrin.strategy.db.dao.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stocksrin.strategy.db.model.TradeEntity;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {

}
