package org.stocksrin.common.utils.options;

import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.utils.DateUtils;

public class USDINREpiry {

	public static List<String> monthlyExpiry = new ArrayList<>();
	static {
		
		monthlyExpiry.add("4JAN2018");
		monthlyExpiry.add("11JAN2018");
		monthlyExpiry.add("18JAN2018");
		monthlyExpiry.add("29JAN2018");
		monthlyExpiry.add("1FEB2018");
		

	}

	public static String getcurrentMOnthlyExpiry() {
		String currentMOnth = DateUtils.getCurrentMonth();
		System.out.println(currentMOnth);
		String expiry = null;
		int i=0;
		for (String string : monthlyExpiry) {
			System.out.println(string);
			if(string.contains(currentMOnth.toUpperCase())) {
				//System.out.println(string);
				i++;
			}
		}
		System.out.println(i);
		System.out.println(monthlyExpiry.get(i-1));
		return expiry;
	}

	public static void main(String[] args) {
		System.out.println(getcurrentMOnthlyExpiry());
	}
}
