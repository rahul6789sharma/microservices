package org.stocksrin.rules.nifty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;

public class IVRule {

	static double openIV = 0.0;
	static double lastRecordedIV = 0.0;
	static int i = 0;
	// fixing atm in morning
	static double atmStrike;

	public static void iv(OptionModles optionModles, double iv) {
		System.out.println("Spot: " + optionModles.getSpot());
		System.out.println("ATM: " + atmStrike);
		System.out.println("IV: " + optionModles.getIv());
		double ivDiff = 1.0;

		if (i == 0) {
			// fixing atm in morning
			atmStrike = optionModles.getAtmStrike();
			openIV = iv;
			lastRecordedIV = iv;
			i++;
		}

		double value = Math.abs(iv - openIV);
		System.out.println("diff iv : " + value);
		if (value >= ivDiff) {
			System.out.println("trigger alerts");
		}
		oiTest(optionModles, atmStrike);
		OptionPremiumDecayCalculation.premiumDecay(optionModles, atmStrike);
	}

	private static void oiTest(OptionModles optionModles, double atmStrike) {
		List<OptionModle> lst = optionModles.getOptionModle();
		List<OptionModle> ceOTMoi = new ArrayList<>();
		List<OptionModle> peOTMoi = new ArrayList<>();

		for (OptionModle optionModle : lst) {
			// taking call otm options
			if (atmStrike <= optionModle.getStrike_price() && optionModle.getC_change_oi() != null) {
				ceOTMoi.add(optionModle);
			}

			// taking out otm options
			if (atmStrike >= optionModle.getStrike_price() && optionModle.getP_change_oi() != null) {
				peOTMoi.add(optionModle);
			}

		}

		Collections.sort(ceOTMoi, new HigestCEChangeOIComparator());
		Collections.sort(peOTMoi, new HigestPEChangeOIComparator());

		int changeINOICE = 0;
		int changeINOIPE = 0;
		for (int i = 0; i < 5; i++) {
			changeINOICE = changeINOICE + ceOTMoi.get(i).getC_change_oi();
			changeINOIPE = changeINOIPE + peOTMoi.get(i).getP_change_oi();

		}
		System.out.println("c : " + changeINOICE);
		System.out.println("P : " + changeINOIPE);
		System.out.println("OI diff : " + Math.abs(changeINOICE - changeINOIPE));
		System.out.println("*********** ");

	}

}

class HigestPEChangeOIComparator implements Comparator<OptionModle> {

	@Override
	public int compare(OptionModle o1, OptionModle o2) {
		if (o1.getP_change_oi() == null || o2.getP_change_oi() == null) {
			return 0;
		}
		if (o1.getP_change_oi() < o2.getP_change_oi()) {
			return 1;
		} else if (o1.getP_change_oi() > o2.getP_change_oi()) {
			return -1;
		}

		return 0;
	}

}

class HigestCEChangeOIComparator implements Comparator<OptionModle> {

	@Override
	public int compare(OptionModle o1, OptionModle o2) {
		if (o1.getC_change_oi() == null || o2.getC_change_oi() == null) {
			return 0;
		}
		if (o1.getC_change_oi() < o2.getC_change_oi()) {
			return 1;
		} else if (o1.getC_change_oi() > o2.getC_change_oi()) {
			return -1;
		}

		return 0;
	}

}
