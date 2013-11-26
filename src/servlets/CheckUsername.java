package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ProfileReader;

/**
 * 
 * Check username availability
 * response true if the username is available
 * 
 * @author jiaheng
 *
 */
public class CheckUsername extends HttpServlet {

	private static final long serialVersionUID = -929638900476571970L;
	String username = "";
	PrintWriter out;
	
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		servlet_response.setContentType("text/html"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		username = servlet_request.getParameter("username");
			
		ProfileReader profileReader = new ProfileReader(",");
		
		if (profileReader.readProfile(username)!=null || username.equals("")) {
			out.write("false");
		} else {
			out.write("true");
		}
	}
}
