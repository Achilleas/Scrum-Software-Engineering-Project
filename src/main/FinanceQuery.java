package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.TreeSet;

import static main.Constants.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import com.jaunt.*;

/**
 * 
 * @author jiaheng 
 * 
 * 12/11/2013 UPDATE
 * - Use LocalDate instead of Date
 * 
 * Provide method to get financial information from Yahoo Finance
 * 
 */
public class FinanceQuery {

	// id,name,latestValue,open,high,low,closing,volume,marketCap
	private final static String DAILY_PRICE_PROP = "snl1ohgpvj1";
	private static LocalDate lastUpdate;
	private static NavigableSet<String> ftseList;
	
	public static void initialise() {
		System.out.println("getting FTSE100 list");
		System.out.println("Please wait...");
		lastUpdate = null;
		ftseList = getComponentsList(FTSE100);
		System.out.println("done");
	}
	
	/**
	 * 
	 * get historical price information for a given stock
	 * you are not allowed to download historical quotes of several stocks or indices
	 * the price information are arranged from latest to oldest
	 * 
	 * Note:	Not all data will be available, Name will be NULL
	 * 			latestValue and market Capitalization will be -1
	 * 	The getClose() from stock class will retrieve close price of that date
	 * 
	 * @param symbol
	 * @return LinkedList of price information for a given stock
	 */
	public static LinkedList<Stock> getHistorical(String symbol, LocalDate fromDate, LocalDate toDate,
			String interval) {
		File file;
		CSVParser parser = new CSVParser();
		
		Validate.notNull(symbol, "symbol can't be null");
		if (symbol.contains(","))
			throw new IllegalArgumentException("only one stock can be retrieved");
		Validate.notNull(fromDate, "fromDate can't be null");
		Validate.notNull(toDate, "toDate can't be null");
		Validate.notNull(interval, "interval can't be null");

		if (fromDate.isAfter(toDate)
				|| fromDate.isEqual(toDate))
			throw new IllegalArgumentException("fromDate must before toDate");

		LocalDate today = new LocalDate();
		if (toDate.isAfter(today))
			throw new IllegalArgumentException(
					"toDate can't be later than today");

		if (!interval.equals(DAILY_INTERVAL)
				&& !interval.equals(WEEKLY_INTERVAL)
				&& !interval.equals(MONTHLY_INTERVAL))
			throw new IllegalArgumentException();

		file = requestCSVHistorical(symbol, fromDate, toDate, interval);
		return (file != null) ? parser.parseHistoricalCSV(file, symbol) : null;
	}
	
	/**
	 * 
	 * get daily pricing information of stock(s) To get multiple stock, put
	 * symbols into a string and separate them with "," the fields are: 
	 * StockID, Name, latestValue, Open, High, Low, Previous Close, Volume, MarketCap
	 * 
	 * Note: MarketCapitalizationwill be -1 if unavailable
	 * 		The getClose() from stock class will retrieve previous close
	 * 
	 * @param symbol
	 * @return	linkedList of Stock
	 */
	public static LinkedList<Stock> getLatestPrice(String symbol) {
		
		File file;
		CSVParser parser = new CSVParser();
		
		Validate.notNull(symbol, "symbol can't be null");
		
		file = requestCSVQuote(symbol, DAILY_PRICE_PROP);
		return parser.parseLatestPriceCSV(file, LocalDate.now());
	}
	
	/**
	 * 
	 * get daily pricing information of stock(s) To get multiple stock, put
	 * symbols into a string and separate them with "," the fields are: StockID,
	 * Name, Open, High, Low, Previous Close, Volume
	 * 
	 * @param symbol
	 * @return
	 */
	public static File getLatestPriceCSV(String symbol) {
		Validate.notNull(symbol, "symbol can't be null");

		return requestCSVQuote(symbol, DAILY_PRICE_PROP);
	}
	
	/**
	 * 
	 * get historical price for a given share in csv file you are not allowed to
	 * download historical quotes of several stocks or indices Interval of the
	 * trading period. use DAILY_INTERVAL, WEEKLY_INTERVAL
	 * 
	 * @param symbol
	 * @param fromDate
	 * @param toDate
	 * @param interval
	 * @return
	 */
	public static File getHistoricalCVS(String symbol, LocalDate fromDate, LocalDate toDate,
			String interval){
		Validate.notNull(symbol, "symbol can't be null");
		Validate.notNull(fromDate, "fromDate can't be null");
		Validate.notNull(toDate, "toDate can't be null");
		Validate.notNull(interval, "interval can't be null");

		if (fromDate.isAfter(toDate)
				|| fromDate.isEqual(toDate))
			throw new IllegalArgumentException("fromDate must before toDate");

		LocalDate today = new LocalDate();
		if (toDate.isAfter(today))
			throw new IllegalArgumentException(
					"toDate can't be later than today");

		if (!interval.equals(DAILY_INTERVAL)
				&& !interval.equals(WEEKLY_INTERVAL)
				&& !interval.equals(MONTHLY_INTERVAL))
			throw new IllegalArgumentException();

		return requestCSVHistorical(symbol, fromDate, toDate, interval);
	}

	/**
	 * get all component of the stock market index
	 * and return them in a set
	 * 
	 */
	public static NavigableSet<String> getComponentsList(String index) {

		// if FTSE is required retrieve the FTSE list from local memory
		if (index.equals(FTSE100)) {
			// if the class is first initiated
			if (lastUpdate == null) {
				ftseList = getComponentsFromWeb(FTSE100);
				lastUpdate = new LocalDate();
				return ftseList;
			}
			
			Duration duration = new Duration(lastUpdate.toDateTimeAtCurrentTime(), null);
			// update list if last update is more than a day
			if (duration.getStandardDays() <= 1) {
				return ftseList;
			} else {
				ftseList = getComponentsFromWeb(FTSE100);
				lastUpdate = new LocalDate();
				return ftseList;
			}
		} else {
			return getComponentsFromWeb(index);
		}
	}
	
	/**
	 * get all component of the stock market index into 
	 * a String separated by ","
	 * 
	 */
	public static String getComponents(String index) {

		NavigableSet<String> set = getComponentsList(index);
		Iterator<String> iterator = set.iterator();
		
		String components = (iterator.hasNext())? iterator.next() : "";
		
		while(iterator.hasNext()) {
			components += "," + iterator.next();
		}
		return components;
	}

	private static NavigableSet<String> getComponentsFromWeb(String index) {
		
		NavigableSet<String> set = new TreeSet<String>();
		
		try {
			index = URLEncoder.encode(index, "ISO-8859-1");
			UserAgent userAgent = new UserAgent();
			userAgent.visit("http://uk.finance.yahoo.com/q/cp?s=" + index);
			Element element;
			Elements elements;

			element = userAgent.doc.findFirst("<div align=right>") .getElement(2);
			String last_url = element.getAt("href");
			// get the last index
			int last_index = Integer.parseInt(last_url.substring(last_url.length() - 1)); 
			int i = 0;
			while (i <= last_index) { // for each page
				userAgent = new UserAgent();
				userAgent.visit("http://uk.finance.yahoo.com/q/cp?s=" + index
						+ "&c=" + i);
				// find the table containing the component of the stock market index
				element = userAgent.doc.findFirst("<table class=yfnc_tableout1>");
				element = element.getElement(0).getElement(0).getElement(0);
				// iterate each row of the table
				elements = element.findEach("<tr>");
				for (Element tr : elements) {
					String textNode;

					element = tr.getElement(0);
					textNode = element.getText();
					if (!textNode.equals("Symbol")) { // filter out the headings
						element = element.getElement(0).getElement(0);
						textNode = element.getText();
						set.add(textNode);
					}
				}
				i++;
			}
		} catch (JauntException e) {
			System.err.println(e);
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.err.println("Something wrong with the encoding format. Exit!");
			System.exit(1);
		}
		return set;
	}
	
	private static File requestCSVHistorical(String symbol, LocalDate fromDate,
			LocalDate toDate, String interval) {

		final String STATIC_PART = "&ignore=.csv";

		String request = "";
		URI uri;
		URL url = null;
		File file = null;

		try {
			uri = new URI("http", "ichart.yahoo.com", "/table.csv", 
					"s=" + symbol + 
					"&a=" + (fromDate.getMonthOfYear() - 1) + 
					"&b=" + fromDate.getDayOfMonth() + 
					"&c=" + fromDate.getYear() + 
					"&d=" + (toDate.getMonthOfYear() - 1) + 
					"&e=" + toDate.getDayOfMonth() + 
					"&f=" + toDate.getYear() + 
					"&g=" + interval + STATIC_PART, null);
			request = uri.toASCIIString();
			url = new URL(request);

			file = File.createTempFile("historical", null);
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
			return null;
		}
		return file;
	}

	private static File requestCSVQuote(String symbol, String properties) {

		final String STATIC_PART = "&e=.csv";

		String request = "";
		URI uri;
		URL url = null;
		File file = null;

		try {
			uri = new URI("http", "download.finance.yahoo.com",
					"/d/quotes.csv", 
					"s=" + symbol + 
					"&f=" + properties + STATIC_PART, null);
			request = uri.toASCIIString();

			url = new URL(request);

			file = File.createTempFile("components", null);
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
			System.err.println("Failed to get the file from the url");
			return null;
		}
		return file;
	}
	
	public static NavigableSet<String> getFtseList(){
		return FinanceQuery.ftseList;
	}
}
