package org.stocksrin.common.utils.html;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLPageUtils {
	
	public static Document getDocumentFromFile(String filePath) {
		try {
			
			return Jsoup.parse(new File(filePath), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*public static Document getHTMLDocument(String url) {
		try {
			
			return Jsoup.connect(url).get();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
*/
	public static Elements getTable(Document doc, int index) throws Exception {
		Element table = (Element) doc.select("table").get(index);
		Elements rows = table.select("tr");
		if ((rows.isEmpty()) || (rows == null)) {
			throw new Exception("Cant get Table out of Document");
		}
		return rows;
	}
}
