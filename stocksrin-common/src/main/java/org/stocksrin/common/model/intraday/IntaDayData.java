package org.stocksrin.common.model.intraday;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IntaDayData {

	public static AllIntraDayData allIntraDayData;
	public static Map<Integer, AllIntraDayData> data = new ConcurrentHashMap<>();
}
