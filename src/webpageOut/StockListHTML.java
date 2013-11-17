package webpageOut;

import java.io.PrintWriter;

public class StockListHTML extends WriteOut {
	
	public StockListHTML(PrintWriter out){
		this.out=out;
		//Set title of HTML output
		this.title="Stock List";
		writeStockList();
	}

	private void writeStockList() {
		htmlStart();
		writeHeader();
		out.println("<p>List of current FTSE 100 Stocks goes here. <br/>");
		out.println("Users can then click on stock to look at, which will redirect to granularity visualisation. </p>");
		htmlEnd();
	}

}