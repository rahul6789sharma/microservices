package org.stocksrin.ai;

import java.util.HashMap;
import java.util.Map;

public class NIftyAIData {

	// due to josn parsing issue
	public static Map<String, Map<String, Straddle>> straddleData = new HashMap<>();

	public static void add(Map<String, Map<String, Straddle>> data) {
		straddleData = data;
	}

	public static Map<String, Straddle> getData(String dte) {
		return straddleData.get(dte);
	}
}
