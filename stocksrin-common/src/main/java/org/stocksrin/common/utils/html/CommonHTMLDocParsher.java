package org.stocksrin.common.utils.html;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.stocksrin.common.model.option.FuturePrice;
import org.stocksrin.common.model.option.OptionModle;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.utils.DateUtils;

public class CommonHTMLDocParsher {

	public static FuturePrice getFuturePrice(Document doc) {
		try {
			Elements strong = doc.select("div[id=responseDiv]");
			String dataJson = strong.get(0).text();
			FuturePrice futurePrice = getUSDINRFuturefromJsonString(dataJson);
			return futurePrice;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public static FuturePrice getUSDINRFuturefromJsonString(String jsonData) {
		FuturePrice futurePrice = new FuturePrice();
		// System.out.println("jsonData " + jsonData);

		String a = jsonData.split("buyPrice1\":\"")[1].split("\",")[0];
		//System.out.println(a);
		futurePrice.setLastPrice(Double.parseDouble(a));
		//System.out.println("UsdInrFuture " + futurePrice);

		return futurePrice;
	}

	public static OptionModles parseNSEColumn(Document doc, Elements rows) {
		List<OptionModle> lst = parseOptionTableColumn(doc, rows);
		OptionModles result = updateTotalOI(doc, lst);
		try {
			Elements elements = getTable(doc, 0);
			String spotString = getSpotPrice(elements);
			result.setUnderlyingSpotPriceString(spotString);
			result.setSpot(parseSpot(spotString));
			result.setDate(getDate(spotString));
			result.setLastDataUpdated(getLastUpdate(spotString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static OptionModles parseUSDINRColumn(Document doc, Elements rows) throws Exception {
		List<OptionModle> lst = parseUSDINROptionTableColumn(doc, rows);
		OptionModles result = updateTotalOI(doc, lst);
		try {
			Elements elements = getTable(doc, 0);
			String spotString = getSpotPrice(elements).trim();
			result.setUnderlyingSpotPriceString(spotString);
			// result.setSpot(parseSpot(spotString));
			result.setDate(getDate(spotString));
			result.setLastDataUpdated(getLastUpdate(spotString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Elements getOptionChainTable(Document doc, String id, int index) throws Exception {
		Element table = (Element) doc.select("table[id=" + id + "]").get(0);
		Elements rows = table.select("tr");
		if ((rows.isEmpty()) || (rows == null)) {
			throw new Exception("Option Chain Table is brokean! Check the table id in Web Page :" + id);
		}
		return rows;
	}

	public static List<String> getSelectBoxById(Document doc, String id, int index) throws Exception {
		List<String> lst = new ArrayList<>();
		try {
			Elements dropbox = ((Element) doc.select("select[id=" + id + "]").get(index)).children();
			lst = new ArrayList<>();
			for (int i = 1; i < dropbox.size(); i++) {
				lst.add(((Element) dropbox.get(i)).text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (lst.isEmpty()) {
			throw new Exception("Expiry Select Box broken! Check expiry Select Box in web Page, ID does not exists : " + id);
		}
		return lst;
	}

	private static List<OptionModle> parseUSDINROptionTableColumn(Document doc, Elements rows) throws Exception {
		List<OptionModle> lst = new ArrayList<>();
		for (int i = 2; i < rows.size() - 1; i++) {
			Element row = (Element) rows.get(i);
			Elements cols = row.select("td");
			OptionModle column = new OptionModle();
			for (int j = 0; j < cols.size(); j++) {
				Element col = (Element) cols.get(j);
				if (cols.size() == 21) {
					column.setValueUSDINR(j, col.text());
				}
			}
			lst.add(column);
		}
		if (lst.isEmpty()) {
			throw new Exception("Option Chain Table is brokean! Check the table id='octable' in Web Page ");
		}
		return lst;
	}

	private static Elements getTable(Document doc, int index) throws Exception {
		Element table = (Element) doc.select("table").get(index);
		Elements rows = table.select("tr");
		if ((rows.isEmpty()) || (rows == null)) {
			throw new Exception("Option Chain Table is brokean! Check the table id in Web Page :");
		}
		return rows;
	}

	private static List<OptionModle> parseOptionTableColumn(Document doc, Elements rows) {
		List<OptionModle> lst = new ArrayList<>();
		for (int i = 2; i < rows.size() - 1; i++) {
			Element row = (Element) rows.get(i);
			Elements cols = row.select("td");

			OptionModle c = new OptionModle();
			for (int j = 0; j < cols.size(); j++) {
				Element col = (Element) cols.get(j);
				if (cols.size() == 23) {
					c.setValue(j, col.text());
				}
			}
			lst.add(c);
		}
		return lst;
	}

	private static OptionModles updateTotalOI(Document doc, List<OptionModle> column) {

		OptionModles columns = new OptionModles();
		columns.setOptionModle(column);

		int total_ce_oi = 0;
		int total_pe_oi = 0;

		int total_ce_oi_change = 0;
		int total_pe_oi_change = 0;

		for (OptionModle c : column) {

			if (c.getC_oi() != null) {
				total_ce_oi += c.getC_oi().intValue();
			}

			if (c.getP_oi() != null) {
				total_pe_oi += c.getP_oi().intValue();
			}

			if (c.getC_change_oi() != null) {
				total_ce_oi_change += c.getC_change_oi().intValue();
			}

			if (c.getP_change_oi() != null) {
				total_pe_oi_change += c.getP_change_oi().intValue();
			}

		}
		columns.setTotal_ce_oi(total_ce_oi);
		columns.setTotal_pe_oi(total_pe_oi);

		columns.setTotal_ce_oi_change(total_ce_oi_change);
		columns.setTotal_pe_oi_change(total_pe_oi_change);

		return columns;
	}

	private static String getSpotPrice(Elements elements) {
		String result = null;
		for (Element element : elements) {
			Elements cols = element.select("td");
			for (int j = 1; j < cols.size(); j++) {
				Element col = (Element) cols.get(j);

				result = col.text();
			}
		}
		return result;
	}

	private static Double parseSpot(String spot) {
		String[] arru = spot.split("As on");
		String[] c = arru[0].trim().split("NIFTY");
		String d = c[1];

		int length = d.length();
		String e = d.substring(0, length - 1);
		return Double.valueOf(Double.parseDouble(e));
	}

	private static String getLastUpdate(String dateInString) throws Exception {
		String dateString = null;
		try {
			String[] a = dateInString.split("As on");
			String d = a[1].trim();

			dateString = d;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR getDate " + dateInString);
		}
		return dateString;
	}

	private static String getDate(String dateInString) throws Exception {
		String dateString = null;
		try {
			String[] a = dateInString.split("As on");
			String d = a[1].trim();

			dateString = getDateStringCustom(d);

			Date todayDate = new Date();
			String today = DateUtils.dateToString(todayDate, "dd_MMM_yyyy");
			if (!dateString.equals(today)) {
			}
			return dateString;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR getDate " + dateInString);
		}
	}

	private static String getDateStringCustom(String date) {
		String[] c = date.replaceAll(",", "").split(" ");
		return c[1] + "_" + c[0] + "_" + c[2];
	}
}
