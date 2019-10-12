package org.stocksrin.strategy.db.dao.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeHoldingTime;

@Repository
@Transactional
public class StrategyDAOService {

	@Autowired
	private StrategyRepository strategyRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public StrategyEntity save(StrategyEntity strategyEntity) {
		return strategyRepository.save(strategyEntity);
	}

	public void delete(StrategyEntity strategyEntity) {
		strategyRepository.delete(strategyEntity);
	}

	public List<StrategyEntity> findAll() {
		return strategyRepository.findAll();
	}

	public List<StrategyEntity> getByHoldingTime(TradeHoldingTime tradeHoldingTime) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StrategyEntity> criteriaQuery = cb.createQuery(StrategyEntity.class);
		Root<StrategyEntity> itemRoot = criteriaQuery.from(StrategyEntity.class);
		Predicate predicateForBlueColor = cb.equal(itemRoot.get("strategyHoldingTime"), tradeHoldingTime);
		criteriaQuery.where(predicateForBlueColor);
		return entityManager.createQuery(criteriaQuery).getResultList();

	}

	public StrategyEntity get(String strategyName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StrategyEntity> criteriaQuery = cb.createQuery(StrategyEntity.class);
		Root<StrategyEntity> itemRoot = criteriaQuery.from(StrategyEntity.class);
		Predicate predicateForBlueColor = cb.equal(itemRoot.get("name"), strategyName);
		criteriaQuery.where(predicateForBlueColor);
		List<StrategyEntity> items = entityManager.createQuery(criteriaQuery).getResultList();
		if (items != null && !items.isEmpty()) {
			return items.get(0);
		} else {
			return null;
		}

	}
}
