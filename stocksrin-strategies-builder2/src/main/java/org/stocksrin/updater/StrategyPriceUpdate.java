package org.stocksrin.updater;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.restclient.BNFConsumeWebService;
import org.stocksrin.restclient.NiftyConsumeWebService;
import org.stocksrin.restclient.RestService;
import org.stocksrin.strategy.db.dao.service.StrategyDAOService;
import org.stocksrin.strategy.db.dao.service.TradeDAOService;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;
import org.stocksrin.strategy.db.model.TradeType;

@Controller
public class StrategyPriceUpdate extends TimerTask {

	private static final Logger log = LoggerFactory.getLogger(StrategyPriceUpdate.class);

	private long timeInteval = 60000;
	@Autowired(required = true)
	private NiftyConsumeWebService niftyConsumeWebService;

	@Autowired(required = true)
	private BNFConsumeWebService bnfConsumeWebService;

	@Autowired
	private StrategyDAOService strategyDAOService;

	@Autowired
	private TradeDAOService tradeDAOService;

	@Override
	public void run() {

		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment

			while (CommonUtils.getEveningTime()) {

				try {
					List<TradeEntity> trades = tradeDAOService.findAll();
					boolean status = updateTrades(trades);
					// List<TradeEntity> updatedTrades = tradeDAOService.findAll();
					// if all trades are updted succesffuly
					if (status) {
						updatedStrategy(trades);
					}

					Thread.sleep(timeInteval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private boolean updateTrades(List<TradeEntity> tradeEntitys) {

		boolean status = true;
		// overnight position needs not be updated today

		for (TradeEntity tradeEntity : tradeEntitys) {
			try {
				if (tradeEntity.isStatus()) {
					System.out.println("Updating " + tradeEntity);
					System.out.println("Updating " + tradeEntity.getSymbole());
					RestService restService = null;
					if (tradeEntity.getSymbole().equalsIgnoreCase("Nifty")) {
						restService = niftyConsumeWebService;
					} else if (tradeEntity.getSymbole().equalsIgnoreCase("BankNifty") || tradeEntity.getSymbole().equalsIgnoreCase("BNF")) {
						restService = bnfConsumeWebService;
					}
					String expiry = tradeEntity.getDerivativeTrade().getExpiry();
					if (tradeEntity.getTradeType().equals(TradeType.OPTION)) {
						// OptionType optionType

						Double ltp = restService.getOptionLtp(expiry, tradeEntity.getDerivativeTrade().getStrike(), tradeEntity.getDerivativeTrade().getOptionType());
						// TODO
						tradeEntity.setLtp(ltp);

					} else if (tradeEntity.getTradeType().equals(TradeType.FUTURE)) {

						Double futureLTP = restService.getFuturePrice();
						tradeEntity.setLtp(futureLTP);
					}

					String dataUpdatedAt = restService.getLastDataUpdated(expiry);
					Double spotPrice = restService.getSpotPrice();

					tradeEntity.getDerivativeTrade().setUnderlyingltp(spotPrice);
					tradeEntity.setDataUpdatedAt(dataUpdatedAt);
					double totalPL = (tradeEntity.getQuantity() * (tradeEntity.getLtp() - tradeEntity.getTradedPrice()));
					tradeEntity.setTotalPL((int) totalPL);
					tradeDAOService.save(tradeEntity);
				}
			} catch (Exception e) {
				e.printStackTrace();
				status = false;
				break;
			}
		}

		return status;
	}

	private void updatedStrategy(List<TradeEntity> updatedTrades) {
		List<StrategyEntity> lst = strategyDAOService.findAll();
		for (StrategyEntity strategyEntity : lst) {
			double spot = 0.0;
			String lastUpdated = null;
			double totalPnl = 0.0;
			for (TradeEntity tradeEntity : updatedTrades) {

				if (strategyEntity.getName().equals(tradeEntity.getStrategyEntity().getName())) {
					totalPnl = totalPnl + tradeEntity.getTotalPL();
					spot = tradeEntity.getDerivativeTrade().getUnderlyingltp();
					lastUpdated = tradeEntity.getDataUpdatedAt();
				}
			}

			if (lastUpdated != null && spot != 0.0) {
				strategyEntity.setLtp(spot);
				strategyEntity.setTotalPL(totalPnl);
				updateMaxMINPNL(strategyEntity, totalPnl, spot, lastUpdated);
				strategyDAOService.save(strategyEntity);
			}
		}
	}

	private void updateMaxMINPNL(StrategyEntity strategy, Double totalPL, double spot, String lastUpdated) {
		log.info("---------------------- finding max min start--------------------" + strategy.getName());
		try {
			if (strategy.getTotalPLMin() > totalPL) {
				log.info("********** new MIN ********");
				log.info("existing min " + strategy.getTotalPLMin());
				log.info("totalPL " + totalPL);
				log.info("spot " + spot);
				log.info("lastUpdated " + lastUpdated);
				log.info("lastUpdated " + strategy);
				strategy.setTotalPLMin(totalPL);
				strategy.setTotalPLMinSpot(spot);
				strategy.setTotalPLMinTime(lastUpdated);
			}

			if (strategy.getTotalPLMax() < totalPL) {
				log.info("********** new Max ********");
				log.info("existing max " + strategy.getTotalPLMax());
				log.info("totalPL " + totalPL);
				log.info("spot " + spot);
				log.info("lastUpdated " + lastUpdated);
				log.info("lastUpdated " + strategy);
				strategy.setTotalPLMax(totalPL);
				strategy.setTotalPLMaxSpot(spot);
				strategy.setTotalPLMaxTime(lastUpdated);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("---------------------- finding max min end--------------------------------------");
	}

}