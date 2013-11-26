package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webpageOut.StockListHTML;

//Generates List of Stocks currently in FTSE100 which a user can select to visualise
public class StockChooser extends HttpServlet {

	private static final long serialVersionUID = -3372993162487229776L;
PrintWriter out;
	
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		
		HttpSession session = servlet_request.getSession(false);
		
		if(session!=null && session.getAttribute("user")!=null){
			servlet_response.setContentType("text/html"); //the response will be of the type html
			servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code
	
			out = servlet_response.getWriter(); //creates writer
			//used to send the html page to the client
			
			StockListHTML s = new StockListHTML(out); //Write HTML
			s.writeHTML();
			out.close();
		} else {
			servlet_response.sendRedirect("/static/HomePage.html");	
		}
	}

}
