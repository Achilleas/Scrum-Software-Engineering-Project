package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import static main.Constants.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;

import com.jaunt.*;

public class FinanceQuery {
	
	private final static String DAILY_PRICE_PROP = "snohgpv";
	
	// get daily price information in csv
	public static File getDailyPriceCSV(String symbol) {
		
		Validate.notNull(symbol, "symbol can't be null");
		
		File file = FinanceQuery.requestCSVQuote(symbol, DAILY_PRICE_PROP);
		return file;
	}
	
	// get Historial price for a given share
	public static File getHistoricalCVS(String symbol, Date fromDate, Date toDate, String interval) {
		
		Validate.notNull(symbol, "symbol can't be null");
		Validate.notNull(fromDate, "fromDate can't be null");
		Validate.notNull(toDate, "toDate can't be null");
		Validate.notNull(interval, "interval can't be null");
		
		LocalDate toLocalDate = new LocalDate(toDate);
		LocalDate fromLocalDate = new LocalDate(fromDate);
		
		if (fromLocalDate.isAfter(toLocalDate) || fromLocalDate.isEqual(toLocalDate))
			throw new IllegalArgumentException("fromDate must before toDate");
		
		LocalDate today = new LocalDate();
		if (toLocalDate.isAfter(today))
			throw new IllegalArgumentException("toDate can't be later than today");
		
		if (!interval.equals(DAILY_INTERVAL) && !interval.equals(WEEKLY_INTERVAL) && !interval.equals(MONTHLY_INTERVAL))
			throw new IllegalArgumentException();
		
		File file = FinanceQuery.requestCSVHistorical(symbol, fromLocalDate, toLocalDate, interval);
		return file;
	}
	
	public static String getComponents(String symbol) {
		
		String components = "";
		
		try{
			symbol = URLEncoder.encode(symbol, "ISO-8859-1");
			UserAgent userAgent = new UserAgent();
			userAgent.visit("http://uk.finance.yahoo.com/q/cp?s=" + symbol);
			Element element;
			Elements elements;
			
			element = userAgent.doc.findFirst("<div align=\"right\">").getElement(2);
			String last_url = element.getAt("href");
			int last_index = Integer.parseInt(last_url.substring(last_url.length() - 1)); // get the last index
			int i = 0;
			while (i <= last_index) {
				userAgent = new UserAgent();
				userAgent.visit("http://uk.finance.yahoo.com/q/cp?s=" + symbol + "&c=" + i);
				element = userAgent.doc.findFirst("<div align=\"right\">").nextSiblingElement().nextSiblingElement();
				element = element.getElement(0).getElement(0).getElement(0);
				elements = element.findEach("<tr>");
				for(Element tr : elements){                                     //iterate through Results
					String textNode;
					
					element = tr.getElement(0);
					textNode = element.getText();
					if (!textNode.equals("Symbol")) {
						element = element.getElement(0).getElement(0);
						textNode = element.getText();
						components = components.equals("")? textNode : components + "," + textNode;
					}
				} 
				i++;
			}
		}
		catch(JauntException e){
			  System.err.println(e);
			  return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.err.println("Something wrong with the encoding format. Exit!");
			System.exit(1);
		}
		
		return components;
	}
	
	private static File requestCSVHistorical(String symbol, LocalDate fromDate, LocalDate toDate, String interval) {
		
		final String STATIC_PART = "&ignore=.csv";

		String request = "";
		URI uri;
		URL url = null;
		File file = null;
	    
		try {
			uri = new URI(
			        "http", 
			        "ichart.yahoo.com", 
			        "/table.csv",
			        "s=" + symbol + 
			        "&a=" + (fromDate.getMonthOfYear() - 1) +
			        "&b=" + fromDate.getDayOfMonth() +
			        "&c=" + fromDate.getYear() +
			        "&d=" + (toDate.getMonthOfYear() - 1) +
			        "&e=" + toDate.getDayOfMonth() +
			        "&f=" + toDate.getYear() +
			        "&g=" + interval +
			        STATIC_PART,
			        null);
			request = uri.toASCIIString();
			url = new URL(request);
			
			file = File.createTempFile("historical", null);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.err.println("Syntax error in the url");
			System.exit(1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to create a temp file");
			return null;
		}
		
		try {
			FileUtils.copyURLToFile(url, file);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to get the file from the url");
			return null;
		}
		file.deleteOnExit();
		
		return file;
	}
	
	private static File requestCSVQuote(String symbol, String properties) {
		
		final String STATIC_PART = "&e=.csv";
		
		String request = "";
		URI uri;
		URL url = null;
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
			
			url = new URL(request);
			
			file = File.createTempFile("components", null);
			FileUtils.copyURLToFile(url, file);
			file.deleteOnExit();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.err.println("Syntax error in the url");
			System.exit(1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to create a temp file");
			return null;
		}
		
		try {
			FileUtils.copyURLToFile(url, file);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to get the file from the url");
			return null;
		}
		file.deleteOnExit();
		
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
