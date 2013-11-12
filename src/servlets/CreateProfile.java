package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Address;
import main.DOB;
import main.Investor;
import main.ProfileWriter;
import profile.InvestorProfile;
import webpageOut.CreateError;
import webpageOut.Profile;
//import WriteOut.CreateError;

public class CreateProfile extends HttpServlet{
	
	String username="";
	String password="";
	String firstName="";
	String surname="";
	Date dateOfBirth;
	String email="";
	String telephone="";
	Address address;
	
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
					firstName = value;
				if(name.equals("Surname"))
					surname = value;	
				if(name.equals("Email"))
					email = value;
				if(name.equals("Telephone"))
					telephone = value;
				if(name.equals("Line1")){
					if(value==null) System.out.println("NULL!!!!");
					if(value==" ") System.out.println("QUOTES");
					if(value=="") System.out.println("QUOTES 2");
				}
				//System.out.println("name: "+name+" value: "+value);
			}
			
				//out.println("<p>" + name + " = " + value + "</p>");
				//System.out.println("name: "+name+" value: "+value);
		}
		createNewProfile(out);
	}

		


	public void createNewProfile(PrintWriter out) throws IOException{
		if(validDetails()){
			//TODO: Create New Investor Argument
			Investor ip = new Investor(username, password, dateOfBirth, firstName,
					surname, email, telephone, address);
			Profile pro = new Profile(out, ip);
			ProfileWriter pw = new ProfileWriter(",");
			pw.writeProfile("profileDB.csv",ip);
			//ip.storeAllDetails();
			System.out.println("valid");
		}
		else{
			System.out.println("invalid");
			CreateError ce = new CreateError(out, "Error!!");
		}
	}
	 
	boolean validDetails(){
		//TODO: SEE IF entered details are valid
		if(username.equals("abc"))
			return false;
		else
			return true;
	}
}
