package main;

import java.io.File;
import java.util.*;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;

/*
 * This class is the demo that analyze the data parsed based on user profile and generate html code when it it called.
 */
public class Analyzer {
	public static final int HIGH_GRANULARITY=1;
	public static final int LOW_GRANULARITY=2;
	private Investor user;
	private String result;
	private ArrayList<String> profitables;
	private String[] indices;
	public Analyzer(Investor user,String separator){
		Validate.notNull(user, "profile can't be null");
		indices=FinanceQuery.getComponents(Constants.FTSE100).split(separator);
		profitables=new ArrayList<String>();
		this.user=user;
		/*
		 * What the hell is going on with the data source??????????
		LinkedList<Stock> stocks=FinanceQuery.getLatestPrice(Constants.FTSE100);
		for(int i=0;i<stocks.size();i++){
			System.out.println(stocks.pollFirst().getId());
		}
		 */
	}
	/**
	 * Calculate the average of a list of prices
	 */
	private double calculateDailyAverage(LinkedList<Stock> prices){
		double result=0;
		for(int i=0;i<prices.size();i++){
			Stock price=prices.pollFirst();
			result+=price.getClose();
		}
		return result/prices.size();
	}
	/*
	 * The actual process that can calculate and analyze the shares being received based on user's interest.
	 */
	public void analyze(){
		LocalDate today=new LocalDate();
		LocalDate two_week_history=today.minusWeeks(2);
		LocalDate one_week_history=today.minusWeeks(1);
		for(int i=0;i<indices.length;i++){
			LinkedList<Stock> two_week_prices=FinanceQuery.getHistorical(indices[i], two_week_history, today,Constants.DAILY_INTERVAL);
			LinkedList<Stock> one_week_prices=FinanceQuery.getHistorical(indices[i], one_week_history, today,Constants.DAILY_INTERVAL);
			//Stock today=FinanceQuery.get
			if(calculateDailyAverage(two_week_prices)<calculateDailyAverage(one_week_prices)){
				profitables.add(indices[i]);
			}
		}
	}
	
	private boolean isProfitable(String index){
		for(int i=0;i<profitables.size();i++){
			if(index.equals(profitables.get(i))){
				return true;
			}
		}
		return false;
	}
/**
 * Display the result in html format after the analysis is done. The level of granularity is specified and it will determine the format of result.
 */
	public String report(int granularity){
		switch(granularity){
		case HIGH_GRANULARITY:{
			result="<h1>Reommended</h1>"+
					"<table>"+
					"<tr>"+
						"<th>Index</th>"+
						"<th>Status</th>"+
						"<th>Comment</th>"+
					"</tr>";
			for(int i=0;i<indices.length;i++){
				result=result+
						"<tr>";
				if(isProfitable(indices[i])){
					result=result
							+"<td class=\"Header\">";
				}else{
					result=result
							+"<td>";
				}
				result=indices[i]+"</td>";
				if(user.isInvested(indices[i])){
					result=result+
							"<td>You have invested this stock</td>";
				}else{
					result=result+
							"<td>You have not invested this stock yet</td>";
				}
				if(user.isInterested(indices[i])){
					result=result
							+"<td class=\"Recommended\">Interested in</td>";
				}else{
					result=result
							+"<td>Not interested in</td>";
				}
				result=result+"</tr>";
			}
			result=result+
						"</table>";
			break;
		}
		case LOW_GRANULARITY:{
			
			break;
		}
		}

		return result;
	}
	public static void main(String args[]){
		Analyzer analyzer=new Analyzer(new Investor(), ",");
		analyzer.analyze();
		HtmlWriter html=new HtmlWriter("Analysis");
		html.append(HtmlWriter.getCSS());
		html.append(HtmlWriter.getJavaScript());
		html.closeHead();
		html.append(analyzer.report(1));
		html.append("<button type=\"button\" onclick=\"ChangeStyle();\">Highlight</button>");
		html.closeHtml();
		System.out.println(html.getContent());
	}
}
