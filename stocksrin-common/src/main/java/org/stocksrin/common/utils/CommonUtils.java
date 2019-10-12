package org.stocksrin.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.stocksrin.common.model.FIIDIIDataModle;
import org.stocksrin.common.model.trade.Strategy;
import org.stocksrin.common.model.trade.StrategyModel;

public class CommonUtils {

	private static final int MARKET_HR = 15;
	private static final int MARKET_MINUTE = 35;
	private static String line = "----------------------------------------------------------------------------------------------";

	public static void createStrategyFile(Strategy strategy, String dir, String fileName) {
		try {
			createStrategyFile(dir, strategy, fileName);
			List<StrategyModel> lst = strategy.getStrategyModels();

			StringBuilder string = new StringBuilder();
			string.append(line + "\n");
			double totalPremium = 0.0;
			for (StrategyModel strategyModel : lst) {
				string.append(strategyModel.toCSV());
				string.append("\n");
				totalPremium = totalPremium + strategyModel.getAvgPrice();
			}
			string.append(line + "\n");
			Integer totalp = (int) Math.round(totalPremium); // 3
			string.append("Total Premium : " + totalp);
			// SendEmail.sentMail("Premium : [" + totalp + "] ," +
			// fileName,string.toString(), "strategy Builder");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createStrategyFile(String dir, Strategy strategy, String strategyName) {

		String header = "strategySerial,expiry,type,strike,close_price,quantity,target,stopLoss,spot_close,desc,status,traded_IV,tradedDate";
		String file = dir + strategyName + ".csv";
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		FileUtils.makeFile(file);
		FileUtils.appendData(header, file);

		List<StrategyModel> m = strategy.getStrategyModels();
		for (StrategyModel strategyModel : m) {
			FileUtils.appendData(strategyModel.toCSV(), file);
		}

	}

	public static boolean isTimeLessThen(Integer hr, Integer min) {
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		today.set(11, hr.intValue());
		today.set(12, min.intValue());
		today.set(13, 0);

		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		if (now.after(today)) {
			return false;
		}
		return true;
	}

	public static void appendData(String data, String fileName) {

		File file = new File(fileName);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			throw new RuntimeException(fileName + " File not exist");
		}

		try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);) {

			bw.write("\n" + data);

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	// ll not read first row
	public static List<String[]> getCSVData(String csvFile) {

		String line = "";
		String cvsSplitBy = ",";
		List<String[]> lst = new ArrayList<>(2);
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile));) {

			int i = 0;
			while ((line = br.readLine()) != null) {
				if (i != 0) {
					// use comma as separator
					String[] data = line.split(cvsSplitBy);
					lst.add(data);
				}
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static String[] getCSVData_FirstLine(String csvFile) {

		String line = "";
		String cvsSplitBy = ",";
		List<String[]> lst = new ArrayList<>(2);
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile));) {

			int i = 0;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				return data;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void appendData2(String data, String fileName) {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			File file = new File(fileName);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				throw new RuntimeException(fileName + " File not exist");
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(data);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}

	public static FIIDIIDataModle getFIIModelFromCSV(String csvData) {
		FIIDIIDataModle fIIDIIDataModle = new FIIDIIDataModle();
		String cvsSplitBy = ",";

		String[] data = csvData.split(cvsSplitBy);
		fIIDIIDataModle.setDate(data[0]);
		fIIDIIDataModle.setFii_BuyValue(data[1]);
		fIIDIIDataModle.setFii_SellValue(data[2]);
		fIIDIIDataModle.setFii_net(data[3]);
		fIIDIIDataModle.setDii_BuyValue(data[4]);
		fIIDIIDataModle.setDii_SellValue(data[5]);
		fIIDIIDataModle.setDii_net(data[6]);
		fIIDIIDataModle.setNiftyprice(data[7]);
		fIIDIIDataModle.setNiftyChange(data[8]);
		fIIDIIDataModle.setStocks_Advance(data[9]);
		fIIDIIDataModle.setStocks_Decline(data[10]);
		return fIIDIIDataModle;
	}

	public static boolean getEveningTimeUSDINR() {
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		today.set(11, 17);
		today.set(12, 10);
		today.set(13, 0);

		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		if (now.after(today)) {
			System.out.println("USD Time is more then 5:00 pm");
			return false;
		}
		return true;
	}

	public static boolean getEveningTime() {
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		today.set(11, 15);
		today.set(12, 35);
		today.set(13, 0);

		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		if (now.after(today)) {
			System.out.println("Time is more then 3:30 pm");
			return false;
		}
		return true;
	}

	public static boolean getEveningTimeForStrategy() {
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		today.set(11, 15);
		today.set(12, 15);
		today.set(13, 0);

		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		if (now.after(today)) {
			return false;
		}
		return true;
	}

	public static String getFilesName(List<String> files) {
		StringBuilder string = new StringBuilder();
		for (String f : files) {
			string.append("\n");
			string.append(f);
		}
		return string.toString();
	}
}
