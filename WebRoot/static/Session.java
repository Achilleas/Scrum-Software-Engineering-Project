package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.Investor;
import webpageOut.Profile;

public class Session extends HttpServlet{

	String username = "";
	String password = "";
	PrintWriter out;
			
	
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		System.out.println("here");
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		HttpSession s = servlet_request.getSession(false);
		System.out.println(s);
		
		if(s!=null){
			
		
		servlet_response.setContentType("text/plain"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		out.println("ALREADY SIGNED IN!");
		
		out.close(); 
		}
		


	}

	
}
