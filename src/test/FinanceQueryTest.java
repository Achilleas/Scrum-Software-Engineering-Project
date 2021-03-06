package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;

import static main.Constants.*;
import main.FinanceQuery;
import main.Stock;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FinanceQueryTest {
	
	LocalDate fromDate, toDate;
	
	@BeforeClass
	public static void setupBeforeClass() {
		System.out.println("Running TestFinanceQuery");
		(new Thread(new FinanceQuery())).start();
	}
	
	@Before
	public void setupBefore() {
		fromDate = new LocalDate(2000,2,15);
		toDate = new LocalDate();
	}
	
	@Test
	public void testDailyPriceFile() {
		// create 2 temporary file and test if second file will replace the first file
		// a sample csv file will created
		File file1, file2, sample_file;
		
		file1 = FinanceQuery.getLatestPriceCSV("@" + FTSE100);
		file2 = FinanceQuery.getLatestPriceCSV("@" + FTSE100);
		
		sample_file = new File("sample-daily-price.csv");
		
		assertNotNull(file1);
		assertNotNull(file2);
		assertNotEquals(file1.getPath(), file2.getPath());
		try {
			FileUtils.copyFile(file1, sample_file);
		} catch (IOException e) {
			System.out.println("Unable to copy sample csv file");
		}
	}

	@Test
	public void altDailyPriceFile() {
		// this uses webscrape to get components instead of using @**** symbol to get components
		File file1, file2, sample_file;
		String symbols;
		symbols = FinanceQuery.getComponents("^FTSE");
		System.out.println("The FTSE list of symbols are \n" + symbols);
		
		file1 = FinanceQuery.getLatestPriceCSV(symbols);
		file2 = FinanceQuery.getLatestPriceCSV(symbols);
		
		sample_file = new File("sample-daily-price2.csv");
		
		assertNotNull(file1);
		assertNotNull(file2);
		assertNotEquals(file1.getPath(), file2.getPath());
		try {
			FileUtils.copyFile(file1, sample_file);
		} catch (IOException e) {
			System.out.println("Unable to copy sample csv file");
		}
	}
	
	@Test(expected = NullPointerException.class)
	public void getDailyPriceNullTest() {
		FinanceQuery.getLatestPriceCSV(null);
	}
	
	@Test
	public void testHistoricalFile() {
		// create 2 temporary file and test if second file will replace the first file
		// a sample csv file will created
		File file1, file2, sample_file;
		file1 = FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
		file2 = FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
		sample_file = new File("sample-historical.csv");
		
		assertNotNull(file1);
		assertNotNull(file2);
		assertNotEquals(file1.getPath(), file2.getPath());
		try {
			FileUtils.copyFile(file1, sample_file);
		} catch (IOException e) {
			System.out.println("Unable to copy sample csv file");
		}
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest1() {
		FinanceQuery.getHistoricalCVS(null, fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest2() {
		FinanceQuery.getHistoricalCVS("GOOG", null, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest3() {
		FinanceQuery.getHistoricalCVS("GOOG", null, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest4() {
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidInterval() {
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, "daily");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange1() {
		FinanceQuery.getHistoricalCVS("GOOG", toDate, fromDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange2() {
		
		toDate = fromDate;
		
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange3() {
		
		LocalDate nextYear = new LocalDate();
		nextYear = nextYear.plusYears(1);
		
		fromDate = toDate;
		toDate = nextYear;
		
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test
	public void retrieveAnotherIndex() {
		// test for other index (NASDAQ-100)
		File file1, file2, sample_file;
		String symbols;
		symbols = FinanceQuery.getComponents(NASDAQ100);
		System.out.println("The NASDAQ list of symbols are \n" + symbols);
		
		file1 = FinanceQuery.getLatestPriceCSV(symbols);
		file2 = FinanceQuery.getLatestPriceCSV(symbols);
		
		sample_file = new File("sample-daily-price-NASDAQ.csv");
		
		assertNotNull(file1);
		assertNotNull(file2);
		assertNotEquals(file1.getPath(), file2.getPath());
		try {
			FileUtils.copyFile(file1, sample_file);
		} catch (IOException e) {
			System.out.println("Unable to copy sample csv file");
		}
	}
	
	@Test
	public void ftseSetTest() {
		
		NavigableSet<String> set1;
		NavigableSet<String> set2;
		Iterator<String> iterator;
		
		set1 = FinanceQuery.getComponentsList(FTSE100);
		set2 = FinanceQuery.getComponentsList(FTSE100);
		
		assertNotNull(set1);
		assertTrue(set1.size() > 0);
		iterator = set1.iterator();
		while (iterator.hasNext()) {
			assertNotNull(iterator.next());
		}
		assertEquals(set1,set2);
	}
	
	@Test
	public void nasdaqSetTest() {

		NavigableSet<String> set1;
		NavigableSet<String> set2;
		Iterator<String> iterator;
		
		set1 = FinanceQuery.getComponentsList(NASDAQ100);
		set2 = FinanceQuery.getComponentsList(NASDAQ100);
		
		assertNotNull(set1);
		assertTrue(set1.size() > 0);
		iterator = set1.iterator();
		while (iterator.hasNext()) {
			assertNotNull(iterator.next());
		}
		assertEquals(set1,set2);
	}
	
	@Test
	public void simpleMethodTest() {
		
		String symbol = "AAL.L";
		LinkedList<Stock> stocks = null;
		LinkedList<Stock> historical = null;
		stocks = FinanceQuery.getLatestPrice(symbol);
		historical = FinanceQuery.getHistorical(symbol, fromDate, toDate, WEEKLY_INTERVAL);
		
		assertNotNull(stocks);
		assertNotNull(historical);
		assertEquals(stocks.size(),1);
	}
}
