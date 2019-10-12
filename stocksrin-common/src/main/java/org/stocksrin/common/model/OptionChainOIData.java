package org.stocksrin.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.stocksrin.common.utils.CommonUtils;

public class OptionChainOIData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String expiry;
	private String symbole;
	private Double spot;
	private Double iv;

	private Integer total_CE_OI;
	private Integer total_PE_OI;
	private Integer total_CE_OI_Change;
	private Integer total_PE_OI_change;

	private Entry<Integer, Integer> higest_CE_OI_1;
	private Entry<Integer, Integer> higest_CE_OI_2;
	private Entry<Integer, Integer> higest_PE_OI_1;
	private Entry<Integer, Integer> higest_PE_OI_2;

	private Entry<Integer, Integer> higest_CE_OI_1_change;
	private Entry<Integer, Integer> higest_CE_OI_2_change;
	private Entry<Integer, Integer> higest_PE_OI_1_change;
	private Entry<Integer, Integer> higest_PE_OI_2_change;

	private Integer avg_CE_OTM_premium;
	private Integer avg_PE_OTM_premium;

	public OptionChainOIData() {
	}

	public OptionChainOIData(OptionChainOIDataBuilder optionChainOIDataBuilder) {
		this.date = optionChainOIDataBuilder.date;
		this.expiry = optionChainOIDataBuilder.expiry;
		this.symbole = optionChainOIDataBuilder.symbole;
		this.expiry = optionChainOIDataBuilder.expiry;
		this.spot = optionChainOIDataBuilder.spot;
		this.iv = optionChainOIDataBuilder.iv;

		this.total_CE_OI = optionChainOIDataBuilder.total_CE_OI;
		this.total_PE_OI = optionChainOIDataBuilder.total_PE_OI;
		this.total_CE_OI_Change = optionChainOIDataBuilder.total_CE_OI_Change;
		this.total_PE_OI_change = optionChainOIDataBuilder.total_PE_OI_change;

		this.higest_CE_OI_1 = optionChainOIDataBuilder.higest_CE_OI_1;
		this.higest_CE_OI_2 = optionChainOIDataBuilder.higest_CE_OI_2;
		this.higest_PE_OI_1 = optionChainOIDataBuilder.higest_PE_OI_1;
		this.higest_PE_OI_2 = optionChainOIDataBuilder.higest_PE_OI_2;

		this.higest_CE_OI_1_change = optionChainOIDataBuilder.higest_CE_OI_1_change;
		this.higest_CE_OI_2_change = optionChainOIDataBuilder.higest_CE_OI_2_change;
		this.higest_PE_OI_1_change = optionChainOIDataBuilder.higest_PE_OI_1_change;
		this.higest_PE_OI_2_change = optionChainOIDataBuilder.higest_PE_OI_2_change;

		this.avg_CE_OTM_premium = optionChainOIDataBuilder.avg_CE_OTM_premium;
		this.avg_PE_OTM_premium = optionChainOIDataBuilder.avg_PE_OTM_premium;

	}

	public String getDate() {
		return date;
	}

	public String getExpiry() {
		return expiry;
	}

	public String getSymbole() {
		return symbole;
	}

	public Double getSpot() {
		return spot;
	}

	public Double getIv() {
		return iv;
	}

	public Integer getTotal_CE_OI() {
		return total_CE_OI;
	}

	public Integer getTotal_PE_OI() {
		return total_PE_OI;
	}

	public Integer getTotal_CE_OI_Change() {
		return total_CE_OI_Change;
	}

	public Integer getTotal_PE_OI_change() {
		return total_PE_OI_change;
	}

	public Entry<Integer, Integer> getHigest_CE_OI_1() {
		return higest_CE_OI_1;
	}

	public Entry<Integer, Integer> getHigest_CE_OI_2() {
		return higest_CE_OI_2;
	}

	public Entry<Integer, Integer> getHigest_PE_OI_1() {
		return higest_PE_OI_1;
	}

	public Entry<Integer, Integer> getHigest_PE_OI_2() {
		return higest_PE_OI_2;
	}

	public Entry<Integer, Integer> getHigest_CE_OI_1_change() {
		return higest_CE_OI_1_change;
	}

	public Entry<Integer, Integer> getHigest_CE_OI_2_change() {
		return higest_CE_OI_2_change;
	}

	public Entry<Integer, Integer> getHigest_PE_OI_1_change() {
		return higest_PE_OI_1_change;
	}

	public Entry<Integer, Integer> getHigest_PE_OI_2_change() {
		return higest_PE_OI_2_change;
	}

	public Integer getAvg_CE_OTM_premium() {
		return avg_CE_OTM_premium;
	}

	public Integer getAvg_PE_OTM_premium() {
		return avg_PE_OTM_premium;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public void setSpot(Double spot) {
		this.spot = spot;
	}

	public void setIv(Double iv) {
		this.iv = iv;
	}

	public void setTotal_CE_OI(Integer total_CE_OI) {
		this.total_CE_OI = total_CE_OI;
	}

	public void setTotal_PE_OI(Integer total_PE_OI) {
		this.total_PE_OI = total_PE_OI;
	}

	public void setTotal_CE_OI_Change(Integer total_CE_OI_Change) {
		this.total_CE_OI_Change = total_CE_OI_Change;
	}

	public void setTotal_PE_OI_change(Integer total_PE_OI_change) {
		this.total_PE_OI_change = total_PE_OI_change;
	}

	public void setHigest_CE_OI_1(Entry<Integer, Integer> higest_CE_OI_1) {
		this.higest_CE_OI_1 = higest_CE_OI_1;
	}

	public void setHigest_CE_OI_2(Entry<Integer, Integer> higest_CE_OI_2) {
		this.higest_CE_OI_2 = higest_CE_OI_2;
	}

	public void setHigest_PE_OI_1(Entry<Integer, Integer> higest_PE_OI_1) {
		this.higest_PE_OI_1 = higest_PE_OI_1;
	}

	public void setHigest_PE_OI_2(Entry<Integer, Integer> higest_PE_OI_2) {
		this.higest_PE_OI_2 = higest_PE_OI_2;
	}

	public void setHigest_CE_OI_1_change(Entry<Integer, Integer> higest_CE_OI_1_change) {
		this.higest_CE_OI_1_change = higest_CE_OI_1_change;
	}

	public void setHigest_CE_OI_2_change(Entry<Integer, Integer> higest_CE_OI_2_change) {
		this.higest_CE_OI_2_change = higest_CE_OI_2_change;
	}

	public void setHigest_PE_OI_1_change(Entry<Integer, Integer> higest_PE_OI_1_change) {
		this.higest_PE_OI_1_change = higest_PE_OI_1_change;
	}

	public void setHigest_PE_OI_2_change(Entry<Integer, Integer> higest_PE_OI_2_change) {
		this.higest_PE_OI_2_change = higest_PE_OI_2_change;
	}

	public void setAvg_CE_OTM_premium(Integer avg_CE_OTM_premium) {
		this.avg_CE_OTM_premium = avg_CE_OTM_premium;
	}

	public void setAvg_PE_OTM_premium(Integer avg_PE_OTM_premium) {
		this.avg_PE_OTM_premium = avg_PE_OTM_premium;
	}

	public String toCSV() {
		return date + "," + expiry + "," + symbole + "," + spot + "," + iv + "," + total_CE_OI + "," + total_PE_OI + ","
				+ total_CE_OI_Change + "," + total_PE_OI_change + "," + higest_CE_OI_1 + "," + higest_CE_OI_2 + ","
				+ higest_PE_OI_1 + "," + higest_PE_OI_2 + "," + higest_CE_OI_1_change + "," + higest_CE_OI_2_change
				+ "," + higest_PE_OI_1_change + "," + higest_PE_OI_2_change + "," + avg_CE_OTM_premium + ","
				+ avg_PE_OTM_premium;
	}

	@Override
	public String toString() {
		return "OptionChainOIData [date=" + date + ", expiry=" + expiry + ", symbole=" + symbole + ", spot=" + spot
				+ ", iv=" + iv + ", total_CE_OI=" + total_CE_OI + ", total_PE_OI=" + total_PE_OI
				+ ", total_CE_OI_Change=" + total_CE_OI_Change + ", total_PE_OI_change=" + total_PE_OI_change
				+ ", higest_CE_OI_1=" + higest_CE_OI_1 + ", higest_CE_OI_2=" + higest_CE_OI_2 + ", higest_PE_OI_1="
				+ higest_PE_OI_1 + ", higest_PE_OI_2=" + higest_PE_OI_2 + ", higest_CE_OI_1_change="
				+ higest_CE_OI_1_change + ", higest_CE_OI_2_change=" + higest_CE_OI_2_change
				+ ", higest_PE_OI_1_change=" + higest_PE_OI_1_change + ", higest_PE_OI_2_change="
				+ higest_PE_OI_2_change + ", avg_CE_OTM_premium=" + avg_CE_OTM_premium + ", avg_PE_OTM_premium="
				+ avg_PE_OTM_premium + "]";
	}

	public static List<OptionChainOIData> getData(String csvFilePath) {
		List<OptionChainOIData> data = new ArrayList<>();
		List<String[]> lst = CommonUtils.getCSVData(csvFilePath);
		for (String[] strings : lst) {
			data.add(getData(strings));
		}
		return data;
	}

	private static OptionChainOIData getData(String[] data) {
		OptionChainOIData optionChainOIData = new OptionChainOIData.OptionChainOIDataBuilder().setDate(data[0])
				.setExpiry(data[1]).setSymbole(data[2]).setSpot(Double.parseDouble(data[3]))
				.setIv(Double.parseDouble(data[4])).setTotal_CE_OI(Integer.parseInt(data[5]))
				.setTotal_PE_OI(Integer.parseInt(data[6])).setTotal_CE_OI_Change(Integer.parseInt(data[7]))
				.setTotal_PE_OI_change(Integer.parseInt(data[8])).setHigest_CE_OI_1(data[9]).setHigest_CE_OI_2(data[10])
				.setHigest_PE_OI_1(data[11]).setHigest_PE_OI_2(data[12])

				.setHigest_CE_OI_1_change(data[13]).setHigest_CE_OI_2_change(data[14])
				.setHigest_PE_OI_1_change(data[15]).setHigest_PE_OI_2_change(data[16])

				.setAvg_CE_OTM_premium(Integer.parseInt(data[17])).setAvg_PE_OTM_premium(Integer.parseInt(data[18]))

				.build();

		return optionChainOIData;
	}

	public static class OptionChainOIDataBuilder {

		private String date;
		private String expiry;
		private String symbole;
		private Double spot;
		private Double iv;

		private Integer total_CE_OI;
		private Integer total_PE_OI;
		private Integer total_CE_OI_Change;
		private Integer total_PE_OI_change;

		private Entry<Integer, Integer> higest_CE_OI_1;
		private Entry<Integer, Integer> higest_CE_OI_2;
		private Entry<Integer, Integer> higest_PE_OI_1;
		private Entry<Integer, Integer> higest_PE_OI_2;

		private Entry<Integer, Integer> higest_CE_OI_1_change;
		private Entry<Integer, Integer> higest_CE_OI_2_change;
		private Entry<Integer, Integer> higest_PE_OI_1_change;
		private Entry<Integer, Integer> higest_PE_OI_2_change;

		private Integer avg_CE_OTM_premium;
		private Integer avg_PE_OTM_premium;

		// private final OptionChainOIData optionChainOIData = new OptionChainOIData();

		public OptionChainOIDataBuilder setDate(String date) {
			this.date = date;
			return this;
		}

		public OptionChainOIDataBuilder setExpiry(String expiry) {
			this.expiry = expiry;
			return this;
		}

		public OptionChainOIDataBuilder setSymbole(String symbole) {
			this.symbole = symbole;
			return this;
		}

		public OptionChainOIDataBuilder setSpot(Double spot) {
			this.spot = spot;
			return this;
		}

		public OptionChainOIDataBuilder setIv(Double iv) {
			this.iv = iv;
			return this;
		}

		public OptionChainOIDataBuilder setTotal_CE_OI(Integer total_CE_OI) {
			this.total_CE_OI = total_CE_OI;
			return this;
		}

		public OptionChainOIDataBuilder setTotal_PE_OI(Integer total_PE_OI) {
			this.total_PE_OI = total_PE_OI;
			return this;
		}

		public OptionChainOIDataBuilder setTotal_CE_OI_Change(Integer total_CE_OI_Change) {
			this.total_CE_OI_Change = total_CE_OI_Change;
			return this;
		}

		public OptionChainOIDataBuilder setTotal_PE_OI_change(Integer total_PE_OI_change) {
			this.total_PE_OI_change = total_PE_OI_change;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_CE_OI_1(String data) {
			String[] a = data.split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));

			Entry<Integer, Integer> map = new Entry<>(strike, value);
			this.higest_CE_OI_1 = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_CE_OI_2(String data) {
			String[] a = data.trim().split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));

			Entry<Integer, Integer> map = new Entry<>(strike, value);
			this.higest_CE_OI_2 = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_PE_OI_1(String data) {
			String[] a = data.trim().split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));
			Entry<Integer, Integer> map = new Entry<>(strike, value);

			this.higest_PE_OI_1 = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_PE_OI_2(String data) {
			String[] a = data.split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));
			Entry<Integer, Integer> map = new Entry<>(strike, value);

			this.higest_PE_OI_2 = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_CE_OI_1_change(String data) {
			String[] a = data.split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));
			Entry<Integer, Integer> map = new Entry<>(strike, value);
			this.higest_CE_OI_1_change = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_CE_OI_2_change(String data) {
			String[] a = data.split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));
			Entry<Integer, Integer> map = new Entry<>(strike, value);
			this.higest_CE_OI_2_change = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_PE_OI_1_change(String data) {
			String[] a = data.split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));
			Entry<Integer, Integer> map = new Entry<>(strike, value);
			this.higest_PE_OI_1_change = map;
			return this;
		}

		public OptionChainOIDataBuilder setHigest_PE_OI_2_change(String data) {
			String[] a = data.split("-");
			Integer strike = (int) Double.parseDouble(a[0].substring(1, a[0].length()));
			Integer value = (int) Double.parseDouble(a[1].substring(0, a[1].length() - 1));
			Entry<Integer, Integer> map = new Entry<>(strike, value);
			this.higest_PE_OI_2_change = map;
			return this;
		}

		public OptionChainOIDataBuilder setAvg_CE_OTM_premium(Integer avg_CE_OTM_premium) {
			this.avg_CE_OTM_premium = avg_CE_OTM_premium;
			return this;
		}

		public OptionChainOIDataBuilder setAvg_PE_OTM_premium(Integer avg_PE_OTM_premium) {
			this.avg_PE_OTM_premium = avg_PE_OTM_premium;
			return this;
		}

		// Return the finally consrcuted User object
		public OptionChainOIData build() {
			OptionChainOIData optionChainOIData = new OptionChainOIData(this);

			return optionChainOIData;
		}
	}
}
