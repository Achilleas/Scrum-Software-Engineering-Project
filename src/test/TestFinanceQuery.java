package test;

import static org.junit.Assert.*;

import java.io.File;

import main.FinanceQuery;

import org.junit.Test;

public class TestFinanceQuery {

	@Test
	public void testFile() {
		
		File file1, file2;
		
		file1 = FinanceQuery.getDailyPriceCSV("@^FTSE");
		file2 = FinanceQuery.getDailyPriceCSV("@^FTSE");
		
		assertNotNull(file1);
		assertNotNull(file2);
		assertNotEquals(file1, file2);
	}

}
