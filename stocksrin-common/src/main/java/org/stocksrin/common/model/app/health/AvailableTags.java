package org.stocksrin.common.model.app.health;

public class AvailableTags {

	private String[] values;

	private String tag;

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "ClassPojo [values = " + values + ", tag = " + tag + "]";
	}
}
