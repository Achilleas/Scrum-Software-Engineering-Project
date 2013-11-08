package test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import main.Analyzer;
import main.InvestorProfile;

import org.junit.Test;

public class AnalyzerTest {

	@Test
	public void test() {
		String file = "example_table.csv";
		InvestorProfile user=new InvestorProfile("Qiao","Kang");
		Analyzer analyzer=new Analyzer(user,file,";");
		analyzer.analyze();
		System.out.println(analyzer.report(Analyzer.HIGH_GRANULARITY));
	}

}
