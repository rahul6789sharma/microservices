package org.stocksrin.common.model.app.health;

public class MemoryStatus {

	private String appName;
	private double max;
	private double min;
	private double current;
	private AppStatus appStatus;

	private int emailCount;

	public MemoryStatus(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public AppStatus getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(AppStatus appStatus) {
		this.appStatus = appStatus;
	}

	public int getEmailCount() {
		return emailCount;
	}

	public void setEmailCount(int emailCount) {
		this.emailCount = emailCount;
	}

	@Override
	public String toString() {
		return "MemoryStatus [appName=" + appName + ", max=" + max + ", min=" + min + ", current=" + current + ", appStatus=" + appStatus + ", emailCount=" + emailCount + "]";
	}

}
