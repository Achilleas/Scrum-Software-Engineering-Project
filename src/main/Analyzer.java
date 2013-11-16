package main;

import java.io.File;
import java.util.*;
import org.apache.commons.lang3.Validate;

/*
 * This class is the demo that analyze the data parsed based on user profile and generate html code when it it called.
 */
public class Analyzer {
	public static final int HIGH_GRANULARITY=1;
	public static final int LOW_GRANULARITY=2;
	private Investor user;
	private String result;
	private ArrayList<String> profitables;
	private LinkedList<Stock> historical_prices;
	public Analyzer(Investor user, File file, String separator){
		Validate.notNull(file, "firstName can't be null");
		Validate.notNull(user, "profile can't be null");
		CSVParser parser=new CSVParser();
		historical_prices=parser.parseHistoricalCSV(file,",");
		prices=parser.parseCSV(filename);
		this.user=user;
	}
	/*
	 * The actual process that can calculate and analyze the shares being received based on user's interest.
	 */
	public void analyze(){
	}
/*
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
					"</tr>"+
					"<tr>"+
						"<td class=\"Header\">001</td>"+
						"<td>status1</td>"+
						"<td class=\"Recommended\">Interested in</td>"+
					"</tr>"+
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
		Analyzer analyzer=new Analyzer(new Investor()," ",",");
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
