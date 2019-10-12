package org.stocksrin.common.model.future;

import java.util.Arrays;

public class Future {

	private String valid;
	private String tradedDate;
	private String eqLink;
	private Data[] data;
	private String companyName;
	private String lastUpdateTime;
	private String isinCode;
	private String ocLink;

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getTradedDate() {
		return tradedDate;
	}

	public void setTradedDate(String tradedDate) {
		this.tradedDate = tradedDate;
	}

	public String getEqLink() {
		return eqLink;
	}

	public void setEqLink(String eqLink) {
		this.eqLink = eqLink;
	}

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getOcLink() {
		return ocLink;
	}

	public void setOcLink(String ocLink) {
		this.ocLink = ocLink;
	}

	@Override
	public String toString() {
		return "Future [valid=" + valid + ", tradedDate=" + tradedDate + ", eqLink=" + eqLink + ", data="
				+ Arrays.toString(data) + ", companyName=" + companyName + ", lastUpdateTime=" + lastUpdateTime
				+ ", isinCode=" + isinCode + ", ocLink=" + ocLink + "]";
	}

}
