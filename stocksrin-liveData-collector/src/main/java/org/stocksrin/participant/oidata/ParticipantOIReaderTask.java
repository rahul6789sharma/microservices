package org.stocksrin.participant.oidata;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.stereotype.Controller;
import org.stocksrin.common.model.ParticipantOIModle;
import org.stocksrin.common.model.ParticipantType;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.NSEHolidayUtils;
import org.stocksrin.email.SendEmail;

@Controller
public class ParticipantOIReaderTask extends TimerTask {
	static DecimalFormat formatter = new DecimalFormat("00.00");

	@Override
	public void run() {
		try {
			// clear data on every month
			if (DateUtils.getCurrentDatetwoDigit().equals("01")) {
				ParticapentOIData.data.clear();
			}

			if (!DateUtils.isWeekEndDay() && !NSEHolidayUtils.isHoliday()) {

				String date = DateUtils.dateToString(new Date(), "MMMyyyy");
				String dir = AppConstant.FO_OI_DIR + date;
				Util.collectAllDateForDay(FileUtils.lasFilePath(dir + File.separator));
			}
			StringBuilder data = getString();
			SendEmail.sentMail("Future OI All Particapent Data", "" + data.toString(), "liva-Data-Downloader");
		} catch (Exception e) {
			SendEmail.sentMail("CRITICAL! FO OI All Particapent Data", "ERROR " + e.getMessage(), "liva-Data-Downloader");
			e.printStackTrace();
		}
	}

	private static void loadParticipantOIDataOn() {
		try {

			String date2 = DateUtils.dateToString(new Date(), "MMMyyyy");
			// String date2 = "Dec2018";
			String dir = AppConstant.FO_OI_DIR + date2;

			List<String> files = FileUtils.listFilesForFolder(new File(dir));
			System.out.println(files);
			for (String string : files) {
				Util.collectAllDateForDay(dir + File.separator + string);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		loadParticipantOIDataOn();
		// System.out.println(ParticapentOIData.data.keySet());
		List<String> lst = new ArrayList<>(ParticapentOIData.data.keySet());

		String lastEntry = lst.get(lst.size() - 2);
		String currentEntry = lst.get(lst.size() - 1);
		List<ParticipantOIModle> data0 = ParticapentOIData.data.get(lastEntry);
		List<ParticipantOIModle> data1 = ParticapentOIData.data.get(currentEntry);

		StringBuilder data = getString();
		System.out.println(data);
		SendEmail.sentMail("Future OI All Particapent Data", "ERROR " + data.toString(), "liva-Data-Downloader");
	}

	private static StringBuilder getString() {
		// loadParticipantOIDataOn();
		StringBuilder result = new StringBuilder();
		result.append(printOIData(ParticipantType.FII, 1000));

		result.append("\n-----------------\n");
		result.append(printOIData(ParticipantType.PRO, 1000));

		result.append("\n-----------------\n");
		result.append(printOIData(ParticipantType.DII, 1000));

		result.append("\n-----------------\n");
		result.append(printOIData(ParticipantType.CLIENT, 1000));

		result.append("\n-----------------\n");

		return result;
	}

	private static StringBuilder printOIData(ParticipantType participantType, int offset) {
		StringBuilder result = new StringBuilder();

		List<Integer> longdata = new ArrayList<>();
		List<Integer> shortdata = new ArrayList<>();
		List<Integer> net = new ArrayList<>();

		for (Map.Entry<String, List<ParticipantOIModle>> entry : ParticapentOIData.data.entrySet()) {
			List<ParticipantOIModle> d = entry.getValue();
			for (ParticipantOIModle participantOIModle : d) {
				if (participantOIModle.getParticipantType().equals(participantType)) {
					longdata.add(participantOIModle.getFutureIndexLong());
					shortdata.add(participantOIModle.getFutureIndexShort());
					net.add(participantOIModle.getFutureIndexLong() - participantOIModle.getFutureIndexShort());
				}
			}
		}
		result.append(participantType);
		result.append("\n");
		result.append("Long " + copy(longdata));
		result.append("\n");
		result.append("Short " + copy(shortdata));
		result.append("\n");
		result.append("Net " + copy(net));

		return result;
	}

	private static List<String> copy(List<Integer> lst) {
		List<String> result = new ArrayList<>();
		for (Integer i : lst) {
			result.add(formatter.format((float) (i) / 1000));
		}

		return result;
	}
}
