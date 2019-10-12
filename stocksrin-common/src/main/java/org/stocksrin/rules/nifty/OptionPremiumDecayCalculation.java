package org.stocksrin.rules.nifty;

import java.util.List;

import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;

public class OptionPremiumDecayCalculation {

	// need to take fix atmStrike in morning
	public static Integer[] premiumDecay(OptionModles optionModles, double atmStrike) {
		Integer[] ce_pe_premium = {0,0};
		List<OptionModle> lst = optionModles.getOptionModle();
		double callStrikeLimit = atmStrike + 500;
		double putStrikeLimit = atmStrike - 500;

		int cecount = 0;
		double cePremiumAvg = 0.0;
		for (OptionModle optionModle : lst) {
			if (atmStrike <= optionModle.getStrike_price() && callStrikeLimit >= optionModle.getStrike_price()) {
				// System.out.println("call " + optionModle.getStrike_price());
				if (optionModle.getC_ltp() != null) {
					cePremiumAvg = cePremiumAvg + optionModle.getC_ltp();
					cecount++;
				}
			}
		}

		int pecount = 0;
		double pePremiumAvg = 0.0;
		for (OptionModle optionModle : lst) {
			if (atmStrike >= optionModle.getStrike_price() && putStrikeLimit <= optionModle.getStrike_price()) {
				// System.out.println("put " + optionModle.getStrike_price());
				if (optionModle.getP_ltp() != null) {
					pePremiumAvg = pePremiumAvg + optionModle.getP_ltp();
					pecount++;
				}
			}
		}
		/*
		 * System.out.println("cePremiumAvg " + cePremiumAvg / cecount);
		 * System.out.println("pePremiumAvg " + pePremiumAvg / pecount);
		 */
		int ce = (int) cePremiumAvg / cecount;
		int pe = (int) pePremiumAvg / pecount;
		ce_pe_premium[0] = ce;
		ce_pe_premium[1] = pe;

		return ce_pe_premium;
	}

}
