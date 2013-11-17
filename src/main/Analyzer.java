package main;

import java.util.*;
import org.joda.time.LocalDate;
import static main.Constants.*;
/**
 * ---------------------------------------------------------------------------------------------------------------------------
 * @author Qiao
 * ---------------------------------------------------------------------------------------------------------------------------
 * @version 1.0
 * ---------------------------------------------------------------------------------------------------------------------------
 * This class is the demo that analyze the data and generate html code based on user profile when it it called.
 * An example of the result page can be viewed at WebRoot/static/Analyzer.html
 */
public class Analyzer {
	public static final int HIGH_GRANULARITY = 1;
	public static final int LOW_GRANULARITY = 2;
	private String result;
	private ArrayList<String> profitables;
	private String[] indices;

	public Analyzer(String separator) {
		indices = FinanceQuery.getComponents(FTSE100)
				.split(separator);
		profitables = new ArrayList<String>();
		/*
		 * What the hell is going on with the data source??????????
		 * LinkedList<Stock>
		 * stocks=FinanceQuery.getLatestPrice(Constants.FTSE100); for(int
		 * i=0;i<stocks.size();i++){
		 * System.out.println(stocks.pollFirst().getId()); }
		 */
	}

	/**
	 * Calculate the average of a list of prices
	 */
	private double calculateDailyAverage(LinkedList<Stock> prices) {
		double result = 0;
		int divisor=prices.size();
		for (int i = 0; i < divisor; i++) {
			Stock price = prices.pollFirst();
			result += price.getClose();
			//System.out.println(price.getClose());
		}
		return result / divisor;
	}

	/*
	 * The actual process that can calculate and analyze the shares being
	 * received based on user's interest.
	 */
	public void analyze() {
		LocalDate today = new LocalDate();
		LocalDate two_week_history = today.minusWeeks(2);
		LocalDate one_week_history = today.minusWeeks(1);
		for (int i = 0; i < indices.length; i++) {
			System.out.println("Processing "+indices[i]);
			LinkedList<Stock> two_week_prices = FinanceQuery.getHistorical(
					indices[i], two_week_history, today,
					Constants.DAILY_INTERVAL);
			LinkedList<Stock> one_week_prices = FinanceQuery.getHistorical(
					indices[i], one_week_history, today,
					Constants.DAILY_INTERVAL);
			//System.out.println("One week");
			double one_week_average=calculateDailyAverage(one_week_prices);
			//System.out.println("Two week");
			double two_week_average=calculateDailyAverage(two_week_prices);
			if (two_week_average <one_week_average ) {
				profitables.add(indices[i]);
				System.out.println("One week: "+one_week_average+" Two week: "+two_week_average);
			}
		}
	}

	private boolean isProfitable(String index) {
		for (int i = 0; i < profitables.size(); i++) {
			if (index.equals(profitables.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Display the result in html format after the analysis is done. The level
	 * of granularity is specified and it will determine the format of result.
	 */
	public String report(Investor user,int granularity) {
		switch (granularity) {
		case HIGH_GRANULARITY: {
			result = "<h1>Reommended</h1><h2>Level=High granularity</h1>" + "<table>" + "<tr>"
					+ "<th>Index</th>" + "<th>Status</th>" + "<th>Comment</th>"
					+ "</tr>";
			for (int i = 0; i < indices.length; i++) {
				result += "<tr>";
				if (isProfitable(indices[i])) {
					result+= "<td class=\"Header\">";
				} else {
					result+= "<td>";
				}
				result +=indices[i]+"</td>";
				if (user.isInvested(indices[i])) {
					result+= "<td>You have invested this stock</td>";
				} else {
					result+="<td>You have not invested this stock yet</td>";
				}
				if (user.isInterested(indices[i])) {
					result+= "<td class=\"Recommended\">Interested in</td>";
				} else {
					result+=  "<td>Not interested in</td>";
				}
				result+= "</tr>";
			}
			result+= "</table>";
			break;
		}
		case LOW_GRANULARITY: {
			break;
		}
		}
		return result;
	}
}
