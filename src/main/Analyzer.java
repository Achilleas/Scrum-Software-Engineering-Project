package main;

import java.util.LinkedList;
import org.apache.commons.lang3.Validate;

/*
 * This class is the demo that analyze the data parsed based on user profile and generate html code when it it called.
 */
public class Analyzer {
	public static final int HIGH_GRANULARITY=1;
	public static final int LOW_GRANULARITY=2;
	private LinkedList<DailyPrice> prices;
	private InvestorProfile user;
	private String result;
	public Analyzer(InvestorProfile profile, String filename,String separator){
		Validate.notNull(filename, "firstName can't be null");
		Validate.notNull(profile, "profile can't be null");
		CSVParser parser=new CSVParser(separator);
		prices=parser.parseCSV(filename);
		user=profile;
	}
	/*
	 * The actual process that can calculate and analyze the shares being received based on user's interest.
	 */
	public void analyze(){
		result="Just buy something! Functionality will be added later.";
	}
/*
 * Display the result in html format after the analysis is done. The level of granularity is specified and it will determine the format of result.
 */
	public String report(int granularity){
		return result;
	}
}
