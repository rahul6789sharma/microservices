package org.stocksrin.common.model;

public class NSEIndicesData {
	private String preOpen;
	private String time;
	private String corrOpen;
	private String status;
	private String haltedStatus;
	private String mktOpen;
	private NSEIndice[] data;
	private String code;
	private String preClose;
	private String corrClose;
	private String mktClose;

	public String getPreOpen() {
		return this.preOpen;
	}

	public void setPreOpen(String preOpen) {
		this.preOpen = preOpen;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCorrOpen() {
		return this.corrOpen;
	}

	public void setCorrOpen(String corrOpen) {
		this.corrOpen = corrOpen;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHaltedStatus() {
		return this.haltedStatus;
	}

	public void setHaltedStatus(String haltedStatus) {
		this.haltedStatus = haltedStatus;
	}

	public String getMktOpen() {
		return this.mktOpen;
	}

	public void setMktOpen(String mktOpen) {
		this.mktOpen = mktOpen;
	}

	public NSEIndice[] getData() {
		return this.data;
	}

	public void setData(NSEIndice[] data) {
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPreClose() {
		return this.preClose;
	}

	public void setPreClose(String preClose) {
		this.preClose = preClose;
	}

	public String getCorrClose() {
		return this.corrClose;
	}

	public void setCorrClose(String corrClose) {
		this.corrClose = corrClose;
	}

	public String getMktClose() {
		return this.mktClose;
	}

	public void setMktClose(String mktClose) {
		this.mktClose = mktClose;
	}

	public String toString() {
		return "ClassPojo [preOpen = " + this.preOpen + " time = " + this.time + " corrOpen = " + this.corrOpen + " status = " + this.status + " haltedStatus = " + this.haltedStatus + " mktOpen = "
				+ this.mktOpen + " data = " + this.data + " code = " + this.code + " preClose = " + this.preClose + " corrClose = " + this.corrClose + " mktClose = " + this.mktClose + "]";
	}
}
