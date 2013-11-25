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
	
	public void writeHTML() {
		htmlStart();
		out.println("<script>" +
				"var url = \"/servlets/refresh-stocks\";" +
				"var interval = 10000;" +
				"</script>");
		out.println("<script type=\"text/javascript\" src=\"/static/javascript/refresh-table-ajax.js\"></script>");
		writeHeader();
		out.println("<h1>Current FTSE100 & NASDAQ100 Shares</h1>");
		out.println("<p>Pick a share to visualise it over time.<br/>");
		out.println("Or alternatively, visualise all shares in the <a href=\"/servlets/all-share-vis?exchange=^FTSE\">FTSE-100</a> or the <a href=\"/servlets/all-share-vis?exchange=^NDX\">NASDAQ-100</a>.");
		out.println("<div id=\"content\">");
		out.println(getTable(FTSE100,"float:left; width:250px;"));
		out.println(getTable(NASDAQ100,"margin-left: 50%; width:250px;"));
		out.println("</div>");
		htmlEnd();
	}
	
	public void writeTable() {
		out.println(getTable(FTSE100,"float:left; width:250px;"));
		out.println(getTable(NASDAQ100,"margin-left: 50%; width:250px;"));
	}
	
	private String getTable(String index, String cssStyle) {
		NavigableSet<String> set = FinanceQuery.getComponentsList(index);
		Iterator<String> iterator = set.iterator();
		
		String tableContent = "<table border=\"1\"" +
				((cssStyle.equalsIgnoreCase(""))?
						"" : " style=\"" + cssStyle + "\"") +
				">";
		
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
		
		return tableContent;
	}
	
	private static String getStockName(String id) {
		String name;
		
		LinkedList<Stock> stockList = FinanceQuery.getLatestPrice(id);
		Stock stock = stockList.getFirst();
		
		name = stock.getName();
		stocks.putIfAbsent(id, name);		
		return name;
	}

	private void getAllStockName(String index){
		String components = FinanceQuery.getComponents(index);
		LinkedList<Stock> list = FinanceQuery.getLatestPrice(components);
		Iterator<Stock> iterator = list.iterator();
		
		while(iterator.hasNext()) {
			Stock stock = iterator.next();
			String id = stock.getId();
			String name = stock.getName();
			stocks.putIfAbsent(id, name);
		}
	}
	
	public void run() {
		System.out.println("StockListHTML : \t Update Stock List");
		getAllStockName(FTSE100);
		getAllStockName(NASDAQ100);
		System.out.println("StockListHTML : \t Done Update Stock List");
	}
}