package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/*import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;*/
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;

/**
 * Parse the csv.
 * To get information of stocks, use methods in FinanceQuery
 *  
 * @author jiaheng
 *
 */
public class CSVParser {
	
	private Pattern pattern = Pattern.compile("(([^\"][^,]*)|\"([^\"]*)\"),?");
	
	public LinkedList<Stock> parseHistoricalCSV(File file, String symbol) {
		BufferedReader r = null;
		String line;
		LinkedList<Stock> list = new LinkedList<Stock>();
		
		try {
			r = new BufferedReader(new FileReader(file));
			r.readLine(); // skip first line
			while ((line = r.readLine()) != null) {
				String[] columns = new String[7];
				Matcher matcher = pattern.matcher(line);
				int i = 0;
				
				while(matcher.find() && i < 7)
				{
					// String without quotes
				    if(matcher.group(2) != null)
				        columns[i]=matcher.group(2).replace("\n", "");
				    // String with quotes
				    else if(matcher.group(3) != null)
				    	columns[i]=matcher.group(3).replace("\n", "");
				    i++;
				}
				Stock historicalData = toHistorical(columns, symbol);
				list.add(historicalData);
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
			while ((line = r.readLine()) != null) {
				String[] columns = new String[9];
				Matcher matcher = pattern.matcher(line);
				int i = 0;
				
				while(matcher.find() && i < 9)
				{
					// String without quotes
				    if(matcher.group(2) != null)
				        columns[i]=matcher.group(2).replace("\n", "");
				    // String with quotes
				    else if(matcher.group(3) != null)
				    	columns[i]=matcher.group(3).replace("\n", "");
				    i++;
				}
				
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

	private Stock toStock(String[] columns, LocalDate date) {
		String val;
		
		String id = columns[0].replace("\"", "");
		String name = columns[1].replace("\"", "");
		double latest = Double.parseDouble(columns[2]);
		double open = Double.parseDouble(columns[3]);
		double high = Double.parseDouble(columns[4]);
		double low = Double.parseDouble(columns[5]);
		double close = Double.parseDouble(columns[6]);
		int volume = Integer.parseInt(columns[7]);
		
		// Market Capitalization
		val = columns[8];
		double marketCap = -2;
		if (val.equalsIgnoreCase("N/A")) {
			marketCap = -1;
		} else {
			if (val.contains("B")){
				val = val.replace("B", "");	// get digits only
				marketCap = Double.parseDouble(val);
			} else if (val.contains("T")){
				val = val.replace("T", "");	// get digits only
				marketCap = Double.parseDouble(val) * 1000;
			}
		}
		return new Stock(date, id, name, latest, open, high, low, close, volume, marketCap);
	}
	
	private Stock toHistorical(String[] columns, String symbol) {

		LocalDate date = LocalDate.parse(columns[0]);
		double open = Double.parseDouble(columns[1]);
		double high = Double.parseDouble(columns[2]);
		double low = Double.parseDouble(columns[3]);
		double close = Double.parseDouble(columns[4]);
		int volume = Integer.parseInt(columns[5]);
		
		return new Stock(date, symbol, null, -1, open, high, low, close, volume, -1);
	}
}
