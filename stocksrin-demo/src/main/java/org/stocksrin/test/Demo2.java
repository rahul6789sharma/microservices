package org.stocksrin.test;

import java.util.ArrayList;
import java.util.List;

public class Demo2 {

	// [(75,76,77,79), (75,79)]
	static List<List<Integer>> moduleReltion = new ArrayList<>();

/*	private static void makeRelation(List<List<Integer>> lst) {

		for (List<Integer> list : lst) {
			System.out.println(list);
			System.out.println("main :" + list.get(0));
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
				ModuleVO mod = getModule(list.get(i));
				List<ModuleVO> subModule= new ArrayList<>();
				for (int j = i; j < list.size(); j++) {
					System.out.println( "sub: " +list.get(j));
					ModuleVO sub = getModule(list.get(j));
					subModule.add(sub);
				}
				
				mod.setSubModule(subModule);
				System.out.println(mod);
				break;
				
				//
			}
		}
	}*/

	private static ModuleVO getModule(Integer moduleID) {
		ModuleVO moduleVO = new ModuleVO();
		moduleVO.setId(moduleID);
		return moduleVO;
	}

	public static void main(String[] args) {

		List<Integer> uniqueModules = new ArrayList<>();
		uniqueModules.add(75);
		// uniqueModules.add(76);
		// uniqueModules.add(77);
		// uniqueModules.add(78);

		List<SubModule> subModules = new ArrayList<>();

		SubModule subModules1 = new SubModule(1, 75, 76);
		SubModule subModules2 = new SubModule(2, 76, 77);
		SubModule subModules3 = new SubModule(3, 77, 78);
		SubModule subModules4 = new SubModule(4, 75, 79);

		subModules.add(subModules1);
		subModules.add(subModules2);
		subModules.add(subModules3);
		subModules.add(subModules4);

		//System.out.println(childs);
		getModulesChildList(subModules, 75);
	}

	private static List<List<SubModule>> getModulesChildList(List<SubModule> subModuleList, Integer id) {
		List<List<SubModule>> result = new ArrayList<>();
		List<SubModule> mainModules = new ArrayList<>();
		List<SubModule> subModules = new ArrayList<>();
		for (SubModule item : subModuleList) {

			if (item.getModID() == id) {
				mainModules.add(item);
			} else {
				subModules.add(item);
			}

		}
		// System.out.println("mainModules : " + mainModules);

		for (SubModule main : mainModules) {

			List<SubModule> m = new ArrayList<>();
			m.add(main);
			m.addAll(subModules);
			result.add(m);

		}

		// System.out.println("subModules : " + subModules);
		// System.out.println("result " + result);

		for (List<SubModule> item : result) {
			List<Integer> childs = new ArrayList<>();
			childs.add(id);
			getChild(item, id, childs);
			// System.out.println("childs " + childs);
			moduleReltion.add(childs);
		}
		System.out.println("moduleReltion" + moduleReltion);
		//makeRelation(moduleReltion);
		return result;
	}

	// static List<Integer> result = new ArrayList<>();
	private static Integer getChild(List<SubModule> subModules, Integer parent, List<Integer> childs) {

		for (SubModule subModule : subModules) {
			if (subModule.getModID() == parent) {
				// System.out.println(parent + " ->" + subModule.getSubModID());
				childs.add(subModule.getSubModID());
				getChild(subModules, subModule.getSubModID(), childs);
				return subModule.getSubModID();
			}
		}

		return null;

	}
}

