package servlets;

import java.io.IOException;
import java.io.PrintWriter;
//import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webpageOut.ProfileHTML;

import main.Investor;
//import webpageOut.ProfileWrite;

public class ProfilePage extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -845318471326546043L;
	PrintWriter out;	
	
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		
		HttpSession session = servlet_request.getSession(false);
		System.out.println(session);
		
		if(session!=null && session.getAttribute("user")!=null){
			servlet_response.setContentType("text/html"); //the response will be of the type html
			servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code
	
			out = servlet_response.getWriter(); //creates writer
			//used to send the html page to the client
			
			
			Investor investor = (Investor) session.getAttribute("user");
			
			ProfileHTML pro = new ProfileHTML(out, investor);
			out.write("<input type=\"button\" value=\"Sign Out\" onclick=\"location.href='/servlets/signout';\">");
			out.close(); 
		} else {
			servlet_response.sendRedirect("/static/HomePage.html");	
		}
	}
}
