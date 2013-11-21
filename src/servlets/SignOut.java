package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOut extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2403528070614551707L;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		HttpSession session = servlet_request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		servlet_response.sendRedirect("/static/HomePage.html");
	}
}
