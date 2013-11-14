package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Investor;
import main.ProfileReader;
import main.ProfileWriter;
import webpageOut.Profile;

public class ProfileSignin extends HttpServlet {

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

		Map<String, String[]> parameter_map = servlet_request.getParameterMap();
		// creates a map - to gets the value entered by the user

		for (String name : parameter_map.keySet()) {
			for (String value : parameter_map.get(name)) {
				if (name.equals("Username"))
					username = value;
				if (name.equals("Password"))
					password = value;
				// System.out.println("name: "+name+" value: "+value);
			}
		}

		// Check User account exists
		if (ProfileWriter.checkDuplication(username)) {
			ProfileReader pr = new ProfileReader(",");
			Investor ip = pr.readProfile("Profiles\\" + username + ".txt");

			if (ip.verifyPassword(password)) {
				System.out.println("Password matches!");
			} else
				// Password was incorrect
				servlet_response.sendRedirect("/static/WrongPassword.html");
		} else
			// Profile doesn't exist
			servlet_response.sendRedirect("/static/NonExisting.html");

	}

}
