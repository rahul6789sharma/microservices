package org.stocksrin.services;

import org.stocksrin.email.SendEmail;

public class WishTask implements Runnable {
	String msg;

	public WishTask(String msg) {
		this.msg = msg;
	}

	public void run() {
		SendEmail.sentMail("MS2 DataDownloader : "+this.msg, "", "LiveData-Collector");
	}
}
