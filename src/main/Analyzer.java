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
 * @version 1.4
 * Add headers
 * Fix displaying bugs
 * Add market capitalization analysis
 * ----------------------------------------------------------------------------
 * @version 1.3
 * HTML looks nicer
 * Delete redundant fields
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
 * This class is the demo that analyze the data and generate HTML code based on user profile when it is called. An example of the result page can be viewed at WebRoot/static/Analyzer.html
 */
public class Analyzer {
	private String[] indices;
	private HashMap<String,StockAnalysis> table;
	private LocalDate today;
	private LocalDate twenty_five_week_history;
	private LocalDate fifty_week_history;
	private LinkedList<Stock> twenty_five_week_prices;
	private LinkedList<Stock> fifty_week_prices;
	private Stock[] latest;
	
	/**
	 * Analyzer constructor
	 * @param separator the regex in which the analyzer will use to split to components
	 */
	public Analyzer(String separator) {
		indices = FinanceQuery.getComponents(FTSE100).split(separator);
		today = new LocalDate();
		twenty_five_week_history = today.minusWeeks(25);
		fifty_week_history = today.minusWeeks(50);
		table = new HashMap<String,StockAnalysis>();
	}
	private Stock getStock(String index){
		for(int i=0;i<latest.length;i++){
			if(latest[i].getId().equals(index)){
				return latest[i];
			}
		}
		return null;
	}
	private void set_twenty_five_weeks(){
		twenty_five_week_prices=new LinkedList<Stock>();
		Stock[] stocks=StockAnalysis.convertToArray(fifty_week_prices);
		for(int i=0;i<stocks.length;i++){
			LocalDate d;
			if(stocks[i].getDate().isAfter(twenty_five_week_history)){
				twenty_five_week_prices.add(stocks[i]);
			}
		}
	}
	/**
	 * Gets a 25-week and 50-week of historical data 
	 * @param index the index this historical data is to be taken from
	 * @return returns return the stock analysis {@link StockAnalysis}
	 */
	public StockAnalysis getAnalysis(String index){
		 fifty_week_prices = FinanceQuery.getHistorical(
					index, fifty_week_history, today,
					Constants.DAILY_INTERVAL);
		 set_twenty_five_weeks();
		StockAnalysis analysis=new StockAnalysis(index);
		Stock stock=getStock(index);
		if(twenty_five_week_prices==null||fifty_week_prices==null||stock==null){
			return null;
		}
		analysis.analyze(twenty_five_week_prices,fifty_week_prices,stock.getMarketCap(),stock.getLatest(),stock.getVolume());
		return analysis;
	}
	
	/**
	 * 
	 */
	public void analyze() {
		//Since analysis of the entire market will take ages, it is wise to analyze historical prices only once.
		//This method can process historical analysis before users' requests.
		System.out.println("Loading and analyzing data...");
		LinkedList<Stock> latest_list=FinanceQuery.getLatestPrice(FinanceQuery.getComponents(FTSE100));
		latest=StockAnalysis.convertToArray(latest_list);
		for (int i = 0; i < indices.length; i++) {
			StockAnalysis analysis=getAnalysis(indices[i]);
			if(analysis!=null){
				table.put(indices[i], analysis);
			}
		}
		System.out.println("...Analysis done");
	}
	
	/**
	 * Returns 
	 * @param user the user
	 * @return returns a string containing the html report of the analysis
	 */
	private String getFullReport(Investor user){
		String result;
		ArrayList<String> matches=new ArrayList<String>();
		boolean suggested=true;
		result = "<h1>General Analysis</h1><h2>All shares</h2>"
				+"<button type=\"button\" onclick=\"ChangeStyle();\">Highlight</button>"+ "<table>" + "<tr>"
				+ "<th>Index</th>" + "<th>Comment</th>" + "<th>If invested</th>" + "<th>Your preference</th>"
				+ "</tr><tr>";
		for (int i = 0; i < indices.length; i++) {
			suggested=true;
			StockAnalysis analysis=table.get(indices[i]);
			if(analysis==null){
				continue;
			}
			if (suggested=analysis.getAverageResult()||analysis.getGradientResult()||analysis.getMarketCapResult()) {
				result+= "<td class=\"Header\">"+"<a href=\"/servlets/recommend?id="+indices[i]+"\">"+indices[i]+"</a></td>"
						+"<td class=\"Header\">"+analysis.getComment()+"</td>";
			} else {
				result+= "<td>"+"<a href=\"/servlets/recommend?id="+indices[i]+"\">"+indices[i]+"</a></td>"
						+"<td>"+analysis.getComment()+"</td>";
			}
			if(suggested){
				if(user.isInvested(indices[i])){
					result+=  "<td>You have invested this stock yet</td>";
				}else{
					suggested=false;
					result+="<td></td>";
				}
				if (user.isInterested(indices[i])) {
					result+= "<td class=\"Recommended\">Interested in</td>";
				} else{
					suggested=false;
					result+=  "<td></td>";
				}
			}else{
				if(user.isInvested(indices[i])){
					result+=  "<td>You have invested this stock</td>";
				}else{
					result+="<td></td>";
				}
				if (user.isInterested(indices[i])) {
					result+= "<td>Interested in</td>";
				} else{
					result+="<td></td>";
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
	
	/**
	 * 
	 * @param user
	 * @param index
	 * @return
	 */
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
		result = "<h1>Analysis of a single stock</h1><p><a href=\"/servlets/share-vis?id="+index+"\">"+index+"</a></p>";
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
		result+="<h3>"+analysis.getComment()+"</h3>"
				+"<table><tr>"+ "<th>Title</th>"+ "<th>Analyzed data</th>"
				+ "</tr><tr>";
		result+="<td>Last 25 weeks' price average</td><td>"
				+analysis.getFirstAverage()+"</td>"
				+"<tr><td>Last 50 weeks' price average</td><td>"
				+analysis.getSecondAverage()+"</td></tr>"

				
				+"<tr><td>Last 25 weeks' price slope</td><td>"
				+analysis.getFirstGradient()+"</td></tr>"
				+"<tr><td>Last 50 weeks' price slope</td><td>"
				+analysis.getSecondGradient()+"</td></tr>";
		if(analysis.getMarketCap()>0&&analysis.getMarketPrice()>0){
			result+="<tr><td>Market Capitalization (B)</td><td>"
					+analysis.getMarketCap()+"</td></tr>"
					+"<tr><td>Product of volume and current price (B)</td><td>"
					+analysis.getMarketPrice()+"</td></tr>";
		}
		result+="</table>";
		return result;
	}
	
	/**
	 * 
	 * @param user
	 * @param index
	 * @return
	 */
	public String getFullReport(Investor user,String index){
		HtmlWriter html = new HtmlWriter("Analysis");
		html.append(HtmlWriter.getCSS());
		html.append(HtmlWriter.getJavaScript());
		html.closeHead();
		html.append("<div id=\"header\" style=\"text-align:center; background-color:#71C6E2\">");
		html.append("<h1>CS3051 Stock Analyser</h1></div>");
		html.append("<p><a href=\"/servlets/profile\" style=\"font-size:20px\">Profile</a> &nbsp;&nbsp;&nbsp;");
		html.append("<a href=\"/servlets/overview\" style=\"font-size:20px\">Market Overview</a> &nbsp;&nbsp;&nbsp;");
		html.append("<a href=\"/servlets/stocks\" style=\"font-size:20px\">Stock Visualisation</a>&nbsp;&nbsp;&nbsp;");
		html.append("<a href=\"/servlets/recommend\" style=\"font-size:20px\">Recommended Stocks</a>&nbsp;&nbsp;&nbsp;");
		html.append("<input type=\"button\" value=\"Sign Out\" onclick=\"location.href='/servlets/signout'\"></p>");
		html.append(report(user,index));
		html.closeHtml();
		return html.getContent();
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
	

}
