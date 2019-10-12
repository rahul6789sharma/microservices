package org.stocksrin.common.model.option;

public class AdvanceDecline {

	private int advances;
	private int declines;

	public int getAdvances() {
		return advances;
	}

	public void setAdvances(int advances) {
		this.advances = advances;
	}

	public int getDeclines() {
		return declines;
	}

	public void setDeclines(int declines) {
		this.declines = declines;
	}

	public static AdvanceDecline parse(String data) throws Exception {
		try {
			AdvanceDecline result = new AdvanceDecline();
			String[] advances = data.split("\"advances\":");
			String[] declines = data.split("\"declines\":");
			result.setAdvances(Integer.parseInt(advances[1].split(",")[0]));
			result.setDeclines(Integer.parseInt(declines[1].split(",")[0]));
			return result;
		} catch (Exception e) {
			// {"success":"true","rows":[{"advances":986,"declines":813,"unchanged":112,"total":1911}],"results":1}
			throw new Exception("Advanced Declined Response Chnages");
		}

	}

	@Override
	public String toString() {
		return "AdvanceDecline [advances=" + advances + ", declines=" + declines + "]";
	}

}