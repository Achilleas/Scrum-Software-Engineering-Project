package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import main.FinanceQuery;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestFinanceQuery {

	@Test
	public void testFile() {
		// create 2 temporary file and test if second file will replace the first file
		// a sample csv file will created
		File file1, file2, sample_file;
		
		file1 = FinanceQuery.getDailyPriceCSV("@^FTSE");
		file2 = FinanceQuery.getDailyPriceCSV("@^FTSE");
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

}
