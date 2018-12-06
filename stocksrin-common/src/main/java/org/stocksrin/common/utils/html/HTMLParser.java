package org.stocksrin.common.utils.html;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.stocksrin.common.model.FIIDIIDataModle;

public class HTMLParser {
	public static void getDII_Data(String url, FIIDIIDataModle fIIDIIDataModle) throws Exception {
		// Document doc = HTMLPageUtils.getHTMLDocument(url);
		Document doc = HTMLPageDocumentDownloader.getDocument(url);
		try {
			Elements table = HTMLPageUtils.getTable(doc, 0);

			String result = null;

			Elements cols = ((Element) table.get(2)).getAllElements();
			for (int j = 1; j < cols.size(); j++) {
				Element col = (Element) cols.get(j);

				result = col.text();
				if (j == 3) {
					fIIDIIDataModle.setDii_BuyValue(result);
				} else if (j == 4) {
					fIIDIIDataModle.setDii_SellValue(result);
				} else if (j == 5) {
					fIIDIIDataModle.setDii_net(result);
				}
			}
		} catch (Exception e) {
			throw new Exception("ERROR! FII DII Data Exception " + e.getMessage());
		}
	}

	public static void getFII_Data(String url, FIIDIIDataModle fIIDIIDataModle) throws Exception {
		// Document doc = HTMLPageUtils.getHTMLDocument(url);
		Document doc = HTMLPageDocumentDownloader.getDocument(url);
		try {
			Elements table = HTMLPageUtils.getTable(doc, 0);

			String result = null;

			Elements cols = ((Element) table.get(2)).getAllElements();
			for (int j = 1; j < cols.size(); j++) {
				Element col = (Element) cols.get(j);

				result = col.text();
				if (j == 2) {
					fIIDIIDataModle.setDate(result);
				} else if (j == 3) {
					fIIDIIDataModle.setFii_BuyValue(result);
				} else if (j == 4) {
					fIIDIIDataModle.setFii_SellValue(result);
				} else if (j == 5) {
					fIIDIIDataModle.setFii_net(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR! FII DII Data Exception url :" + url + " , " + e.getMessage());
		}
	}
}
