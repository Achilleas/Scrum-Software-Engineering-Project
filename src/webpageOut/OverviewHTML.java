package webpageOut;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;

import org.joda.time.LocalDate;

import main.Constants;
import main.FinanceQuery;
import main.Stock;

//Writes HTML for Market Overview
public class OverviewHTML extends WriteOut {
	
	
	
	public OverviewHTML(PrintWriter out){
		this.out=out;
		//Set title of HTML output
		this.title="Market Overview";
		writeOverview();
	}

	private void writeOverview() {
		htmlStart();
		writeHeader();
		out.println("<p>Dynamic Market Overview goes here. </p>");
		//writeStocks();
		htmlEnd();
	}
	
	private String id;
	private String name;
	private double latest; // Latest price
	private double open;
	private double high;
	private double low;
	private double close; // Previous close price
	private int volume;
	private double marketCap;
	
	private void tableHeading() {
		out.println("<table border =\"1\">");
		out.println("<tr>");
		out.println("<th>Stock ID</th>");
		out.println("<th>Stock Name</th>");
		out.println("<th>Opening Price</th>");
		out.println("<th>High Price</th>");
		out.println("<th>Low Price</th>");
		out.println("<th>Volume</th>");
		out.println("<th>Market Cap</th>");
		out.println("</tr>");
		
	}
	
	private void writeStocks() {
		tableHeading();
		FinanceQuery fq = new FinanceQuery();
		NavigableSet<String> ftseList = FinanceQuery.getFtseList();
		Iterator<String> it = ftseList.iterator();
		
		LocalDate today = new LocalDate();
		LocalDate yesterday = new LocalDate();
		yesterday = yesterday.withDayOfMonth((yesterday.getDayOfMonth()-1));
		
		String s;
		Stock stock;
		while(it.hasNext()){
			s = it.next();
			System.out.println(s);
			LinkedList<Stock> list = FinanceQuery.getHistorical(s, yesterday, today, Constants.DAILY_INTERVAL);
			if(list.isEmpty()) System.out.println("EMPTY!!!");
			//else System.out.println("LENGTH: "+list.size());
			/*stock = list.get(0);
			//Write Table values
			out.println("<tr>");
			out.println("<td>"+stock.getId()+"</td>");
			out.println("<td>"+stock.getName()+"</td>");
			out.println("<td>"+stock.getOpen()+"</td>");
			out.println("<td>"+stock.getHigh()+"</td>");
			out.println("<td>"+stock.getLow()+"</td>");
			out.println("<td>"+stock.getVolume()+"</td>");
			out.println("<td>"+stock.getMarketCap()+"</td>");
			out.println("</tr>");*/
		}
		
		out.println("</table>");
	}

}
