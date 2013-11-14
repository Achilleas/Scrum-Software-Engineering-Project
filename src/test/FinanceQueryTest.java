package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import static main.Constants.*;
import main.FinanceQuery;

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
	}
	
	@Before
	public void setupBefore() {
		fromDate = new LocalDate(2000,2,15);
		toDate = new LocalDate(2010,1,31);
	}
	
	@Test
	public void testDailyPriceFile() {
		// create 2 temporary file and test if second file will replace the first file
		// a sample csv file will created
		File file1, file2, sample_file;
		FinanceQuery query = new FinanceQuery();
		
		file1 = query.getDailyPriceCSV(FTSE100);
		file2 = query.getDailyPriceCSV(FTSE100);
		
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
		FinanceQuery query = new FinanceQuery();
		
		symbols = query.getComponents("^FTSE");
		System.out.println("The list of symbols are \n" + symbols);
		
		file1 = query.getDailyPriceCSV(symbols);
		file2 = query.getDailyPriceCSV(symbols);
		
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
		FinanceQuery query = new FinanceQuery();
		
		query.getDailyPriceCSV(null);
	}
	
	@Test
	public void testHistoricalFile() {
		// create 2 temporary file and test if second file will replace the first file
		// a sample csv file will created
		File file1, file2, sample_file;
		FinanceQuery query = new FinanceQuery();
		
		file1 = query.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
		file2 = query.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
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
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS(null, fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest2() {
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", null, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest3() {
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", null, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = NullPointerException.class)
	public void getHistoricalNullTest4() {
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", fromDate, toDate, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidInterval() {
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", fromDate, toDate, "daily");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange1() {
		// flip the toDate and fromDate
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", toDate, fromDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange2() {
		
		toDate = fromDate;
		
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange3() {
		
		LocalDate nextYear = new LocalDate();
		nextYear = nextYear.plusYears(1);
		
		fromDate = toDate;
		toDate = nextYear;
		
		FinanceQuery query = new FinanceQuery();
		query.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test
	public void retrieveAnotherIndex() {
		// test for other index (NASDAQ-100)
		File file1, file2, sample_file;
		String symbols;
		FinanceQuery query = new FinanceQuery();
		
		symbols = query.getComponents("^NDX");
		System.out.println("The list of symbols are \n" + symbols);
		
		file1 = query.getDailyPriceCSV(symbols);
		file2 = query.getDailyPriceCSV(symbols);
		
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
}
