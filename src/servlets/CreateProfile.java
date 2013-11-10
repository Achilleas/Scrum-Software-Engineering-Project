package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import profile.InvestorProfile;
//import WriteOut.CreateError;

public class CreateProfile extends HttpServlet{
	
	String username;
	String password;
	String firstname;
	String surname;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	/**
	 * Used to deal with the users request
	 * @param servlet_request the request from the client
	 * @param servlet_response used to respond to the client
	 */
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		servlet_response.setContentType("text/html"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		PrintWriter out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		Map<String, String[]> parameter_map = servlet_request.getParameterMap();
		//creates a map - to gets the value entered by the user
		
		for (String name : parameter_map.keySet()) {
			for (String value : parameter_map.get(name)) {
				if(name.equals("Username"))
					username = value;
				if(name.equals("Password"))
					password = value;
				if(name.equals("Firstname"))
					firstname = value;
				if(name.equals("Surname"))
					surname = value;	
			}
				//out.println("<p>" + name + " = " + value + "</p>");
				//System.out.println("name: "+name+" value: "+value);
		}
		createNewProfile();
	}

		


	public void createNewProfile(){
		if(validUsername(username)){
			InvestorProfile ip = new InvestorProfile(username, password, firstname, surname);
			ip.storeAllDetails();
			System.out.println("valid");
		}
		else{
			System.out.println("invalid");
			//CreateError ce = new CreateError(out, "Error!!");
		}
	}
	
	boolean validUsername(String username){
		//TODO: SEE IF USERNAME BELOWS TO ANOTHER INVESTOR
		if(username.equals("abc"))
			return false;
		else
			return true;
	}
}
