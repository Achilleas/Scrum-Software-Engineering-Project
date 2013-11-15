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

	static LinkedList<Stock> stocks;
	static LocalDate date;
	
	@BeforeClass
	public static void setupBeforeClass() {

		date = LocalDate.now();
		CSVParser parser = new CSVParser();
		File file = new File("parser-test.csv");

		stocks = parser.parseLatestPriceCSV(file, date);
	}
	
	@Test
	public void listSize() {
		assertEquals(stocks.size(),2);
	}
	
	@Test
	public void parserTestSample() {
		
		Stock stock;
		
		stock = stocks.get(0);
		assertEquals(stock.getDate(),date);
		assertEquals(stock.getId(),"GKN.L");
		assertEquals(stock.getName(),"GKN");
		assertEquals(stock.getLatest(), 367.2, 0.001);
		assertEquals(stock.getOpen(), 368.1, 0.001);
		assertEquals(stock.getHigh(), 370.4, 0.001);
		assertEquals(stock.getLow(), 366.2, 0.001);
		assertEquals(stock.getPreviousClose(), 367.3, 0.001);
		assertEquals(stock.getVolume(), 1275214);
		assertEquals(stock.getMarketCap(), 5.951, 0.001);
		
		stock = stocks.get(1);
		assertEquals(stock.getDate(),date);
		assertEquals(stock.getId(),"GLEN.L");
		assertEquals(stock.getName(),"GLENCORE XSTRAT");
		assertEquals(stock.getLatest(), 325.5, 0.001);
		assertEquals(stock.getOpen(), 325.1, 0.001);
		assertEquals(stock.getHigh(), 328.95, 0.001);
		assertEquals(stock.getLow(), 323.25, 0.001);
		assertEquals(stock.getPreviousClose(), 324.2, 0.001);
		assertEquals(stock.getVolume(), 9814259);
		assertEquals(stock.getMarketCap(), -1, 0.001);
	}

}
