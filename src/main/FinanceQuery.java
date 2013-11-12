package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import static main.Constants.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
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

	private final static String DAILY_PRICE_PROP = "snohgpv";

	/**
	 * 
	 * get daily pricing information of stock(s) To get multiple stock, put
	 * symbols into a string and separate them with "," the fields are: StockID,
	 * Name, Open, High, Low, Previous Close, Volume
	 * 
	 * @param symbol
	 * @return
	 */
	public File getDailyPriceCSV(String symbol) {
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
	public File getHistoricalCVS(String symbol, LocalDate fromDate, LocalDate toDate,
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

	// get all component of the stock market index into a string separated with
	// ","
	public String getComponents(String index) {

		String components = "";

		try {
			index = URLEncoder.encode(index, "ISO-8859-1");
			UserAgent userAgent = new UserAgent();
			userAgent.visit("http://uk.finance.yahoo.com/q/cp?s=" + index);
			Element element;
			Elements elements;

			element = userAgent.doc.findFirst("<div align=right>")
					.getElement(2);
			String last_url = element.getAt("href");
			int last_index = Integer.parseInt(last_url.substring(last_url
					.length() - 1)); // get the last index
			int i = 0;
			while (i <= last_index) { // for each page
				userAgent = new UserAgent();
				userAgent.visit("http://uk.finance.yahoo.com/q/cp?s=" + index
						+ "&c=" + i);
				// find the table containing the component of the stock market
				// index
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
						components = components.equals("") ? textNode
								: components + "," + textNode;
					}
				}
				i++;
			}
		} catch (JauntException e) {
			System.err.println(e);
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.err
					.println("Something wrong with the encoding format. Exit!");
			System.exit(1);
		}

		return components;
	}

	private File requestCSVHistorical(String symbol, LocalDate fromDate,
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
			e.printStackTrace();
			System.err.println("Failed to get the file from the url");
			return null;
		}
		return file;
	}

	private File requestCSVQuote(String symbol, String properties) {

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
			e.printStackTrace();
			System.err.println("Failed to get the file from the url");
			return null;
		}
		return file;
	}
}
