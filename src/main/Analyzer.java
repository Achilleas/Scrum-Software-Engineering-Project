package main;

import java.util.*;
import org.joda.time.LocalDate;
import static main.Constants.*;

/**
 * ----------------------------------------------------------------------------
 * ----------------------------------------------------------------------------
 * 
 * @author Qiao
 * ----------------------------------------------------------------------------
 * @version 1.1
 * Functionality to view a single share is provided, some variables upgraded to field scope to avoid duplication of codes.
 * New tests added
 * ----------------------------------------------------------------------------
 * @version 1.0
 * This class is the demo that analyze the data and generate html code based on user profile when it it called. An example of the result page can be viewed at WebRoot/static/Analyzer.html
 */
public class Analyzer {
	private String result;
	private ArrayList<String> profitables;
	private String[] indices;
	private double one_week_average;
	private double two_week_average;
	LocalDate today;
	LocalDate two_week_history;
	LocalDate one_week_history;
	public Analyzer(String separator) {
		indices = FinanceQuery.getComponents(FTSE100).split(separator);
		profitables = new ArrayList<String>();
		today = new LocalDate();
		two_week_history = today.minusWeeks(2);
		one_week_history = today.minusWeeks(1);
	}

	/**
	 * Calculate the average of a list of prices
	 */
	private double calculateDailyAverage(LinkedList<Stock> prices) {
		double result = 0;
		int divisor = prices.size();
		for (int i = 0; i < divisor; i++) {
			Stock price = prices.pollFirst();
			result += price.getClose();
			// System.out.println(price.getClose());
		}
		return result / divisor;
	}
	private void analyze(String index){
		LinkedList<Stock> two_week_prices = FinanceQuery.getHistorical(
				index, two_week_history, today,
				Constants.DAILY_INTERVAL);
		LinkedList<Stock> one_week_prices = FinanceQuery.getHistorical(
				index, one_week_history, today,
				Constants.DAILY_INTERVAL);
		one_week_average = calculateDailyAverage(one_week_prices);
		two_week_average = calculateDailyAverage(two_week_prices);
	}
	/*
	 * The actual process that can calculate and analyze the shares being
	 * received based on user's interest.
	 */
	public void analyze() {
		for (int i = 0; i < indices.length; i++) {
			System.out.println("Processing " + indices[i]);
			analyze(indices[i]);
			if (two_week_average < one_week_average) {
				profitables.add(indices[i]);
				System.out.println("One week: " + one_week_average
						+ " Two week: " + two_week_average);
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
	 * To have an overview of the entire market, the index is set to be null To
	 * generate report about a specific share, the index should be the index of
	 * that share.
	 */
	public String report(Investor user,String index) {
		ArrayList<String> matches=new ArrayList<String>();
		if(index==null){
			boolean suggested=true;
			result = "<h1>Market overview</h1><h2>All shares</h2>" + "<table>" + "<tr>"
					+ "<th>Index</th>" + "<th>Comment</th>" + "<th>If invested</th>" + "<th>Your preference</th>"
					+ "</tr><tr>";
			for (int i = 0; i < indices.length; i++) {
				suggested=true;
				if (isProfitable(indices[i])) {
					result+= "<td class=\"Header\">"+indices[i]+"</td>"
							+"<td class=\"Header\">Profitable based on two weeks' analysis.</td>";
				} else {
					result+= "<td>"+indices[i]+"</td>"
							+"<td>No comment</td>";
					suggested=false;
				}
				if(suggested){
					if(user.isInvested(indices[i])){
						result+="<td class=\"Recommended\">You have not invested this stock yet</td>";
					}else{
						suggested=false;
						result+= "<td>You have invested this stock</td>";
					}
					if (user.isInterested(indices[i])&&suggested) {
						result+= "<td class=\"Recommended\">Interested in</td>";
					} else{
						suggested=false;
						result+=  "<td>Not interested in</td>";
					}
				}else{
					if(user.isInvested(indices[i])){
						result+="<td>You have not invested this stock yet</td>";
					}else{
						result+= "<td>You have invested this stock</td>";
					}
					if (user.isInterested(indices[i])&&suggested) {
						result+= "<td>Interested in</td>";
					} else{
						result+=  "<td>Not interested in</td>";
					}
				}
				result+= "</tr>";
				if(suggested){
					matches.add(indices[i]);
				}
			}
			result+= "</table>";
			if(matches.size()>0){
				result+="<h2>Those are the stocks you may want to buy.</h2>";
				for(int i=0;i<matches.size();i++){
					result+="<p>"+matches.get(i)+"</p>";
				}
			}else{
				result+="<h2>Based on your profile, there is no recommendation.</h2>";
			}
		}else{
			analyze(index);
			result = "<h1>Analysis about a single stock</h1>" + "<table>"
			+ "<tr>"+ "<th>Index</th>"+ "<th>Two weeks' average</th>" + "<th>One weeks' average</th>" + "<th>If invested</th>" + "<th>Your preference</th>"
					+ "</tr><tr>";
			if (one_week_average>two_week_average) {
				result+= "<td class=\"Header\">"+index+"</td>";
			} else {
				result+= "<td>"+index+"</td>";
			}
			result+="<td>"
					+two_week_average+"</td><td>"
					+one_week_average+"</td>";
			if (user.isInvested(index)) {
				result+= "<td>You have invested this stock</td>";
			} else {
				result+="<td class=\"Recommended\">You have not invested this stock yet</td>";
			}
			if (user.isInterested(index)) {
				result+= "<td class=\"Recommended\">Interested in</td>";
			} else {
				result+=  "<td>Not interested in</td>";
			}
		}
		return result;
	}
}
