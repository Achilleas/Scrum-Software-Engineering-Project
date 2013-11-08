package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static main.Constants.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;

public class FinanceQuery {
	
	private final static String DAILY_PRICE_PROP = "snohgpv";
	
	// get daily price information in csv
	public static File getDailyPriceCSV(String symbol) {
		
		Validate.notNull(symbol, "symbol can't be null");
		
		File file = FinanceQuery.requestCSVQuote(symbol, DAILY_PRICE_PROP);
		return file;
		
	}
	
	public static File getHistoricalCVS(String symbol, Date fromDate, Date toDate, String interval) {
		
		Validate.notNull(symbol, "symbol can't be null");
		Validate.notNull(fromDate, "fromDate can't be null");
		Validate.notNull(toDate, "toDate can't be null");
		Validate.notNull(interval, "interval can't be null");
		
		if (fromDate.compareTo(toDate) >= 0)
			throw new IllegalArgumentException("fromDate must before toDate");
		
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		today = cal.getTime();
		if (today.compareTo(toDate) <= 0)
			throw new IllegalArgumentException("toDate can't be later than today");
		
		if (!interval.equals(DAILY_INTERVAL) && !interval.equals(WEEKLY_INTERVAL) && !interval.equals(MONTHLY_INTERVAL))
			throw new IllegalArgumentException();
		
		File file = FinanceQuery.requestCSVHistorical(symbol, fromDate, toDate, interval);
		return file;
	}
	
	private static File requestCSVHistorical(String symbol, Date fromDate, Date toDate, String interval) {
		
		final String STATIC_PART = "&ignore=.csv";

		String request = "";
		URI uri;
		File file = null;
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(fromDate);
	    int fromYear = cal.get(Calendar.YEAR);
	    int fromMonth = cal.get(Calendar.MONTH);
	    int fromDay = cal.get(Calendar.DAY_OF_MONTH);
	    cal.setTime(toDate);
	    int toYear = cal.get(Calendar.YEAR);
	    int toMonth = cal.get(Calendar.MONTH);
	    int toDay = cal.get(Calendar.DAY_OF_MONTH);
	    
		try {
			uri = new URI(
			        "http", 
			        "ichart.yahoo.com", 
			        "/table.csv",
			        "s=" + symbol + 
			        "&a=" + fromMonth +
			        "&b=" + fromDay +
			        "&c=" + fromYear +
			        "&d=" + toMonth +
			        "&e=" + toDay +
			        "&f=" + toYear +
			        "&g=" + interval +
			        STATIC_PART,
			        null);
			request = uri.toASCIIString();
			URL url = new URL(request);
			
			file = File.createTempFile("historical", null);
			FileUtils.copyURLToFile(url, file);
			file.deleteOnExit();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
	
	private static File requestCSVQuote(String symbol, String properties) {
		
		final String STATIC_PART = "&e=.csv";
		
		String request = "";
		URI uri;
		File file = null;
		
		try {
			uri = new URI(
			        "http", 
			        "download.finance.yahoo.com", 
			        "/d/quotes.csv",
			        "s=" + symbol + 
			        "&f=" + properties +
			        STATIC_PART,
			        null);
			request = uri.toASCIIString();
			
			URL url = new URL(request);
			
			file = File.createTempFile("components", null);
			FileUtils.copyURLToFile(url, file);
			file.deleteOnExit();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
	
	public ArrayList<String> parseComponentCSV(File inputFile) {
		 
		BufferedReader br = null;
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		
		try {
	 
			br = new BufferedReader(new FileReader(inputFile));
			while ((line = br.readLine()) != null) {

			    // use comma as separator
				String symbol = line.replace("\"","");
				list.add(symbol);
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
}
