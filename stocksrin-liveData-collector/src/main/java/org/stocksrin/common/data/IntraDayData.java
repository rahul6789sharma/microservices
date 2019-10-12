package org.stocksrin.common.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.stocksrin.common.model.intraday.IntraDayOptionModles;

public class IntraDayData {

	// 5 min delay data
	public static Map<Integer, IntraDayOptionModles> niftyOptionDataIntraDay5Min = new ConcurrentHashMap<>();

	// 10 min delay data
	public static Map<Integer, IntraDayOptionModles> niftyOptionDataIntraDay10Min = new ConcurrentHashMap<>();

	// 15 min delay data
	public static Map<Integer, IntraDayOptionModles> niftyOptionDataIntraDay15Min = new ConcurrentHashMap<>();

	
	// 5 min delay data
	public static Map<Integer, IntraDayOptionModles> bnfOptionDataIntraDay5Min = new ConcurrentHashMap<>();

	// 10 min delay data
	public static Map<Integer, IntraDayOptionModles> bnfOptionDataIntraDay10Min = new ConcurrentHashMap<>();

	// 15 min delay data
	public static Map<Integer, IntraDayOptionModles> bnfOptionDataIntraDay15Min = new ConcurrentHashMap<>();
}
