package org.stocksrin.reports.delivery;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.DateUtils;
import org.stocksrin.common.utils.FileUtils;

public class DeliveryReportsReader {

	static final String fullpath = AppConstant.REPORTS_DELIVRY;
	private final static String dateFormate = "dd-MMM-yyyy";

	// symbole and its sorted DeliveryData by date
	static SortedMap<Date, List<DeliveryReportEntity>> allData = new TreeMap<>();

	// symbole and its data
	static SortedMap<String, List<DeliveryReportEntity>> increasingPer = new TreeMap<>();

	public static void main(String[] args) throws Exception {
		String currentMonth = DateUtils.getCurrentMonth();
		List<String> a= new ArrayList<>();
		a.add("TATAMOTORS");
		a.add("INFY");
		a.add("HDFC");
		 Map<String, List<DeliveryReportEntity>> map= deliveryData(a, currentMonth);
		 Set<String> keys = map.keySet();
		 for (String string : keys) {
			 System.out.println("*********");
			 List<DeliveryReportEntity> lst=map.get(string);
			 lst.forEach((i) -> System.out.println(i));
			 
		}
		//lst.forEach((i) -> System.out.println(i));
		/*
		 * Comparator<DeliveryReportEntity> cmp = new Comparator<DeliveryReportEntity>()
		 * {
		 * 
		 * @Override public int compare(DeliveryReportEntity o1, DeliveryReportEntity
		 * o2) { int a = o1.getDATE1().compareTo(o2.getDATE1()); if (a == 1) { return
		 * -1; } else if (a == -1) { return 1; } else { return 0; } } };
		 * 
		 * // System.out.println(allData); Map<String, SortedSet<DeliveryReportEntity>>
		 * map = new HashMap<>(); Set<Date> keys = allData.keySet(); for (Date key :
		 * keys) { List<DeliveryReportEntity> e = allData.get(key); for
		 * (DeliveryReportEntity d : e) { SortedSet<DeliveryReportEntity> a =
		 * map.get(d.getSymbole()); if (a == null) { SortedSet<DeliveryReportEntity> b =
		 * new TreeSet<>(cmp); b.add(d); map.put(d.getSymbole(), b); } else { a.add(d);
		 * } // System.out.println(d); } } Set<String> keyss = map.keySet(); for (String
		 * string : keyss) {
		 * 
		 * // if (string.equals("INFY")) { System.out.println(string);
		 * SortedSet<DeliveryReportEntity> r = map.get(string);
		 * List<DeliveryReportEntity> rr = new ArrayList<>(r); for (int i = 0; i <
		 * rr.size(); i++) { // System.out.println(rr.get(i)); if (i < rr.size() - 1 &&
		 * rr.get(i).getDELIV_PER() != null && rr.get(i + 1).getDELIV_PER() != null &&
		 * rr.get(i).getDELIV_PER() > rr.get(i + 1).getDELIV_PER()) {
		 * System.out.println(rr.get(i)); List<DeliveryReportEntity> rrr =
		 * increasingPer.get(string); if (rrr == null) { List<DeliveryReportEntity> ee =
		 * new ArrayList<>(); ee.add(rr.get(i)); // increasingPer.put(string, ee); }
		 * else { rrr.add(rr.get(i)); } }
		 * 
		 * }
		 */
		// }
		// System.out.println(increasingPer);
		/*
		 * List<DeliveryReportEntity> i = r.stream().sorted( (DeliveryReportEntity o1,
		 * DeliveryReportEntity o2) ->o1.getDELIV_PER().compareTo(o2.getDELIV_PER())
		 * 
		 * ).collect(Collectors.toList());
		 */

		// increasingPer.put(string, i);

		// }
		/*
		 * System.out.println(increasingPer.keySet().size()); List<DeliveryReportEntity>
		 * r = increasingPer.get("INFY"); for (DeliveryReportEntity deliveryReportEntity
		 * : r) { System.out.println(deliveryReportEntity); }
		 */
		// System.out.println(map.get("INFY"));

	}

	private static Map<String, List<DeliveryReportEntity>> deliveryData(List<String> symboles, String month) throws Exception {
		Map<String, List<DeliveryReportEntity>> result = new HashMap<>();
		for (String string : symboles) {
			result.put(string, readCurrrentMonthData(string, month));
		}
		return result;

	}


	private static List<DeliveryReportEntity> readCurrrentMonthData(String symbole, String month) throws Exception {
		List<DeliveryReportEntity> result = new ArrayList<>();
		List<String> dates = FileUtils.listFilesForFolder(fullpath);
		for (String string : dates) {
			if (string.contains(month)) {
				System.out.println(string);
				// Date d = DateUtils.stringToDate(string.split(".csv")[0], dateFormate);

				result.add(getDataFromFile(symbole, fullpath + File.separator + string));
				// SortedSet<DeliveryReportEntity> lst = new TreeSet<>();

			}
		}
		return result;
	}

	// read all file for current month
	private static void readCurrrentMonthData(String month) throws Exception {
		List<DeliveryReportEntity> result = new ArrayList<>();
		List<String> dates = FileUtils.listFilesForFolder(fullpath);
		for (String string : dates) {
			if (string.contains(month)) {
				System.out.println(string);
				// Date d = DateUtils.stringToDate(string.split(".csv")[0], dateFormate);

				getDataFromFile(fullpath + File.separator + string);
				// SortedSet<DeliveryReportEntity> lst = new TreeSet<>();

			}
		}
	}

	private static void test1() throws Exception {
		List<String> dates = FileUtils.listFilesForFolder(fullpath);
		for (String string : dates) {

			Date d = DateUtils.stringToDate(string.split(".csv")[0], dateFormate);
			if (!allData.containsKey(d)) {
				List<DeliveryReportEntity> e = getDataFromFile(fullpath + File.separator + string);
				SortedSet<DeliveryReportEntity> lst = new TreeSet<>();
				allData.put(d, e);
			} else {
				// SortedSet<DeliveryReportEntity> e = allData.get(d);
			}
		}
	}

	private static void test() throws Exception {

		System.out.println(DateUtils.dateToString(new Date(), dateFormate));
		System.out.println(fullpath);
		List<String> dates = FileUtils.listFilesForFolder(fullpath);
		System.out.println(dates);
		for (String string : dates) {
			readFile(fullpath + File.separator + string, "TATAMOTORS");
		}
	}

	static SortedMap<Date, DeliveryReportEntity> result = new TreeMap<>();

	private static DeliveryReportEntity getDataFromFile(String symbole, String filePath) {

		List<String[]> data = CommonUtils.getCSVData(filePath);
		for (String[] strings : data) {
			if (strings[0].trim().equalsIgnoreCase(symbole)) {
				return convertData(strings);
			}
		}
		return null;
	}

	private static List<DeliveryReportEntity> getDataFromFile(String filePath) {

		List<DeliveryReportEntity> deliveryReportEntitys = new ArrayList<>();
		List<String[]> data = CommonUtils.getCSVData(filePath);
		for (String[] strings : data) {
			DeliveryReportEntity e = convertData(strings);
			deliveryReportEntitys.add(e);
		}
		return deliveryReportEntitys;
	}

	private static SortedMap<Date, DeliveryReportEntity> readFile(String filePath, String symbole) throws Exception {

		List<String[]> data = CommonUtils.getCSVData(filePath);
		for (String[] strings : data) {

			if (strings[0].equalsIgnoreCase(symbole)) {
				DeliveryReportEntity deliveryReportEntity = convertData(strings);
				Date date = deliveryReportEntity.getDATE1();
				result.put(date, deliveryReportEntity);
			}

		}
		System.out.println(result);
		return result;
	}

	private static DeliveryReportEntity convertData(String[] data) {
		DeliveryReportEntity deliveryReportEntity = new DeliveryReportEntity();
		try {
			deliveryReportEntity.setSymbole(data[0].trim());
			deliveryReportEntity.setSERIES(data[1].trim());
			deliveryReportEntity.setDATE1(DateUtils.stringToDate(data[2].trim(), dateFormate));
			deliveryReportEntity.setCLOSE_PRICE(Double.parseDouble(data[8].trim()));
			deliveryReportEntity.setTTL_TRD_QNTY(Integer.parseInt(data[10].trim()));
			if (!data[13].trim().contains("-")) {
				deliveryReportEntity.setDELIV_QTY(Integer.parseInt(data[13].trim()));
			}
			if (!data[14].trim().contains("-")) {
				deliveryReportEntity.setDELIV_PER(Double.parseDouble(data[14].trim()));
			} else {
				deliveryReportEntity.setDELIV_PER(0.0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deliveryReportEntity;

	}

}

class DeliveryReportEntity {
	String symbole;
	String SERIES;
	Date DATE1;
	Double PREV_CLOSE;
	Double OPEN_PRICE;
	Double HIGH_PRICE;
	Double LOW_PRICE;
	Double LAST_PRICE;
	Double CLOSE_PRICE;
	Double AVG_PRICE;
	Integer TTL_TRD_QNTY;
	Double TURNOVER_LACS;
	Integer NO_OF_TRADES;
	Integer DELIV_QTY;
	Double DELIV_PER;

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public String getSERIES() {
		return SERIES;
	}

	public void setSERIES(String sERIES) {
		SERIES = sERIES;
	}

	public Date getDATE1() {
		return DATE1;
	}

	public void setDATE1(Date dATE1) {
		DATE1 = dATE1;
	}

	public Double getPREV_CLOSE() {
		return PREV_CLOSE;
	}

	public void setPREV_CLOSE(Double pREV_CLOSE) {
		PREV_CLOSE = pREV_CLOSE;
	}

	public Double getOPEN_PRICE() {
		return OPEN_PRICE;
	}

	public void setOPEN_PRICE(Double oPEN_PRICE) {
		OPEN_PRICE = oPEN_PRICE;
	}

	public Double getHIGH_PRICE() {
		return HIGH_PRICE;
	}

	public void setHIGH_PRICE(Double hIGH_PRICE) {
		HIGH_PRICE = hIGH_PRICE;
	}

	public Double getLOW_PRICE() {
		return LOW_PRICE;
	}

	public void setLOW_PRICE(Double lOW_PRICE) {
		LOW_PRICE = lOW_PRICE;
	}

	public Double getLAST_PRICE() {
		return LAST_PRICE;
	}

	public void setLAST_PRICE(Double lAST_PRICE) {
		LAST_PRICE = lAST_PRICE;
	}

	public Double getCLOSE_PRICE() {
		return CLOSE_PRICE;
	}

	public void setCLOSE_PRICE(Double cLOSE_PRICE) {
		CLOSE_PRICE = cLOSE_PRICE;
	}

	public Double getAVG_PRICE() {
		return AVG_PRICE;
	}

	public void setAVG_PRICE(Double aVG_PRICE) {
		AVG_PRICE = aVG_PRICE;
	}

	public int getTTL_TRD_QNTY() {
		return TTL_TRD_QNTY;
	}

	public void setTTL_TRD_QNTY(int tTL_TRD_QNTY) {
		TTL_TRD_QNTY = tTL_TRD_QNTY;
	}

	public Double getTURNOVER_LACS() {
		return TURNOVER_LACS;
	}

	public void setTURNOVER_LACS(Double tURNOVER_LACS) {
		TURNOVER_LACS = tURNOVER_LACS;
	}

	public int getNO_OF_TRADES() {
		return NO_OF_TRADES;
	}

	public void setNO_OF_TRADES(int nO_OF_TRADES) {
		NO_OF_TRADES = nO_OF_TRADES;
	}

	public int getDELIV_QTY() {
		return DELIV_QTY;
	}

	public void setDELIV_QTY(int dELIV_QTY) {
		DELIV_QTY = dELIV_QTY;
	}

	public Double getDELIV_PER() {
		return DELIV_PER;
	}

	public void setDELIV_PER(Double dELIV_PER) {
		DELIV_PER = dELIV_PER;
	}

	@Override
	public String toString() {
		return "DeliveryReportEntity [symbole=" + symbole + ", SERIES=" + SERIES + ", DATE1=" + DATE1 + ", PREV_CLOSE=" + PREV_CLOSE + ", OPEN_PRICE=" + OPEN_PRICE + ", HIGH_PRICE=" + HIGH_PRICE
				+ ", LOW_PRICE=" + LOW_PRICE + ", LAST_PRICE=" + LAST_PRICE + ", CLOSE_PRICE=" + CLOSE_PRICE + ", AVG_PRICE=" + AVG_PRICE + ", TTL_TRD_QNTY=" + TTL_TRD_QNTY + ", TURNOVER_LACS="
				+ TURNOVER_LACS + ", NO_OF_TRADES=" + NO_OF_TRADES + ", DELIV_QTY=" + DELIV_QTY + ", DELIV_PER=" + DELIV_PER + "]";
	}

}