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
 * @version 1.2
 * Some data structures are abstracted to StockAnalysis class
 * Slope method added and average method is updated to be more rigorous ( At least *1.05> ).
 * Handle nulls
 * Hash map instead of array list is used for O(1) search cost.
 * Further request can refer back to work done previously.
 * Brand new test HTML generated
 * ----------------------------------------------------------------------------
 * @version 1.1
 * Functionality to view a single share is provided, some variables upgraded to field scope to avoid duplication of codes.
 * New tests added
 * ----------------------------------------------------------------------------
 * @version 1.0
 * This class is the demo that analyze the data and generate HTML code based on user profile when it it called. An example of the result page can be viewed at WebRoot/static/Analyzer.html
 */
public class Analyzer {
	private ArrayList<String> profitables;
	private String[] indices;
	private ArrayList<StockAnalysis> analysis_list;
	private HashMap<String,StockAnalysis> table;
	private LocalDate today;
	private LocalDate two_week_history;
	private LocalDate four_week_history;
	private LinkedList<Stock> two_week_prices;
	private LinkedList<Stock> four_week_prices;
	public Analyzer(String separator) {
		indices = FinanceQuery.getComponents(FTSE100).split(separator);
		profitables = new ArrayList<String>();
		today = new LocalDate();
		two_week_history = today.minusWeeks(2);
		four_week_history = today.minusWeeks(4);
		analysis_list=new ArrayList<StockAnalysis>();
		table=new HashMap<String,StockAnalysis>();
	}
	public StockAnalysis getAnalysis(String index){
		 four_week_prices = FinanceQuery.getHistorical(
					index, four_week_history, today,
					Constants.DAILY_INTERVAL);
		 two_week_prices = FinanceQuery.getHistorical(
					index, two_week_history, today,
					Constants.DAILY_INTERVAL);
		if(two_week_prices==null||four_week_prices==null){
			return null;
		}
		StockAnalysis analysis=new StockAnalysis(index);
		analysis.analyze(two_week_prices,four_week_prices);
		return analysis;
	}
/**
 * Since analysis of the entire market will take ages, it is wise to analyze historical prices only once.
 * This method can process historical analysis before users' requests.
 */
	public void analyze() {
		for (int i = 0; i < indices.length; i++) {
			System.out.println("Processing "+indices[i]);
			StockAnalysis analysis=getAnalysis(indices[i]);
			if(analysis!=null){
				table.put(indices[i], analysis);
			}
		}
	}
	private String getFullReport(Investor user){
		String result;
		ArrayList<String> matches=new ArrayList<String>();
		boolean suggested=true;
		result = "<h1>Market overview</h1><h2>All shares</h2>"
				+"<button type=\"button\" onclick=\"ChangeStyle();\">Highlight</button>"+ "<table>" + "<tr>"
				+ "<th>Index</th>" + "<th>Comment</th>" + "<th>If invested</th>" + "<th>Your preference</th>"
				+ "</tr><tr>";
		for (int i = 0; i < indices.length; i++) {
			suggested=true;
			StockAnalysis analysis=table.get(indices[i]);
			if(analysis==null){
				continue;
			}
			if (suggested=analysis.getAverageResult()||analysis.getGradientResult()) {
				result+= "<td class=\"Header\">"+"<a href=\"/servlets/recommend?id="+indices[i]+"\">"+indices[i]+"</a></td>"
						+"<td class=\"Header\">"+analysis.getComment()+"</td>";
			} else {
				result+= "<td>"+"<a href=\"/servlets/recommend?id="+indices[i]+"\">"+indices[i]+"</a></td>"
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
		return result;
	}
	private String getSingleReport(Investor user,String index){
		String result;
		StockAnalysis analysis;
		if(table.isEmpty()){
			analysis=getAnalysis(index);
		}else{
			analysis=table.get(index);
		}
		if(analysis==null){
			result="<h1>Cannot get data for+"+index+"</h1>";
			return result;
		}
		result = "<h1>Analysis of a single stock</h1><h2>Index: +"+index+"</h2>";
		if(user.isInterested(index)){
			result+="<h3>Interested in</h3> ";
		}else{
			result+="<h3>Not interested in</h3> ";
		}
		if(user.isInvested(index)){
			result+="<h3>You have invested this stock</h3> ";
		}else{
			result+="<h3>You have invested this stock</h3> ";
		}
		result+="<table><tr>"+ "<th>Title</th>"+ "<th>Analyzed data</th>"
				+ "</tr><tr>";
		result+="<td>Last two weeks' average</td><td>"
				+analysis.getFirstAverage()+"</td>"
				+"<tr><td>Last four weeks' average</td><td>"
				+analysis.getSecondAverage()+"</td></tr>"
				+"<tr><td>Last two weeks' slope</td><td>"
				+analysis.getFirstGradient()+"</td></tr>"
				+"<tr><td>Last two weeks' slope</td><td>"
				+analysis.getFirstGradient()+"</td></tr>"
				+"<tr><td>Last four weeks' slope</td><td>"
				+analysis.getSecondGradient()+"</td></tr></table>";
		return result;
	}
	/**
	 * Display the result in HTML format after the analysis is done.
	 * To have an overview of the entire market, the index is set to be null
	 * To generate report about a specific share, the index should be the index of
	 * that share.
	 * Note that you must call analyze method before passing the null parameter, otherwise the report contains nothing!
	 */
	private String report(Investor user,String index) {
		String result;
		if(index==null){
			result=getFullReport(user);
		}else{
			result=getSingleReport(user,index);
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
