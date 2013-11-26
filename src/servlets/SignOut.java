package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Used to sign the investor out of the application
 * 
 */
public class SignOut extends HttpServlet {

	private static final long serialVersionUID = -2403528070614551707L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		HttpSession session = servlet_request.getSession(false);

		// checks if there is currently a session running
		if (session != null) {
			// if there is - the session will be ended
			session.invalidate();
		}
		servlet_response.sendRedirect("/static/HomePage.html");
	}
}
