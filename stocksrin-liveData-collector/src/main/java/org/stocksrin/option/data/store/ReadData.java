package org.stocksrin.option.data.store;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.FileUtils;

public class ReadData {

	// symbole and its list
	public static Map<String, List<OIData>> data = new HashMap<>();

	public static void read() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MMM_yyyy");

		// convert String to LocalDate
		// LocalDate localDate = LocalDate.parse(date, formatter);

		List<String> lst = FileUtils.listdir(AppConstant.STOCKSRIN_OPTION_DATA_DIR_FNO);

		// lst.forEach(System.out::println);

		for (String string : lst) {
			String d = AppConstant.STOCKSRIN_OPTION_DATA_DIR_FNO + File.separator + string + File.separator
					+ "27JUN2019";
			List<String> files = FileUtils.listFilesForFolder(new File(d));
			// System.out.println(files);

			List<OIData> oiDataLst = new ArrayList<>();
			for (String string2 : files) {

				String filePath = d + File.separator + string2;
				// System.out.println(filePath);
				OptionModles o = FileUtils.fromJson2(filePath);
				LocalDate localDate = LocalDate.parse(string2.split(".json")[0], formatter);
				OIData oiData = new OIData(string, o.getTotal_ce_oi(), o.getTotal_pe_oi(), localDate);
				oiData.setClosePrice(o.getSpot());
				oiDataLst.add(oiData);
			}
			data.put(string, oiDataLst);

			//
		}

		// System.out.println(data);
		Set<String> keys = data.keySet();
		for (String string : keys) {
			List<OIData> d = data.get(string);
			//System.out.println(string);
			for (OIData string2 : d) {
				//System.out.println(string2.getClosePrice());
				//System.out.println(string2.getTotalCe());
				//System.out.println(string2.getTotalPe());
			}
			// System.out.println("-------------");

		}
	}

	public static void main(String[] args) throws Exception {

		/*
		 * int count=0; //System.out.println(data);
		 * System.out.println("***************"); for (String string : keys) {
		 * List<OIData> d = data.get(string);
		 * 
		 * for (OIData string2 : d) {
		 * 
		 * long diff =Math.abs(string2.getTotalCe()-string2.getTotalPe());
		 * if(diff>3000000) { System.out.println(string);
		 * System.out.println(string2.getTotalCe());
		 * System.out.println(string2.getTotalPe());
		 * System.out.println("-------------"); count++; } }
		 * 
		 * 
		 * }
		 */
		// System.out.println(count);
	}

}
