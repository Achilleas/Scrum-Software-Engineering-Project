package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	FinanceQueryTest.class,
	AnalyzerTest.class,
	InvestorTest.class
})

public class AllTests {

} 
