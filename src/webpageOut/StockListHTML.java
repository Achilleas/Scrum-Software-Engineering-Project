package webpageOut;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentHashMap;

import main.FinanceQuery;
import main.Stock;
import static main.Constants.*;

public class StockListHTML extends WriteOut implements Runnable{
	
	private static ConcurrentHashMap<String, String> stocks = new ConcurrentHashMap<String, String>();
	
	public StockListHTML(PrintWriter out){
		this.out=out;
		//Set title of HTML output
		this.title="Stock List";
	}
	
	synchronized public void write() {
		
		NavigableSet<String> set = FinanceQuery.getComponentsList(FTSE100);
		Iterator<String> iterator = set.iterator();
		
		String tableContent = "<table border=\"1\">";
		
		// header
		tableContent += "<tr>" +
				"<th>Symbol</th>" +
				"<th>Name</th>" +
				"</tr>";
		
		while(iterator.hasNext()){
			String id = iterator.next();
			String name = stocks.get(id);
			if (name == null) {
				name = getStockName(id);
			}
			tableContent += "<tr>" +
					"<td>" + "<a href=\"/servlets/share-vis?id="+id+"\">"+id+"</a>" + "</td>" +
					"<td>" + name + "</td>" +
					"</tr>";
		}
		
		// table end tag
		tableContent += "</table>";
		htmlStart();
		writeHeader();
		out.println("<p>List of current FTSE 100 Stocks goes here. <br/>");
		out.println("<a href=\"/servlets/all-share-vis?exchange=^FTSE\">Display All</a>");
		out.println("Users can then click on stock to look at, which will redirect to granularity visualisation. </p>");
		out.println(tableContent);
		htmlEnd();
	}

	private static String getStockName(String id) {
		String name;
		
		LinkedList<Stock> stockList = FinanceQuery.getLatestPrice(id);
		Stock stock = stockList.getFirst();
		
		name = stock.getName();
		stocks.putIfAbsent(id, name);		
		return name;
	}

	synchronized public void run() {
		String ftse = FinanceQuery.getComponents(FTSE100);
		LinkedList<Stock> list = FinanceQuery.getLatestPrice(ftse);
		Iterator<Stock> iterator = list.iterator();
		
		System.out.println("StockListHTML : \t Update Stock List");
		while(iterator.hasNext()) {
			Stock stock = iterator.next();
			String id = stock.getId();
			String name = stock.getName();
			stocks.putIfAbsent(id, name);
		}
		System.out.println("StockListHTML : \t Done Update Stock List");
	}
}
