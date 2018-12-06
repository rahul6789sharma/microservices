package org.stocksrin.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Demo {

	static Map<Integer, List<Integer>> map = new HashMap<>();

	static List<Module> modules = new ArrayList<>();

	public static void main(String[] args) {

		List<Integer> uniqueModules = new ArrayList<>();
		uniqueModules.add(75);
		//uniqueModules.add(76);
		//uniqueModules.add(77);
		//uniqueModules.add(78);

		List<SubModule> subModules = new ArrayList<>();
		SubModule subModules1 = new SubModule(1, 75, 76);
		SubModule subModules2 = new SubModule(2, 76, 77);
		SubModule subModules3 = new SubModule(3, 75, 78);

		subModules.add(subModules1);
		subModules.add(subModules2);
		subModules.add(subModules3);

		for (Integer integer : uniqueModules) {
			List<Integer> childs = new ArrayList<>();
			getChild(subModules, integer, childs);
			System.out.println("childs " + childs);
			map.put(integer,childs);	
		}
		
		System.out.println("-------------");
		
		System.out.println(map);
		System.out.println("-------------");
		// formResult(map, 75);
		// System.out.println(modules);

		

	}

	
	//static List<Integer> result = new ArrayList<>();
	private static Integer getChild(List<SubModule> subModules, Integer parent, List<Integer> childs) {
			
		for (SubModule subModule : subModules) {
			if (subModule.getModID() == parent) {
				System.out.println(parent + " ->" + subModule.getSubModID());
				childs.add(subModule.getSubModID());
				getChild(subModules, subModule.getSubModID(), childs);
				return subModule.getSubModID();
			}
		}
		
		return null;

	}

	private static void formResult(Map<Integer, Set<Integer>> result, Integer modID) {

		Module module = new Module(modID);

		Set<Integer> subModules = result.get(modID);
		List<Module> subModules1 = new ArrayList<>();
		if (!subModules.isEmpty()) {
			for (Integer integer : subModules) {
				Module subModule = new Module(integer);
				subModules1.add(subModule);
			}
		}
		module.setSubModules(subModules1);
		modules.add(module);
	}

	private static Set<Integer> getSubMoDules(List<SubModule> subModules, long modId) {
		Set<Integer> result = new HashSet<>();

		for (SubModule subModule : subModules) {
			if (subModule.getModID() == modId) {
				result.add(subModule.getSubModID());
			}
		}

		return result;
	}

}
