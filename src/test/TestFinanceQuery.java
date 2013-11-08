package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static main.Constants.*;
import main.FinanceQuery;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFinanceQuery {
	
	Date fromDate = new Date();
	Date toDate = new Date();
	
	@BeforeClass
	public static void setupBeforeClass() {
		System.out.println("Running TestFinanceQuery");
	}
	
	@Before
	public void setupBefore() {
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		fromDate = cal.getTime();
		
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		toDate = cal.getTime();
	}
	
	@Test
	public void testDailyPriceFile() {
		// create 2 temporary file and test if second file will replace the first file
		// a sample csv file will created
		File file1, file2, sample_file;
		
		file1 = FinanceQuery.getDailyPriceCSV(FTSE100_COMPONENTS);
		file2 = FinanceQuery.getDailyPriceCSV(FTSE100_COMPONENTS);
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

	@Test(expected = NullPointerException.class)
	public void getDailyPriceNullTest() {
		FinanceQuery.getDailyPriceCSV(null);
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
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		fromDate = cal.getTime();
		
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		toDate = cal.getTime();
		
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange2() {
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		fromDate = cal.getTime();
		toDate = cal.getTime();
		
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDateRange3() {
		Calendar cal = Calendar.getInstance();
		int next_year = 1 + cal.get(Calendar.YEAR);

		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		fromDate = cal.getTime();
		
		cal.set(Calendar.YEAR, next_year);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		toDate = cal.getTime();
		
		FinanceQuery.getHistoricalCVS("GOOG", fromDate, toDate, WEEKLY_INTERVAL);
	}
}
