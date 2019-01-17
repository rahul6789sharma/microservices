package org.stocksrin.ai;

import java.util.Date;
import java.util.TimerTask;

import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;

public class AIWriterTask extends TimerTask {

	@Override
	public void run() {

		try {
			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {
				System.out.println("writing AI data to file");
				String date = DateUtils.dateToString(new Date(), "ddMMMyyyy");
				String path = AppConstant.STOCKSRIN_AI + date + ".json";
				FileUtils.writeDataAsJson(NIftyAIData.straddleData, path);
				System.out.println("writing AI data to file completed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
