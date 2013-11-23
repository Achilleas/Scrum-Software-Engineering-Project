package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webpageOut.SessionHTML;
import webpageOut.WriteOut;
import main.Investor;

public class Session extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5175782808795911731L;
	PrintWriter out;
			
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		HttpSession session = servlet_request.getSession(false);
		
		if(session!=null){
		
		servlet_response.setContentType("text/plain"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		Investor ip = (Investor) session.getAttribute("user");
		SessionHTML sesOut = new SessionHTML(out);
		sesOut.writeHTML(ip);
		}
	}
}
