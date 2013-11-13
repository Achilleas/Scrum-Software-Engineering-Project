package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;

import main.Address;
import main.Investor;
import main.ProfileWriter;
import webpageOut.CreateError;
import webpageOut.Profile;

//import WriteOut.CreateError;

public class CreateProfile extends HttpServlet {

	String username = "";
	String password = "";
	String firstName = "";
	String surname = "";
	LocalDate dateOfBirth;
	String email = "";
	String telephone = "";
	Address address;
	ArrayList<String> companiesInvested;
	ArrayList<String> companiesInterested;

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}

	/**
	 * Used to deal with the users request
	 * 
	 * @param servlet_request
	 *            the request from the client
	 * @param servlet_response
	 *            used to respond to the client
	 */
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {
		servlet_response.setContentType("text/html"); // the response will be of
														// the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); // and the HTTP
																// response code

		PrintWriter out = servlet_response.getWriter(); // creates writer
		// used to send the html page to the client

		Map<String, String[]> parameter_map = servlet_request.getParameterMap();
		// creates a map - to gets the value entered by the user

		address = new Address();
		dateOfBirth = new LocalDate();
		companiesInvested = new ArrayList<String>();
		companiesInterested = new ArrayList<String>();

		for (String name : parameter_map.keySet()) {
			for (String value : parameter_map.get(name)) {
				// Note: If no value entered for text boxes, sets as ""
				// =======USERNAME + NAME=========
				if (name.equals("Username"))
					username = value;
				if (name.equals("Password"))
					password = value;
				if (name.equals("Firstname"))
					firstName = value;
				if (name.equals("Surname"))
					surname = value;
				// =======DATE OF BIRTH=========
				if (name.equals("Day"))
					dateOfBirth = dateOfBirth.withDayOfMonth(Integer
							.parseInt(value));
				if (name.equals("Month"))
					dateOfBirth = dateOfBirth.withMonthOfYear(Integer
							.parseInt(value));
				if (name.equals("Year"))
					dateOfBirth = dateOfBirth.withYear(Integer.parseInt(value));
				// =======CONTACT INFO=========
				if (name.equals("Email"))
					email = value;
				if (name.equals("Telephone"))
					telephone = value;
				// =======ADDRESS=========
				if (name.equals("Line1"))
					address.setLine1(value);
				if (name.equals("Line2"))
					address.setLine2(value);
				if (name.equals("Town"))
					address.setTown(value);
				if (name.equals("County"))
					address.setCounty(value);
				if (name.equals("Postcode"))
					address.setPostcode(value);
				if (name.equals("Country"))
					address.setCountry(value);
				// =======COMPANIES=========
				if (name.equals("Invested"))
					parseCompanies(value, companiesInvested);
				if (name.equals("Interested"))
					parseCompanies(value, companiesInterested);
			}
		}
		createNewProfile(out);
	}

	public void createNewProfile(PrintWriter out) throws IOException {
		if (validDetails()) {
			// TODO: Create New Investor Argument
			Investor ip = new Investor(username, password, dateOfBirth,
					firstName, surname, email, telephone, address,
					companiesInvested, companiesInterested);
			Profile pro = new Profile(out, ip);
			ProfileWriter pw = new ProfileWriter(",");
			pw.writeProfile("profileDB.csv", ip);
			// ip.storeAllDetails();
			System.out.println("valid");
		} else {
			System.out.println("invalid");
			CreateError ce = new CreateError(out, "Error!!");
		}
	}

	public void parseCompanies(String str, ArrayList<String> list) {
		if (str == "") {
			str = "none";
		}
		String[] a = str.split(",");
		for (int i = 0; i < a.length; i++) {
			list.add(a[i]);
		}
	}

	boolean validDetails() {
		// TODO: SEE IF entered details are valid
		if (username.equals("abc"))
			return false;
		else
			return true;
	}
}
