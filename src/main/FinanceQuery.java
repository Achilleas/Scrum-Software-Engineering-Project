package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class FinanceQuery {
	
	private final static String DAILY_PRICE_FLAG = "snohgpv";
	
	// get daily price information in csv
	public static File getDailyPriceCSV(String symbol) {
		
		File file = FinanceQuery.requestCSVQuote(symbol, DAILY_PRICE_FLAG);
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
			        "s=" + symbol + "&f=" + properties + STATIC_PART,
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
