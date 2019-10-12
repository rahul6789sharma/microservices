package org.stocksrin.result.writer;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.strategy.db.model.StrategyEntity;
import org.stocksrin.strategy.db.model.TradeEntity;

public class StrategyCSVUtils {

	private static String line = "--------------------------------------------------------------------------------------------";

	public static void main(String[] args) throws Exception {
		StrategyEntity s = FileUtils.fromJsonStrategy2("C:\\Users\\sharmarah\\stocksRin_CONF2\\StrategiesDB\\INTRADAY\\Nifty\\NiftyIronFly150_INTRADAY_13-Sep-2019-FRI.json");
		print(s);
	}

	public static String print(StrategyEntity strategyEntity) throws Exception {
		String todayDate = DateUtils.dateToString(new Date(), "ddMMMyyyy");
		String todayDay = DateUtils.getDayFromDate(todayDate, "ddMMMyyyy");

		TradeEntity trades = strategyEntity.getTrades().get(0);
		List<TradeEntity> tradess = strategyEntity.getTrades();
		StringBuilder result = new StringBuilder();
		Long dte = null;
		if (trades.getDerivativeTrade() != null) {
			dte = trades.getDerivativeTrade().getDte();
		}
		result.append(strategyEntity.getSymbole() + ", DTE: " + dte);

		result.append("\n");
		result.append(line);
		result.append("\n");

		result.append("Traded Date: " + strategyEntity.getTradedDate() + " (" + strategyEntity.getTradedDay() + "), Traded Price: " + strategyEntity.getTradedPrice());
		result.append("\n");
		result.append("Status   At: " + todayDate + " (" + todayDay + "), Current Price: " + "       " + " [" + "   " + "]  Diff from Trade");
		result.append("\n");
		result.append(line);
		result.append("\n");
		result.append("TradeType   Expiry     Strike   Qty  TradePrice   ltp    change   P&L   tradeIV" + "\n");
		result.append(line);
		result.append("\n");

		for (TradeEntity tradeEntity : tradess) {
			if (tradeEntity.getDerivativeTrade() != null) {
				result.append(tradeEntity.getTradeType() + "     ");
				result.append(tradeEntity.getDerivativeTrade().getExpiry() + "   ");
				String strike = (tradeEntity.getDerivativeTrade().getStrike().toString()).split("\\.")[0];
				result.append(strike + "   ");
				if (tradeEntity.getQuantity() > 0) {
					// positive
					result.append(" " + tradeEntity.getQuantity() + "     ");
				} else {
					result.append(tradeEntity.getQuantity() + "     ");
				}
				result.append(df.format(tradeEntity.getTradedPrice()) + "    ");
				result.append(foramteTradedPrice(df.format(tradeEntity.getLtp())) + "    ");
				double change = tradeEntity.getLtp() - tradeEntity.getTradedPrice();
				result.append(signFormate.format(change) + "    ");
				double pl = (tradeEntity.getLtp() - tradeEntity.getTradedPrice()) * tradeEntity.getQuantity();
				result.append(foramtePL(pl) + "    ");
				result.append(tradeEntity.getDerivativeTrade().getIv());
				result.append("\n");
			}
		}
		result.append(line);
		result.append("\n");
		result.append("Total PNL: [" + strategyEntity.getTotalPL() + "]    ");
		result.append("At Spot: [" + strategyEntity.getLtp() + "] Diff " + "[ " + df2.format(strategyEntity.getTradedPrice() - strategyEntity.getLtp()) + "  ]");
		result.append("\n");
		result.append("Min   PNL: [" + strategyEntity.getTotalPLMin() + "]    ");
		result.append("At Spot: [" + strategyEntity.getTotalPLMinSpot() + "] Diff " + "[ " + df2.format(strategyEntity.getTradedPrice() - strategyEntity.getTotalPLMinSpot()) + "  ] at :"
				+ strategyEntity.getTotalPLMinTime());
		result.append("\n");
		result.append("Max   PNL: [" + strategyEntity.getTotalPLMax() + "]    ");
		result.append("At Spot: [" + strategyEntity.getTotalPLMaxSpot() + "] Diff " + "[ " + df2.format(strategyEntity.getTradedPrice() - strategyEntity.getTotalPLMaxSpot()) + "  ] at :"
				+ strategyEntity.getTotalPLMaxTime());
		result.append("\n");
		result.append(line);
		result.append("\n");
		result.append("\n*********************************************************************************************************\n");
		result.append("\n");
		System.out.println(result);

		return result.toString();
	}

	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private static DecimalFormat df = new DecimalFormat("#00.00");
	private static DecimalFormat signFormate = new DecimalFormat("+#,##00;-#");

	private static String foramtePL(Double price) {
		DecimalFormat signFormate = new DecimalFormat("+#,##0000;-#");
		return signFormate.format(price);
	}

	private static String foramteTradedPrice(String price) {
		if (price.length() == 6) {
			return price;
		} else {
			return " " + price;
		}
	}

}
