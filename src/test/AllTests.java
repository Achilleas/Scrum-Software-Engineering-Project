package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	AnalyzerTest.class,
	FinanceQueryTest.class,
	InvestorTest.class,
	ProfileWriterTest.class
})

public class AllTests {

} 
