package org.stocksrin.common.model;

public class ParticipantOIModle {
	
	private String date;
	private ParticipantType participantType;
	private int futureIndexLong;
	private int futureIndexShort;
	private int futureStockLong;
	private int futureStockShort;
	private int optionIndexCallLong;
	private int optionIndexPutLong;
	private int optionIndexCallShort;
	private int optionIndexPutShort;
	private int optionStockCallLong;
	private int optionStockPutLong;
	private int optionStockCallShort;
	private int optionStockPutShort;
	private int totalLongContracts;
	private int totalShortContracts;

	public ParticipantOIModle(String date, ParticipantType participantType) {
		this.date = date;
		this.participantType = participantType;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ParticipantType getParticipantType() {
		return this.participantType;
	}

	public void setParticipantType(ParticipantType participantType) {
		this.participantType = participantType;
	}

	public int getFutureIndexLong() {
		return this.futureIndexLong;
	}

	public void setFutureIndexLong(int futureIndexLong) {
		this.futureIndexLong = futureIndexLong;
	}

	public int getFutureIndexShort() {
		return this.futureIndexShort;
	}

	public void setFutureIndexShort(int futureIndexShort) {
		this.futureIndexShort = futureIndexShort;
	}

	public int getFutureStockLong() {
		return this.futureStockLong;
	}

	public void setFutureStockLong(int futureStockLong) {
		this.futureStockLong = futureStockLong;
	}

	public int getFutureStockShort() {
		return this.futureStockShort;
	}

	public void setFutureStockShort(int futureStockShort) {
		this.futureStockShort = futureStockShort;
	}

	public int getOptionIndexCallLong() {
		return this.optionIndexCallLong;
	}

	public void setOptionIndexCallLong(int optionIndexCallLong) {
		this.optionIndexCallLong = optionIndexCallLong;
	}

	public int getOptionIndexPutLong() {
		return this.optionIndexPutLong;
	}

	public void setOptionIndexPutLong(int optionIndexPutLong) {
		this.optionIndexPutLong = optionIndexPutLong;
	}

	public int getOptionIndexCallShort() {
		return this.optionIndexCallShort;
	}

	public void setOptionIndexCallShort(int optionIndexCallShort) {
		this.optionIndexCallShort = optionIndexCallShort;
	}

	public int getOptionIndexPutShort() {
		return this.optionIndexPutShort;
	}

	public void setOptionIndexPutShort(int optionIndexPutShort) {
		this.optionIndexPutShort = optionIndexPutShort;
	}

	public int getOptionStockCallLong() {
		return this.optionStockCallLong;
	}

	public void setOptionStockCallLong(int optionStockCallLong) {
		this.optionStockCallLong = optionStockCallLong;
	}

	public int getOptionStockPutLong() {
		return this.optionStockPutLong;
	}

	public void setOptionStockPutLong(int optionStockPutLong) {
		this.optionStockPutLong = optionStockPutLong;
	}

	public int getOptionStockCallShort() {
		return this.optionStockCallShort;
	}

	public void setOptionStockCallShort(int optionStockCallShort) {
		this.optionStockCallShort = optionStockCallShort;
	}

	public int getOptionStockPutShort() {
		return this.optionStockPutShort;
	}

	public void setOptionStockPutShort(int optionStockPutShort) {
		this.optionStockPutShort = optionStockPutShort;
	}

	public int getTotalLongContracts() {
		return this.totalLongContracts;
	}

	public void setTotalLongContracts(int totalLongContracts) {
		this.totalLongContracts = totalLongContracts;
	}

	public int getTotalShortContracts() {
		return this.totalShortContracts;
	}

	public void setTotalShortContracts(int totalShortContracts) {
		this.totalShortContracts = totalShortContracts;
	}

	public String toString() {
		return "ParticipantOIModle [date=" + this.date + ", participantType=" + this.participantType + ", futureIndexLong=" + this.futureIndexLong + ", futureIndexShort=" + this.futureIndexShort
				+ ", futureStockLong=" + this.futureStockLong + ", futureStockShort=" + this.futureStockShort + ", optionIndexCallLong=" + this.optionIndexCallLong + ", optionIndexPutLong="
				+ this.optionIndexPutLong + ", optionIndexCallShort=" + this.optionIndexCallShort + ", optionIndexPutShort=" + this.optionIndexPutShort + ", optionStockCallLong="
				+ this.optionStockCallLong + ", optionStockPutLong=" + this.optionStockPutLong + ", optionStockCallShort=" + this.optionStockCallShort + ", optionStockPutShort="
				+ this.optionStockPutShort + ", totalLongContracts=" + this.totalLongContracts + ", totalShortContracts=" + this.totalShortContracts + "]";
	}
}
