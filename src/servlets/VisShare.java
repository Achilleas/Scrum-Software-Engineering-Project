package servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.FinanceQuery;
import main.Stock;

import org.joda.time.LocalDate;

import webpageOut.WriteOut;

/**
 * @author cl72 Used to visualise a single share HTML and associated javascript
 *         code is based on: http://www.humblesoftware.com/envision
 */
public class VisShare extends HttpServlet {

	private static final long serialVersionUID = 934297545825976921L;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		HttpSession session = servlet_request.getSession(false);
		// return to homepage if not sign in
		if (session == null || session.getAttribute("user") == null) {
			servlet_response.sendRedirect("/static/HomePage.html");
			return;
		}

		servlet_response.setContentType("text/html"); // the response will be of
														// the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); // and the HTTP
																// response code

		// gets the id of the share which has to be displayed
		String id = servlet_request.getParameter("id");

		writeDataFile(id);

		PrintWriter out = servlet_response.getWriter(); // creates writer
		// used to send the html page to the client

		WriteOut w = new WriteOut();
		w.setWriter(out);

		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title> Share Data - " + id + "</title>");
		out.println("<link rel=\"stylesheet\" href=\"../static/javascript/Vis_Files/finance.css\" type=\"text/css\" />");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/prototype.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/scriptaculous.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/excanvas.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/base64.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/canvas2image.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/canvastext.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/flotr.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/HumbleFinance.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/data-"
				+ id + ".js\"></script>");

		out.println("<script type=\"text/javascript\" src=\"../static/javascript/Vis_Files/demo.js\"></script>");
		out.println("</head>");
		out.println("<body>");

		w.writeHeader();

		out.println("<div id=\"body-container\" >");
		out.println("<div id=\"header-container\"><div id=\"header\"></div></div>");
		out.println("<div id=\"links-container\"><div id=\"links\"></div></div>");
		out.println("<div id=\"content-container\"><div id=\"content\">");
		out.println("<body>");
		out.println("<h3>Share Data - " + id + "</h3>");
		out.println("<div id=\"humblefinance\" style=\"position: relative; margin: 40px 0px; width: 600px; border: 1px solid #99CCFF;\";></div>");
		out.println("</div></div>");
		out.println("<div id=\"footer-container\"><div id=\"footer\"></div></div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	/**
	 * Creates the javascript file that contains all the data about the share
	 * that will be visualised
	 * 
	 * @param stock
	 *            the id of the stock that will be displayed
	 * 
	 */
	public void writeDataFile(String stock) throws FileNotFoundException {

		StringBuilder jsonData = new StringBuilder("");
		StringBuilder priceData = new StringBuilder("");
		StringBuilder volumeData = new StringBuilder("");
		StringBuilder summaryData = new StringBuilder("");

		String[] str = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };

		LocalDate fromDate = new LocalDate(2010, 11, 17);

		LocalDate toDate = new LocalDate();

		LinkedList<Stock> ll = FinanceQuery.getHistorical(stock, fromDate,
				toDate, "d");

		Iterator<Stock> iterator = ll.iterator();

		File file = new File("./WebRoot/static/javascript/Vis_Files/data-"
				+ stock + ".js");
		file.deleteOnExit();
		PrintWriter write = new PrintWriter(file);

		int i = 0;

		while (iterator.hasNext()) {
			String jD = "";
			String pD = "";
			String vD = "";
			String sD = "";
			Stock s = iterator.next();

			// will contain all the key points about the share
			jD += "{date:'" + str[s.getDate().getMonthOfYear() - 1] + " "
					+ s.getDate().getDayOfMonth() + ", "
					+ s.getDate().getYear() + "',";
			jD += "open:" + s.getOpen() + ",";
			jD += "high:" + s.getHigh() + ",";
			jD += "low:" + s.getLow() + ",";
			jD += "close:" + s.getClose() + ",";
			jD += "volume:" + s.getVolume() + "}";
			// -----------------------------
			// will store the closing price of the share over the range of time
			pD += "[" + (ll.size() - 1 - i) + "," + s.getClose() + "]";
			// -----------------------------
			// will store the volume sold of the share over the range in time
			vD += "[" + (ll.size() - 1 - i) + "," + s.getVolume() + "]";
			// -----------------------------
			// summary of the data, same as price data
			sD += "[" + (ll.size() - 1 - i) + "," + s.getClose() + "]";

			if (i != 0) {
				jD += ",";
				pD += ",";
				vD += ",";
				sD += ",";
			}

			i++;
			// add to the front of the string
			// [so that olds dates are first and newest dates are last]
			// when iterating over the list - new dates are done first
			jsonData.insert(0, jD);
			priceData.insert(0, pD);
			volumeData.insert(0, vD);
			summaryData.insert(0, sD);
		}
		jsonData.insert(0, "var jsonData = [");
		priceData.insert(0, "var priceData = [");
		volumeData.insert(0, "var volumeData = [");
		summaryData.insert(0, "var summaryData = [");

		write.println(jsonData + "];");
		write.println(priceData + "];");
		write.println(volumeData + "];");
		write.println(summaryData + "];");

		write.close();
	}
}
