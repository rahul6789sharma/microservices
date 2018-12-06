package org.stocksrin.test;

import java.util.ArrayList;
import java.util.List;

public class ModuleVO {

	private int id;
	private String label;
	private List<DocMgmtVO> children;

	private List<ModuleVO> subModule = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<DocMgmtVO> getChildren() {
		return children;
	}

	public void setChildren(List<DocMgmtVO> children) {
		this.children = children;
	}

	public List<ModuleVO> getSubModule() {
		return subModule;
	}

	public void setSubModule(List<ModuleVO> subModule) {
		this.subModule = subModule;
	}

	@Override
	public String toString() {
		return "ModuleVO [id=" + id + ", label=" + label + ", children=" + children + ", subModule=" + subModule + "]";
	}

}
