package test;

import java.util.ArrayList;
import main.Investor;

import org.junit.Test;

public class AnalyzerTest {
	String firstName = "Qiao";
	String surname = "Kang";
	ArrayList<String> companiesInvested;
	ArrayList<String> companiesInterested;
	Investor user;
	String password="123";
	@Test
	public void test() {
		String file = "example_table.csv";
		/* TODO: Add more argument to the constructor
		Investor user=new Investor("Qiao","Kang","123"); 
		Analyzer analyzer=new Analyzer(user,file,";");
		analyzer.analyze();
		System.out.println(analyzer.report(Analyzer.HIGH_GRANULARITY)); */
	}

}
