package org.stocksrin.notifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.email.SendEmail;

public class OINotification {

	private static final Logger log = LoggerFactory.getLogger(OINotification.class);
	public static Map<Double, List<Integer>> callOILimit = new HashMap<>();
	public static Map<Double, List<Integer>> putOILimit = new HashMap<>();
	private static Integer oiincrement = 400000;

	public static void OINotificationTask() {

		String currentExpiry = NiftyData.shortedExpiry.first();
		OptionModles optionModles = NiftyData.optionData.get(currentExpiry);
		List<OptionModle> lst = optionModles.getOptionModle();
		oiTest(lst, OptionType.CALL, callOILimit);
		oiTest(lst, OptionType.PUT, putOILimit);

	}

	private static void oiTest(List<OptionModle> lst, OptionType optionType, Map<Double, List<Integer>> oiLimits) {
		for (OptionModle optionModle : lst) {

			Double strike = optionModle.getStrike_price();
			Integer changeInOI = null;
			if (OptionType.CALL.equals(optionType)) {
				changeInOI = optionModle.getC_change_oi();
			} else {
				changeInOI = optionModle.getP_change_oi();
			}

			if (changeInOI != null) {

				if (!oiLimits.containsKey(strike)) {
					if (changeInOI > optionModle.getC_oiLimits()) {
						List<Integer> oi = new ArrayList<>();
						oi.add(changeInOI);
						oiLimits.put(strike, oi);
					}

				} else {
					// strike is already there
					List<Integer> changeList = oiLimits.get(strike);
					Integer lastItem = changeList.get(changeList.size() - 1);
					// System.out.println("strike " + strike + ", diff " + Math.abs(changeInOI -
					// lastItem));
					if (Math.abs(changeInOI - lastItem) > oiincrement) {
						log.info("New Entry at Strike " + strike);
						changeList.add(changeInOI);
						mailString(strike, optionType, changeInOI, changeList);
					}

				}
			}

		}
		System.out.println();
	}

	private static String mailString(Double Strike, OptionType optionType, Integer oiChange, List<Integer> lst) {
		StringBuilder result = new StringBuilder();
		double totalOI = (double) oiChange / 100000;
		double roundOff = (double) Math.round(totalOI * 100) / 100;
		result.append(Strike.toString() + ":" + optionType + "->" + roundOff + " Lacks");
		// System.out.println("Subject : " + result);
		String body = optionType + ":" + Strike + "->" + lst;
		// System.out.println("body : " + body);
		SendEmail.sentMail("NF OI Alert [" + result + "]", body, "LiveData-Collector");
		return result.toString();

	}
}
