package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.*;

public class RecommendShares extends HttpServlet {
	private Analyzer analyzer;
	/**
	 * 
	 */
	public void setUpAnalyzer(){
		analyzer=new Analyzer(",");
		analyzer.analyze();
	}
	private static final long serialVersionUID = 4068022209531276116L;
	PrintWriter out;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		HttpSession session = servlet_request.getSession(false);
		String index=servlet_request.getParameter("id");
		if (session != null && session.getAttribute("user") != null) {
			//the response will be of type html
			servlet_response.setContentType("text/html");
			//and the http response code
			servlet_response.setStatus(HttpServletResponse.SC_OK); 
			
			//creates writer used to send html page to the client
			out = servlet_response.getWriter();

			Investor investor = (Investor) session.getAttribute("user");
			String result=analyzer.getFullReport(investor, index);
			out.write(result);
			out.close();
		} else {
			servlet_response.sendRedirect("/static/HomePage.html");
		}
	}

}
