package org.stocksrin.common.model.app.health;

public class Memory {

	private Measurements[] measurements;

	private String baseUnit;

	private AvailableTags[] availableTags;

	private String description;

	private String name;

	public Measurements[] getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Measurements[] measurements) {
		this.measurements = measurements;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public AvailableTags[] getAvailableTags() {
		return availableTags;
	}

	public void setAvailableTags(AvailableTags[] availableTags) {
		this.availableTags = availableTags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ClassPojo [measurements = " + measurements + ", baseUnit = " + baseUnit + ", availableTags = " + availableTags + ", description = " + description + ", name = " + name + "]";
	}
}
