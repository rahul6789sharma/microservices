package org.stocksrin.strategy.db.model;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Trade")
public class TradeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String symbole; // nifty,bnf,infy
	// private Instrument instrument;
	@Enumerated(EnumType.STRING)
	private TradeType tradeType;
	private int quantity;
	private double tradedPrice; // for option its option price

	@ManyToOne
	@JoinColumn
	private StrategyEntity strategyEntity;

	@Enumerated(EnumType.STRING)
	private TradeHoldingTime tradeHoldingTime;

	private String tradedTime;
	private String tradedDate;
	private String tradedDay;

	private String dataUpdatedAt;

	// underlyingPrice

	private double ltp;

	// define positive amount only
	// optional
	private int stopLoss;
	private int target;
	private int totalPL;

	private double min_price;
	private double max_price;
	private int minPNL;
	private int maxPNL;

	private boolean status = true;
	private String closeDate;
	private String closeTime;

	@Embedded
	private DerivativeTrade derivativeTrade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StrategyEntity getStrategyEntity() {
		return strategyEntity;
	}

	public void setStrategyEntity(StrategyEntity strategyEntity) {
		this.strategyEntity = strategyEntity;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public TradeHoldingTime getTradeHoldingTime() {
		return tradeHoldingTime;
	}

	public void setTradeHoldingTime(TradeHoldingTime tradeHoldingTime) {
		this.tradeHoldingTime = tradeHoldingTime;
	}

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public String getTradedTime() {
		return tradedTime;
	}

	public void setTradedTime(String tradedTime) {
		this.tradedTime = tradedTime;
	}

	public String getTradedDate() {
		return tradedDate;
	}

	public void setTradedDate(String tradedDate) {
		this.tradedDate = tradedDate;
	}

	public String getTradedDay() {
		return tradedDay;
	}

	public void setTradedDay(String tradedDay) {
		this.tradedDay = tradedDay;
	}

	public String getDataUpdatedAt() {
		return dataUpdatedAt;
	}

	public void setDataUpdatedAt(String dataUpdatedAt) {
		this.dataUpdatedAt = dataUpdatedAt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTradedPrice() {
		return tradedPrice;
	}

	public void setTradedPrice(double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}

	public double getLtp() {
		return ltp;
	}

	public void setLtp(double ltp) {
		this.ltp = ltp;
	}

	public int getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(int stopLoss) {
		this.stopLoss = stopLoss;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getTotalPL() {
		return totalPL;
	}

	public void setTotalPL(int totalPL) {
		this.totalPL = totalPL;
	}

	public double getMin_price() {
		return min_price;
	}

	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}

	public double getMax_price() {
		return max_price;
	}

	public void setMax_price(double max_price) {
		this.max_price = max_price;
	}

	public int getMinPNL() {
		return minPNL;
	}

	public void setMinPNL(int minPNL) {
		this.minPNL = minPNL;
	}

	public int getMaxPNL() {
		return maxPNL;
	}

	public void setMaxPNL(int maxPNL) {
		this.maxPNL = maxPNL;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public DerivativeTrade getDerivativeTrade() {
		return derivativeTrade;
	}

	public void setDerivativeTrade(DerivativeTrade derivativeTrade) {
		this.derivativeTrade = derivativeTrade;
	}

	@Override
	public String toString() {
		return "TradeEntity [id=" + id + ", strategyEntity=" + strategyEntity + ", tradeType=" + tradeType + ", tradeHoldingTime=" + tradeHoldingTime + ", symbole=" + symbole + ", tradedTime="
				+ tradedTime + ", tradedDate=" + tradedDate + ", tradedDay=" + tradedDay + ", dataUpdatedAt=" + dataUpdatedAt + ", quantity=" + quantity + ", tradedPrice=" + tradedPrice + ", ltp="
				+ ltp + ", stopLoss=" + stopLoss + ", target=" + target + ", totalPL=" + totalPL + ", min_price=" + min_price + ", max_price=" + max_price + ", minPNL=" + minPNL + ", maxPNL=" + maxPNL
				+ ", status=" + status + ", closeDate=" + closeDate + ", closeTime=" + closeTime + ", derivativeTrade=" + derivativeTrade + "]";
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field[] f = TradeEntity.class.getDeclaredFields();
		Field[] df = DerivativeTrade.class.getDeclaredFields();

		DerivativeTrade derivativeTrade = new DerivativeTrade();
		derivativeTrade.setOptionType(DerivativeType.CALL);
		derivativeTrade.setExpiry("12SEp");

		TradeEntity tradeEntity = new TradeEntity();
		tradeEntity.setId(1l);
		tradeEntity.setSymbole("Nifty");
		tradeEntity.setDerivativeTrade(derivativeTrade);
		StringBuilder header = new StringBuilder();
		StringBuilder value = new StringBuilder();
		for (Field field : f) {
			if (!(field.getName().equalsIgnoreCase("stopLoss") || field.getName().equalsIgnoreCase("target") || field.getName().equalsIgnoreCase("min_price")
					|| field.getName().equalsIgnoreCase("max_price") || field.getName().equalsIgnoreCase("minPNL") || field.getName().equalsIgnoreCase("maxPNL")
					|| field.getName().equalsIgnoreCase("status") || field.getName().equalsIgnoreCase("derivativeTrade") || field.getName().equalsIgnoreCase("closeDate")
					|| field.getName().equalsIgnoreCase("closeTime"))) {
				value.append(field.get(tradeEntity) + ", ");
				// System.out.println(field.getName());
				header.append(field.getName() + ", ");
			}
			if (field.getName().equalsIgnoreCase("derivativeTrade")) {
				DerivativeTrade dt = tradeEntity.getDerivativeTrade();

				for (Field item : df) {
					item.setAccessible(true);
					value.append(item.get(dt) + ", ");
					header.append(item.getName() + ", ");
				}
			}
		}

		System.out.println(header);
		System.out.println(value);
	}

	public static String csv(List<TradeEntity> tradeEntitys) throws IllegalArgumentException, IllegalAccessException {
		Field[] f = TradeEntity.class.getDeclaredFields();
		Field[] df = DerivativeTrade.class.getDeclaredFields();

		StringBuilder header = new StringBuilder();
		StringBuilder value = new StringBuilder();
		for (Field field : f) {
			if (!(field.getName().equalsIgnoreCase("stopLoss") || field.getName().equalsIgnoreCase("target") || field.getName().equalsIgnoreCase("min_price")
					|| field.getName().equalsIgnoreCase("max_price") || field.getName().equalsIgnoreCase("minPNL") || field.getName().equalsIgnoreCase("maxPNL")
					|| field.getName().equalsIgnoreCase("status") || field.getName().equalsIgnoreCase("derivativeTrade") || field.getName().equalsIgnoreCase("closeDate")
					|| field.getName().equalsIgnoreCase("closeTime"))) {

				header.append(field.getName() + ", ");
			}
			if (field.getName().equalsIgnoreCase("derivativeTrade")) {
				for (Field item : df) {
					header.append(item.getName() + ", ");
				}
			}
		}

		for (TradeEntity tradeEntity : tradeEntitys) {
			for (Field field : f) {
				if (!(field.getName().equalsIgnoreCase("stopLoss") || field.getName().equalsIgnoreCase("target") || field.getName().equalsIgnoreCase("min_price")
						|| field.getName().equalsIgnoreCase("max_price") || field.getName().equalsIgnoreCase("minPNL") || field.getName().equalsIgnoreCase("maxPNL")
						|| field.getName().equalsIgnoreCase("status") || field.getName().equalsIgnoreCase("derivativeTrade") || field.getName().equalsIgnoreCase("closeDate")
						|| field.getName().equalsIgnoreCase("closeTime"))) {

					value.append(field.get(tradeEntity) + ", ");
				}
				if (field.getName().equalsIgnoreCase("derivativeTrade")) {
					DerivativeTrade dt = tradeEntity.getDerivativeTrade();
					for (Field item : df) {
						item.setAccessible(true);
						value.append(item.get(dt) + ", ");
						header.append(item.getName() + ", ");
					}
				}
			}
			value.append("\n");
		}
		System.out.println(header);
		System.out.println(value);
		return header.append("\n").append(value).toString();
	}

}
