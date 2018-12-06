package org.stocksrin.participant.oidata.cash;

import org.stocksrin.common.model.FIIDIIDataModle;
import org.stocksrin.common.model.NSEIndice;
import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.html.HTMLParser;
import org.stocksrin.indices.data.NiftyIndicesDataColloctor;

public class FIIDIIDailyReportUtils {

	public static FIIDIIDataModle getData() throws Exception {
		
		FIIDIIDataModle fIIDIIDataModle = new FIIDIIDataModle();

		HTMLParser.getFII_Data(AppConstant.FII_DATA_URL, fIIDIIDataModle);
		HTMLParser.getDII_Data(AppConstant.DII_DATA_URL, fIIDIIDataModle);

		try {
			NSEIndice nifty = NiftyIndicesDataColloctor.getData("NIFTY 50");
			fIIDIIDataModle.setNiftyChange(nifty.getChange());
			fIIDIIDataModle.setNiftyprice(nifty.getLastPrice().replaceAll(",", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fIIDIIDataModle;

	}

}
