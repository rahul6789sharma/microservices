package org.stocksrin.common.utils.html;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLPageDocumentDownloader {

	private static final int RETRY = 5;

	public static Document getDocument(String url) {
		int retryCounter = 0;
		while (retryCounter < RETRY) {
			try {
				return Jsoup.connect(url).get();
			} catch (IOException e) {
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					System.out.println("");
					e1.printStackTrace();
				}
				retryCounter++;
				System.out.println("FAILED - Command failed on retry " + retryCounter + " of " + RETRY + " error: " + e);
				e.printStackTrace();
				if (retryCounter >= 3) {
					System.out.println("Max retries exceeded.");
				}
			}
		}
		throw new RuntimeException("Command failed on all of 3 retries, url " + url);
	}

	public static Document getDocumentFromFile(String filePath) {
		try {
			return Jsoup.parse(new File(filePath), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
