package org.stocksrin.strategy.db.dao.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stocksrin.strategy.db.model.StrategyEntity;

@Repository
public interface StrategyRepository extends JpaRepository<StrategyEntity, String> {

}
