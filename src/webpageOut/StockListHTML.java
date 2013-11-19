package webpageOut;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;

import main.FinanceQuery;
import main.Stock;
import static main.Constants.*;

public class StockListHTML extends WriteOut {
	
	private static HashMap<String, String> stocks = new HashMap<String, String>();
	
	public static void initialise() {
		NavigableSet<String> set = FinanceQuery.getComponentsList(FTSE100);
		Iterator<String> iterator = set.iterator();
		
		System.out.println("Loading Stock List");
		System.out.println("Please wait...");
		while(iterator.hasNext()) {
			String id = iterator.next();
			getStockName(id);
		}
		System.out.println("done\n");
	}
	
	public StockListHTML(PrintWriter out){
		this.out=out;
		//Set title of HTML output
		this.title="Stock List";
		writeStockList();
	}

	private void writeStockList() {
		
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
				System.out.println(id + " is null");
				name = getStockName(id);
			}
			tableContent += "<tr>" +
					"<td>" + id + "</td>" +
					"<td>" + "<a href=\"/servlets/share-vis?name="+name+"\">"+name+"</a>" + "</td>" +
					"</tr>";
		}
		
		// table end tag
		tableContent += "</table>";
		htmlStart();
		writeHeader();
		out.println("<p>List of current FTSE 100 Stocks goes here. <br/>");
		out.println("Users can then click on stock to look at, which will redirect to granularity visualisation. </p>");
		out.println(tableContent);
		htmlEnd();
	}

	private static String getStockName(String id) {
		String name;
		
		LinkedList<Stock> stockList = FinanceQuery.getLatestPrice(id);
		Stock stock = stockList.getFirst();
		
		name = stock.getName();
		stocks.put(id, name);		
		return name;
	}
}