package org.stocksrin.services;

import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.model.app.health.MemoryStatus;
import org.stocksrin.email.SendEmail;
import org.stocksrin.monitors.AppMonitor;

public class WishTask implements Runnable {
	String msg;

	public WishTask(String msg) {
		this.msg = msg;
	}

	public void run() {
		String result = getUsedMemory();
		System.out.println(result);
		SendEmail.sentMail("HealStatus : " + this.msg, result.toString(), "Data-Downloader");
	}

	public static String getUsedMemory() {

		List<MemoryStatus> memoryStatus = new ArrayList<>();
		memoryStatus.add(AppMonitor.getDataDowloaderemoryStatus());
		memoryStatus.add(AppMonitor.getLiveData_Collector());
		memoryStatus.add(AppMonitor.getStrategies_Builder());
		StringBuilder st = new StringBuilder();
		for (MemoryStatus memoryStatus2 : memoryStatus) {
			st.append("\n");
			st.append(memoryStatus2.toString());
			st.append("\n");

		}

		return st.toString();
	}
}
