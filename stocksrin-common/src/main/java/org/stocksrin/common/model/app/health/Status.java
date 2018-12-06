package org.stocksrin.common.model.app.health;

public class Status {

	String name;
	double value;
	AppStatus aapStatus;

	public Status(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public AppStatus getAapStatus() {
		return aapStatus;
	}

	public void setAapStatus(AppStatus aapStatus) {
		this.aapStatus = aapStatus;
	}

	@Override
	public String toString() {
		return "Status [name=" + name + ", value=" + value + ", aapStatus=" + aapStatus + "]";
	}

}
