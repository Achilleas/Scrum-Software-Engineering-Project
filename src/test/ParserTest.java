package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

import main.CSVParser;
import main.Stock;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {

	static LinkedList<Stock> stocks, historical;
	static LocalDate date;
	
	@BeforeClass
	public static void setupBeforeClass() {

		date = LocalDate.now();
		CSVParser parser = new CSVParser();
		File file1 = new File("parser-test.csv");
		File file2 = new File("historical-test.csv");
		
		stocks = parser.parseLatestPriceCSV(file1, date);
		historical = parser.parseHistoricalCSV(file2, "unknown");
	}
	
	@Test
	public void listSize() {
		assertEquals(stocks.size(),2);
		assertEquals(historical.size(),2);
	}
	
	@Test
	public void parserTest() {
		
		Stock stock;
		
		stock = stocks.get(0);
		assertEquals(stock.getDate(),date);
		assertEquals(stock.getId(),"GKN.L");
		assertEquals(stock.getName(),"GKN");
		assertEquals(stock.getLatest(), 367.2, 0.00001);
		assertEquals(stock.getOpen(), 368.1, 0.00001);
		assertEquals(stock.getHigh(), 370.4, 0.00001);
		assertEquals(stock.getLow(), 366.2, 0.00001);
		assertEquals(stock.getClose(), 367.3, 0.00001);
		assertEquals(stock.getVolume(), 1275214);
		assertEquals(stock.getMarketCap(), 5.951, 0.00001);
		
		stock = stocks.get(1);
		assertEquals(stock.getDate(),date);
		assertEquals(stock.getId(),"GLEN.L");
		assertEquals(stock.getName(),"GLENCORE XSTRAT");
		assertEquals(stock.getLatest(), 325.5, 0.00001);
		assertEquals(stock.getOpen(), 325.1, 0.00001);
		assertEquals(stock.getHigh(), 328.95, 0.00001);
		assertEquals(stock.getLow(), 323.25, 0.00001);
		assertEquals(stock.getClose(), 324.2, 0.00001);
		assertEquals(stock.getVolume(), 9814259);
		assertEquals(stock.getMarketCap(), -1, 0.00001);
	}

	@Test
	public void historicalTest() {
		
		Stock stock;
		LocalDate date1, date2;
		
		date1 = new LocalDate(2010,1,25);
		date2 = new LocalDate(2010,1,19);
		
		stock = historical.get(0);
		assertEquals(stock.getDate(),date1);
		assertEquals(stock.getId(),"unknown");
		assertNull(stock.getName());
		assertEquals(stock.getLatest(), -1, 0.00001);
		assertEquals(stock.getOpen(), 546.59, 0.00001);
		assertEquals(stock.getHigh(), 549.88, 0.00001);
		assertEquals(stock.getLow(), 525.61, 0.00001);
		assertEquals(stock.getClose(), 529.94, 0.00001);
		assertEquals(stock.getVolume(), 4021800);
		assertEquals(stock.getMarketCap(), -1, 0.00001);
		
		stock = historical.get(1);
		assertEquals(stock.getDate(),date2);
		assertEquals(stock.getId(),"unknown");
		assertNull(stock.getName());
		assertEquals(stock.getLatest(), -1, 0.00001);
		assertEquals(stock.getOpen(), 581.20, 0.00001);
		assertEquals(stock.getHigh(), 590.42, 0.00001);
		assertEquals(stock.getLow(), 534.86, 0.00001);
		assertEquals(stock.getClose(), 550.01, 0.00001);
		assertEquals(stock.getVolume(), 5168800);
		assertEquals(stock.getMarketCap(), -1, 0.00001);
	}
}
