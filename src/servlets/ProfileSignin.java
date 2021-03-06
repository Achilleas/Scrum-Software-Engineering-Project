package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.Investor;
import main.ProfileReader;
import main.ProfileWriter;

/**
 * @author cwk4 Checks username & password entered on login screen - if
 *         successful, redirects to user profile page
 */
public class ProfileSignin extends HttpServlet {

	private static final long serialVersionUID = -2301464661383582935L;
	String username = "";
	String password = "";
	PrintWriter out;

	protected void doPost(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		servlet_response.setContentType("text/html"); // the response will be of
														// the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); // and the HTTP
																// response code

		out = servlet_response.getWriter(); // creates writer
		// used to send the html page to the client

		// Get username + password parameters
		username = servlet_request.getParameter("Username");
		password = servlet_request.getParameter("Password");

		// Check User account exists
		if (ProfileWriter.checkDuplication(username)) {
			ProfileReader pr = new ProfileReader(",");
			Investor investor = pr.readProfile(username);

			// Check input password against password associated with username in
			// db
			if (investor.verifyPassword(password)) {
				// Create Session
				HttpSession session = servlet_request.getSession(true);
				session.setAttribute("user", investor);
				servlet_response.sendRedirect("/servlets/profile");
			} else
				// Password was incorrect
				servlet_response.sendRedirect("/static/WrongPassword.html");
		} else
			// Profile doesn't exist
			servlet_response.sendRedirect("/static/NonExisting.html");

	}

}
