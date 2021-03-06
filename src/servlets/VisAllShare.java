package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webpageOut.WriteOut;
import main.Constants;
import main.FinanceQuery;
import main.Stock;

/**
 * @author cl72
 * Used to visualise all the shares that are part of an index
 * HTML and associated javascript code is based on: http://mbostock.github.io/protovis
 */
/**
 * @author calumlaing
 * 
 */
public class VisAllShare extends HttpServlet {

	PrintWriter out; // will be used to write the response to the user

	private static final long serialVersionUID = 5878729264427584184L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	/**
	 * @param servlet_request
	 *            The index (e.g. FTSE100) which is to be visualised
	 * @param servlet_response
	 *            The visualisation of the data
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		HttpSession session = servlet_request.getSession(false);

		// checks an investor is signed into the system
		if (session == null || session.getAttribute("user") == null) {
			servlet_response.sendRedirect("/static/HomePage.html");
			return;
		}

		servlet_response.setContentType("text/html"); // the response will be of
														// the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); // and the HTTP
																// response code

		out = servlet_response.getWriter(); // creates writer
		// used to send the html page to the client

		// parameter to determine if the the ftse100 or the nasdaq100 are to be
		// displayed
		String sExchange = servlet_request.getParameter("exchange");
		String index = "";

		if (sExchange.equals(Constants.FTSE100)) {
			index = "FTSE 100";
		} else if (sExchange.equals(Constants.NASDAQ100)) {
			index = "NASDAQ 100";
		} else {
			return;
		}

		// get all the shares in an index
		String ex = FinanceQuery.getComponents(sExchange);
		// get all the shares in an index returned as a LinkedList
		LinkedList<Stock> list = FinanceQuery.getLatestPrice(ex);

		printOutWebpage(list, index);

	}

	/**
	 * Used to print out the graph of all the shares belonging to a certain
	 * index
	 * 
	 * @param list
	 *            list of all the shares in the index
	 * @param index
	 *            the index to visualise, e.g. FTSE100
	 */
	public void printOutWebpage(LinkedList<Stock> list, String index) {
		Iterator<Stock> it = list.iterator();

		WriteOut w = new WriteOut();
		w.setWriter(out);

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		// all the javascript files that are needed to visualise the shares
		out.println("<script src=\"../static/javascript/VisAllShare/protovis.js\" type=\"text/javascript\"></script>");
		out.println("<script src=\"../static/javascript/VisAllShare/jquery-1.4.2.min.js\" type=\"text/javascript\"></script>");
		out.println("<script src=\"../static/javascript/VisAllShare/jquery.tipsy.js\" type=\"text/javascript\"></script>");
		out.println("<script src=\"../static/javascript/VisAllShare/tipsy.js\" type=\"text/javascript\"></script>");
		out.println("<link href=\"../static/javascript/VisAllShare/tipsy.css\" type=\"text/css\" rel=\"stylesheet\"/>");
		out.println("<title>All Share Visualisation</title>");
		out.println("<style type=\"text/css\">");
		out.println("body {");
		out.println("font: 10px sans-serif;");
		out.println("}");

		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		w.writeHeader(); // Write the webpage header
		out.println("<h1>Visualisation of shares in " + index + "</h1>");
		out.println("<script type=\"text/javascript+protovis\">");
		out.println("var data = [");
		Stock stock;
		String stockID = "";
		int i = 0;
		double max = 0;
		// go through the list of stocks and add the latest price of each stock
		// the string (javascript array)
		while (it.hasNext()) {
			stock = it.next();
			if (i > 0) {
				out.print(",");
				stockID += ",";
				if (stock.getLatest() > max) {
					max = stock.getLatest();
				}
			} else {
				max = stock.getLatest();
			}
			i++;
			out.print(stock.getLatest());
			stockID = stockID + "\"" + stock.getId() + "\"";
		}
		// used to the range of the graph.
		// will be used round the value to the nearest 100
		// see below: (a+1)*100
		// i.e. if max is 1567, the group will range from 0 to 1600
		int a = (int) max / 100;

		out.print("],");
		out.println("share = [" + stockID + "],");
		out.println("w =1200,");
		out.println("h = 2000,");
		out.print("x = pv.Scale.linear(0," + ((a + 1) * 100) + ").range(0, w),");
		out.println("y = pv.Scale.ordinal(pv.range(" + list.size()
				+ ")).splitBanded(0, h, 4/5);");
		// used to format the output graph
		out.println("var vis = new pv.Panel().width(w).height(h).bottom(20).left(50).right(10).top(5);");
		out.println("var bar = vis.add(pv.Bar).data(data).top(function() y(this.index)).height(y.range().band).left(0).width(x).title(function(d) d.toFixed(2)).event(\"mouseover\", pv.Behavior.tipsy({gravity: \"w\", fade: true}));");
		out.println("bar.anchor(\"left\").add(pv.Label).textMargin(10).textAlign(\"right\").text(function() share[this.index]);");
		out.println("vis.add(pv.Rule).data(x.ticks()).left(function(d) Math.round(x(d)) - .5).strokeStyle(function(d) d ? \"rgba(0,0,0,.3)\" : \"#000\").add(pv.Rule).bottom(0).height(5).strokeStyle(\"#000\").anchor(\"bottom\").add(pv.Label).text(function(d) d.toFixed(1));");
		out.println("vis.render();");

		out.println("</script>");
		out.println("<div id=\"example\"></div>");
		out.println("</body>");
		out.println("</html>");
	}

}
