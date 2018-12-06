package org.stocksrin.test;

import java.util.List;

public class Module {

	private long id;
	private List<Module> subModules;

	public Module(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Module> getSubModules() {
		return subModules;
	}

	public void setSubModules(List<Module> subModules) {
		this.subModules = subModules;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", subModules=" + subModules + "]";
	}

	

}
