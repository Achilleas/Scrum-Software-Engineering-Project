package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ProfileWriter;

/**
 * 
 * Check username availability
 * response true if the username is available
 * 
 * @author jiaheng
 *
 */
public class CheckUser extends HttpServlet {
	
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
		
		Map<String, String[]> parameter_map = servlet_request.getParameterMap();
		//creates a map - to gets the value entered by the user
		
		for (String name : parameter_map.keySet()) {
			for (String value : parameter_map.get(name)) {
				if(name.equals("username"))
					username = value;
			}
		}
			
		if (ProfileWriter.checkDuplication(username) || username.equals("")) {
			out.write("false");
		} else {
			out.write("true");
		}
	}
}
