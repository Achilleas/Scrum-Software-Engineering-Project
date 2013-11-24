package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webpageOut.SessionHTML;
import main.Investor;

/**
 * @author cl72
 * Used to determine if the system has already been logged into by an investor
 */
public class Session extends HttpServlet{

	PrintWriter out; //will be used to send response to browser
			
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	/**
	 * Method used to deal with the session servlet request
	 * @param servlet_request 
	 * @param servlet_response
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response){
		
		//gets the session id for the system
		HttpSession session = servlet_request.getSession(false);
		
		if(session!=null){ //if the investor has already logged in
			servlet_response.setContentType("text/plain"); //the response will be of the type html
			servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code
			
			try {
				out = servlet_response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
			//display that the system is already logged into and which investor is logged in
			Investor ip = (Investor) session.getAttribute("user");
			SessionHTML sesOut = new SessionHTML(out);
			sesOut.writeHTML(ip);
		}
	}
}
