package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		System.out.println("here");
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		HttpSession session = servlet_request.getSession(false);
		System.out.println(session);
		
		if(session!=null){
		
		servlet_response.setContentType("text/plain"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		Investor ip = (Investor) session.getAttribute("user");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title> Individual Details </title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/Style.css\" />");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>ALREADY SIGNED IN AS USER: "+ip.getUsername()+"</h1>");
		out.println("<p>Name: "+ip.getFirstName()+"</p>");
		out.println("<p>Name: "+ip.getSurname()+"</p>");
		out.println("<a href=\"/servlets/share-vis\">Display</a>");
		out.println("</body>");
		out.println("</html>");
		out.close(); 
		}
	}
}
