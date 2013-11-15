package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import org.joda.time.LocalDate;

/**
 * Parse the csv.
 * To get information of stocks, use methods in FinanceQuery
 *  
 * @author jiaheng
 *
 */
public class CSVParser {
	
	private final String separator = ",";
	
	/**
	 * 
	 * @param file	
	 * @param date	date of the information in the csv file
	 * @return	
	 */
	public LinkedList<Stock> parseLatestPriceCSV(File file, LocalDate date) {
		BufferedReader r = null;
		String line;
		LinkedList<Stock> list = new LinkedList<Stock>();
		
		try {
			r = new BufferedReader(new FileReader(file));
			//r.readLine(); // skip first line
			while ((line = r.readLine()) != null) {
				String[] columns = line.split(separator);
				System.out.println("theline is " + line);
				System.out.println(columns[0]+ " " + columns[1]+ " " + columns[2]+ " " + columns[3]);
				Stock dailyprice = toStock(columns, date);
				list.add(dailyprice);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
					System.out.println("IO exception: " + e.getMessage());
				}
			}
		}
		return list;
	}

	private Stock toStock(String[] price_info, LocalDate date) {
		String name = price_info[0];
		String id = price_info[1];
		double latest = Double.parseDouble(price_info[2]);
		double open = Double.parseDouble(price_info[3]);
		double high = Double.parseDouble(price_info[4]);
		double low = Double.parseDouble(price_info[5]);
		double close = Double.parseDouble(price_info[6]);
		int volume = Integer.parseInt(price_info[7]);
		
		// Market Capitalization
		String val = price_info[8];
		double marketCap;
		if (val.equalsIgnoreCase("N/A")) {
			marketCap = -1;
		} else {
			val = val.replaceAll("\\D+","");	// get digits only
			marketCap = Double.parseDouble(val);
		}
		return new Stock(date, id, name, latest, open, high, low, close, volume, marketCap);
	}
}
