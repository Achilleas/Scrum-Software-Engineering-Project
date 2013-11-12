package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Investor;
import webpageOut.Profile;

public class ProfileSignin extends HttpServlet{

	String username = "";
	String password = "";
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
				if(value.equals("username"))
					username = value;
				if(value.equals("password"))
					password=value;
				System.out.println("name: "+name+" value: "+value);
			}
			
		}
		validateUsernamePassword();

	}

	private void validateUsernamePassword() {
		// TODO validate user name and password
		Investor ip = null;
		if(true){
			Profile p = new Profile(out, ip);
		}
		
	}
	
}
