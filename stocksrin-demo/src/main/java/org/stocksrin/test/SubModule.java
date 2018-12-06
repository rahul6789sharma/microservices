package org.stocksrin.test;

public class SubModule {

	int id;
	int modID;
	int subModID;

	public SubModule(int id, int modID, int subModID) {
		super();
		this.id = id;
		this.modID = modID;
		this.subModID = subModID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getModID() {
		return modID;
	}

	public void setModID(int modID) {
		this.modID = modID;
	}

	public int getSubModID() {
		return subModID;
	}

	public void setSubModID(int subModID) {
		this.subModID = subModID;
	}

	@Override
	public String toString() {
		return " (id=" + id + ", modID=" + modID + ", subModID=" + subModID + ")";
	}

}
