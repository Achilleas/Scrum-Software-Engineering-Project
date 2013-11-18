package main;

import java.util.*;

import org.joda.time.LocalDate;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

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
	private ArrayList<StockAnalysis> analysis_list;
	private HashMap<String,StockAnalysis> table;
	LocalDate today;
	LocalDate two_week_history;
	LocalDate one_week_history;
	public Analyzer(String separator) {
		indices = FinanceQuery.getComponents(FTSE100).split(separator);
		profitables = new ArrayList<String>();
		today = new LocalDate();
		two_week_history = today.minusWeeks(2);
		one_week_history = today.minusWeeks(1);
		analysis_list=new ArrayList<StockAnalysis>();
	}
/**
 * Since analysis of the entire market will take ages, it is wise to analyze historical prices only once.
 * This method can process historical analysis before users' requests.
 */
	public void analyze() {
		for (int i = 0; i < indices.length; i++) {
			System.out.println("Processing " + indices[i]);
			LinkedList<Stock> two_week_prices = FinanceQuery.getHistorical(
					indices[i], two_week_history, today,
					Constants.DAILY_INTERVAL);
			LinkedList<Stock> one_week_prices = FinanceQuery.getHistorical(
					indices[i], one_week_history, today,
					Constants.DAILY_INTERVAL);
			StockAnalysis analysis=new StockAnalysis(indices[i]);
			analysis.analyze(one_week_prices,two_week_prices);
			table.put(indices[i], analysis);
		}
	}

	/**
	 * Display the result in html format after the analysis is done.
	 * To have an overview of the entire market, the index is set to be null
	 * To generate report about a specific share, the index should be the index of
	 * that share.
	 * Note that you must call analyze method before passing the null parameter, otherwise the report contains nothing!
	 */
	private String report(Investor user,String index) {
		ArrayList<String> matches=new ArrayList<String>();
		if(index==null){
			boolean suggested=true;
			result = "<h1>Market overview</h1><h2>All shares</h2>"
					+"<button type=\"button\" onclick=\"ChangeStyle();\">Highlight</button>"+ "<table>" + "<tr>"
					+ "<th>Index</th>" + "<th>Comment</th>" + "<th>If invested</th>" + "<th>Your preference</th>"
					+ "</tr><tr>";
			for (int i = 0; i < indices.length; i++) {
				suggested=true;
				StockAnalysis analysis=table.get(indices[i]);
				if (suggested=analysis.getAverageResult()||analysis.getAverageResult()) {
					result+= "<td class=\"Header\">"+indices[i]+"</td>"
							+"<td class=\"Header\">"+analysis.getComment()+"</td>";
				} else {
					result+= "<td>"+indices[i]+"</td>"
							+"<td>"+analysis.getComment()+"</td>";
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
				if(suggested&&analysis.getAverageResult()&&analysis.getAverageResult()){
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
			StockAnalysis analysis;
			if(table.isEmpty()){
				analysis=new StockAnalysis(index);
				LinkedList<Stock> two_week_prices = FinanceQuery.getHistorical(
						index, two_week_history, today,
						Constants.DAILY_INTERVAL);
				LinkedList<Stock> one_week_prices = FinanceQuery.getHistorical(
						index, one_week_history, today,
						Constants.DAILY_INTERVAL);
				analysis.analyze(one_week_prices, two_week_prices);
			}else{
				analysis=table.get(index);
			}
			result = "<h1>Analysis of a single stock</h1>"
					+"<button type=\"button\" onclick=\"ChangeStyle();\">Highlight</button>"+ "<table>"
			+ "<tr>"+ "<th>Index</th>"+ "<th>Two weeks' average</th>" + "<th>One weeks' average</th>"+ "<th>Two weeks' gradient</th>" + "<th>One weeks' gradient</th>" + "<th>If invested</th>" + "<th>Your preference</th>"
					+ "</tr><tr>";
			if (analysis.getAverageResult()||analysis.getGradientResult()) {
				result+= "<td class=\"Header\">"+index+"</td>";
			} else {
				result+= "<td>"+index+"</td>";
			}
			result+="<td>"
					+analysis.getSecondAverage()+"</td><td>"
					+analysis.getFirstAverage()+"</td>";
			result+="<td>"
					+analysis.getSecondGradient()+"</td><td>"
					+analysis.getFirstAverage()+"</td>";
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
			result+="</table>";
		}
		return result;
	}
	public String getFullReport(Investor user,String index){
		HtmlWriter html = new HtmlWriter("Analysis");
		html.append(HtmlWriter.getCSS());
		html.append(HtmlWriter.getJavaScript());
		html.closeHead();
		html.append(report(user,index));
		html.closeHtml();
		return html.getContent();
	}
}
