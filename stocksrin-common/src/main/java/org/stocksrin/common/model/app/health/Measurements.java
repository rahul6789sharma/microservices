package org.stocksrin.common.model.app.health;

public class Measurements {

	private String statistic;

	private String value;

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ClassPojo [statistic = " + statistic + ", value = " + value + "]";
	}

}
