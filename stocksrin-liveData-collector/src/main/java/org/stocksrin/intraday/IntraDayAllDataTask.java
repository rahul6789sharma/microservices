package org.stocksrin.intraday;

import java.util.TimerTask;

import org.springframework.stereotype.Controller;
import org.stocksrin.common.data.BankNiftyData;
import org.stocksrin.common.data.NiftyData;
import org.stocksrin.common.model.intraday.AllIntraDayData;
import org.stocksrin.common.model.intraday.IntaDayData;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.rules.nifty.OptionPremiumDecayCalculation;

@Controller
public class IntraDayAllDataTask extends TimerTask {

	private static long timeInteval5min = 300000; // 5 min

	private static Double niftyDailyAtmStrike;
	private static Double bnfDailyAtmStrike;

	@Override
	public void run() {
		AllIntraDayData allIntraDayData = new AllIntraDayData();
		processdata(allIntraDayData);
		if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
			// handle null data over weekend and after market time
			// deployment
			while (CommonUtils.getEveningTime()) {
				processdata(allIntraDayData);
			}
		}

	}

	private static void processdata(AllIntraDayData allIntraDayData) {
		try {
			Thread.sleep(timeInteval5min);
			String currentMOnthExpiry = NiftyData.shortedExpiry.first();

			OptionModles niftyOptionModles = NiftyData.optionData.get(currentMOnthExpiry);
			OptionModles bnfOptionModles = BankNiftyData.bnOptionData.get(currentMOnthExpiry);

			// morning niftyDailyAtmStrike ll be null
			// setting atm strice in morning itself
			if (niftyDailyAtmStrike == null) {
				niftyDailyAtmStrike = niftyOptionModles.getAtmStrike();
			}
			if (bnfDailyAtmStrike == null) {
				bnfDailyAtmStrike = bnfOptionModles.getAtmStrike();
			}

			allIntraDayData.setTotalAdvance(NiftyData.advanceDecline.getAdvances());
			allIntraDayData.setTotalDecline(NiftyData.advanceDecline.getDeclines());

			allIntraDayData.getTotalAdvances().add(NiftyData.advanceDecline.getAdvances());
			allIntraDayData.getTotalDeclines().add(NiftyData.advanceDecline.getDeclines());

			allIntraDayData.setNifty_change_ce_oi(getlakcs(niftyOptionModles.getTotal_ce_oi_change()));
			allIntraDayData.setNifty_change_pe_oi(getlakcs(niftyOptionModles.getTotal_pe_oi_change()));
			allIntraDayData.setNifty_spot(niftyOptionModles.getSpot());

			Integer[] nifty_ce_pe = OptionPremiumDecayCalculation.premiumDecay(niftyOptionModles, niftyDailyAtmStrike);
			allIntraDayData.getNifty_ce_avg_premium().add(nifty_ce_pe[0]);
			allIntraDayData.getNifty_pe_avg_premium().add(nifty_ce_pe[1]);

			allIntraDayData.getNifty_change_ce_oi_lst().add(getlakcs(niftyOptionModles.getTotal_ce_oi_change()));
			allIntraDayData.getNifty_change_pe_oi_lst().add(getlakcs(niftyOptionModles.getTotal_pe_oi_change()));

			allIntraDayData.setNifty_expiry(bnfOptionModles.getExpiry());

			allIntraDayData.setBnf_change_ce_oi(getlakcs(bnfOptionModles.getTotal_ce_oi_change()));
			allIntraDayData.setBnf_change_pe_oi(getlakcs(bnfOptionModles.getTotal_pe_oi_change()));
			allIntraDayData.setBnf_spot(bnfOptionModles.getSpot());
			Integer[] bnf_ce_pe = OptionPremiumDecayCalculation.premiumDecay(bnfOptionModles, bnfDailyAtmStrike);

			allIntraDayData.getBnf_ce_avg_premium().add(bnf_ce_pe[0]);
			allIntraDayData.getBnf_pe_avg_premium().add(bnf_ce_pe[1]);

			allIntraDayData.getBnf_change_ce_oi_lst().add(getlakcs(bnfOptionModles.getTotal_ce_oi_change()));
			allIntraDayData.getBnf_change_pe_oi_lst().add(getlakcs(bnfOptionModles.getTotal_pe_oi_change()));
			allIntraDayData.setBnf_expiry(bnfOptionModles.getExpiry());

			allIntraDayData.setLastDataUpdated(niftyOptionModles.getLastDataUpdated());

			IntaDayData.allIntraDayData = allIntraDayData;
			System.out.println(allIntraDayData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int getlakcs(int value) {
		return value / 100000;

	}

}
