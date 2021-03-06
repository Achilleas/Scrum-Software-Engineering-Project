package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.Investor;
import webpageOut.OverviewHTML;

/**
 * @author cwk4
 * Generates a dynamic market overview in tabular form
 */
public class MarketOverview extends HttpServlet {

	private static final long serialVersionUID = -7429721582887832078L;
	PrintWriter out;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		HttpSession session = servlet_request.getSession(false);

		// Check that a user is signed in and a session has been created for
		// them
		if (session != null && session.getAttribute("user") != null) {
			servlet_response.setContentType("text/html"); // the response will
															// be of the type
															// html
			servlet_response.setStatus(HttpServletResponse.SC_OK); // and the
																	// HTTP
																	// response
																	// code

			out = servlet_response.getWriter(); // creates writer
			// used to send the html page to the client

			Investor investor = (Investor) session.getAttribute("user");
			OverviewHTML o = new OverviewHTML(out);
			o.writeHTML(investor); // Writes HTML
			out.close();
		}
		// Otherwise, redirects to homepage
		else {
			servlet_response.sendRedirect("/static/HomePage.html");
		}
	}

}
