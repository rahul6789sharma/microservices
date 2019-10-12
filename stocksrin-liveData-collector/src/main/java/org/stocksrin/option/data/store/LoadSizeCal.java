package org.stocksrin.option.data.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoadSizeCal {

	public static void main(String[] args) throws Exception {
		ReadData.read();
		System.out.println(ReadData.data.keySet());
		try {

			File f = new File("C:\\Users\\sharmarah\\stocksRin_CONF2\\OptionData\\Stocks\\FNOLOT.txt");

			BufferedReader b = new BufferedReader(new FileReader(f));

			String readLine = "";

			System.out.println("Reading file using Buffered Reader");

			List<StockLot> lotSize = new ArrayList<>();
			while ((readLine = b.readLine()) != null) {

				// System.out.println(readLine.substring(0, 11).trim()+ " -> " +
				// readLine.substring(11, readLine.length()).trim());
				// System.out.println();
				if (ReadData.data.containsKey(readLine.substring(0, 11).trim())) {
					System.out.println(readLine.substring(0, 11).trim() + "-"
							+ ReadData.data.get(readLine.substring(0, 11).trim()).get(0).getClosePrice());

					String symbole = readLine.substring(0, 11).trim();
					String lotsizer = readLine.substring(11, readLine.length()).trim();
					double price = ReadData.data.get(readLine.substring(0, 11).trim()).get(0).getClosePrice();
					double lotValue = Integer.parseInt(readLine.substring(11, readLine.length()).trim()) * price;
					// System.out.println(lotValue);
					StockLot stockLot = new StockLot(symbole, Integer.parseInt(lotsizer), price, lotValue);
					lotSize.add(stockLot);
				}
				System.out.println("**********");

			}
			
			lotSize.sort((o1, o2) -> o1.getLotvalue().compareTo(o2.getLotvalue()));
			lotSize.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}



class StockLot {

	String symbole;
	int lotsize;
	double price;
	Double lotvalue;

	public StockLot(String symbole, int lotsize, double price, Double lotvalue) {
		super();
		this.symbole = symbole;
		this.lotsize = lotsize;
		this.price = price;
		this.lotvalue = lotvalue;
	}

	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	public int getLotsize() {
		return lotsize;
	}

	public void setLotsize(int lotsize) {
		this.lotsize = lotsize;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Double getLotvalue() {
		return lotvalue;
	}

	public void setLotvalue(Double lotvalue) {
		this.lotvalue = lotvalue;
	}

	@Override
	public String toString() {
		return "StockLot [symbole=" + symbole + ", lotsize=" + lotsize + ", price=" + price + ", lotvalue=" + lotvalue
				+ "]";
	}

}