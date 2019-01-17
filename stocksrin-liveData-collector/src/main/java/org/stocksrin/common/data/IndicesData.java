package org.stocksrin.common.data;

import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.model.NSEIndice;

public class IndicesData {

	private static List<NSEIndice> data = new ArrayList<>();

	public static double getVIX() {
		double price = 00;
		for (NSEIndice nseIndice : data) {
			if (nseIndice.getName().equalsIgnoreCase("INDIA VIX")) {
				price = Double.parseDouble(nseIndice.getLastPrice());
				break;
			}
		}
		return price;
	}

	public static List<NSEIndice> getData() {
		return data;
	}

	public static void setData(List<NSEIndice> data) {
		IndicesData.data = data;
	}

}
