package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.Investor;
import webpageOut.UPHTML;

public class UpdateProfile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7700115661390031968L;
	PrintWriter out;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		HttpSession session = servlet_request.getSession(false);

		if (session != null && session.getAttribute("user") != null) {
			//the response will be of type html
			servlet_response.setContentType("text/html");
			//HTTP response code
			servlet_response.setStatus(HttpServletResponse.SC_OK); 
			
			//creates writer used to sent html page to client
			out = servlet_response.getWriter();

			Investor investor = (Investor) session.getAttribute("user");
			UPHTML up = new UPHTML(out); // Write HTML
			up.writeHTML(investor);
			out.close();
		} else {
			servlet_response.sendRedirect("/static/HomePage.html");
		}
	}

}
