package webpageOut;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;

import org.joda.time.LocalDate;

import main.Constants;
import main.FinanceQuery;
import main.Investor;
import main.Stock;

//Writes HTML for Market Overview
public class OverviewHTML extends WriteOut {

	public OverviewHTML(PrintWriter out, Investor ip) {
		this.out = out;
		// Set title of HTML output
		this.title = "Market Overview";
		writeOverview(ip);
	}

	private void writeOverview(Investor ip) {
		htmlStart();
		writeHeader();
		tableHeading();
		writeStocks(ip);
		htmlEnd();
	}

	private void tableHeading() {
		out.println("<table border =\"1\">");
		out.println("<tr>");
		out.println("<th>Stock ID</th>");
		out.println("<th>Stock Name</th>");
		out.println("<th>Current</th>");
		out.println("<th>Opening Price</th>");
		out.println("<th>Change</th>");
		out.println("<th>High Price</th>");
		out.println("<th>Low Price</th>");
		out.println("<th>Volume</th>");
		out.println("<th>Market Cap</th>");
		out.println("</tr>");
	}

	private void writeStocks(Investor ip) {
		String ftse = FinanceQuery.getComponents(Constants.FTSE100);
		LinkedList<Stock> list = FinanceQuery.getLatestPrice(ftse);

		Iterator<Stock> it = list.iterator();

		Stock stock;
		while (it.hasNext()) {
			stock = it.next();

			// % Change of Current from Opening
			double percent = (stock.getLatest() - stock.getOpen())
					/ stock.getOpen();
			percent = Math.floor(100000 * percent + 0.5) / 1000;
			// Assumes positive, if negative - sets to red
			String change = "style=\"background-color:green\"";
			if (stock.getLatest() < stock.getOpen()) {
				change = "style=\"background-color:red\"";
			}
			// Highlight share if user is interested/invested
			String interested = "";
			if (ip.isInterested(stock.getName())
					|| ip.isInvested(stock.getName())) {
				interested = "style=\"background-color:#71C6E2\"";
			}

			// Write Table values
			out.println("<tr " + interested + ">");
			out.println("<td " + interested + ">" + stock.getId() + "</td>");
			out.println("<td>" + stock.getName() + "</td>");
			out.println("<td>" + stock.getLatest() + "</td>");
			out.println("<td>" + stock.getOpen() + "</td>");
			out.println("<td " + change + ">" + percent + "%</td>");
			out.println("<td>" + stock.getHigh() + "</td>");
			out.println("<td>" + stock.getLow() + "</td>");
			out.println("<td>" + stock.getVolume() + "</td>");
			out.println("<td>" + stock.getMarketCap() + "</td>");
			out.println("</tr>");
		}

		out.println("</table>");
	}
}
