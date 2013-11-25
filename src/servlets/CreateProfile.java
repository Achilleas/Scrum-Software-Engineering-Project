package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;

import main.Address;
import main.Investor;
import main.ProfileWriter;
import main.UserExistException;

/**
 * @author Callum Kenny
 * Takes values from registration form and creates an Investor object for the user	
 */
public class CreateProfile extends HttpServlet {

	private static final long serialVersionUID = -1922391698617055744L;
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

	protected void doPost(HttpServletRequest servlet_request,
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
		HttpSession session = servlet_request.getSession(true);
		String value;
		
		// the response will be of the type html
		servlet_response.setContentType("text/html");
		// and the HTTP response code
		servlet_response.setStatus(HttpServletResponse.SC_OK);

		// creates writer used to send the html page to the client
		PrintWriter out = servlet_response.getWriter(); 

		address = new Address();
        dateOfBirth = new LocalDate();
		
		// Note: If no value entered for text boxes, sets as ""
        // =======USERNAME + NAME=========
		username = servlet_request.getParameter("Username");
		password = servlet_request.getParameter("Password");
		firstName = servlet_request.getParameter("Firstname");
		surname = servlet_request.getParameter("Surname");
		
        // =======DATE OF BIRTH=========
		value = servlet_request.getParameter("Day");
		dateOfBirth = dateOfBirth.withDayOfMonth(Integer
				.parseInt(value));
		value = servlet_request.getParameter("Month");
		dateOfBirth = dateOfBirth.withMonthOfYear(Integer
				.parseInt(value));
		value = servlet_request.getParameter("Year");
		dateOfBirth = dateOfBirth.withYear(Integer.parseInt(value));
		
		// =======CONTACT INFO=========
		email = servlet_request.getParameter("Email");
		telephone = servlet_request.getParameter("Telephone");

		// =======ADDRESS=========
		value = servlet_request.getParameter("Line1");
		address.setLine1(value);
		value = servlet_request.getParameter("Line2");
		address.setLine2(value);
		value = servlet_request.getParameter("Town");
		address.setTown(value);
		value = servlet_request.getParameter("County");
		address.setCounty(value);
		value = servlet_request.getParameter("Postcode");
		address.setPostcode(value);
		value = servlet_request.getParameter("Country");
		address.setCountry(value);
		
		// =======COMPANIES=========
		value = servlet_request.getParameter("Invested");
		companiesInvested=parseCompanies(value);
		value = servlet_request.getParameter("Interested");
		companiesInterested=parseCompanies(value);

		Investor ip = createNewProfile(out, session);
		session.setAttribute("user", ip);
		servlet_response.sendRedirect("/servlets/profile");
	}

	private Investor createNewProfile(PrintWriter out, HttpSession session) throws IOException {
		Investor ip = new Investor(username, password, dateOfBirth, firstName, 
				surname, email, telephone, address,
				companiesInvested, companiesInterested);
		ProfileWriter pw = new ProfileWriter(",");
		try {
			pw.writeProfile(ip, false);
		} catch (UserExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ip.storeAllDetails();
		return ip;
	}

	// Method for parsing companies (given by Strings) from text area input
	private ArrayList<String> parseCompanies(String str) {
		ArrayList<String> list=new ArrayList<String>();
		if (str == "") {
			str = "none";
		}
		str = removeWhiteSpace(str);
		String[] a = str.split(",");
		for (int i = 0; i < a.length; i++) {
			list.add(a[i]);
		}
		//Removes duplicates by converting to Set then back again
		//LinkedHashSet preserves ordering of list
		Set<String> set = new LinkedHashSet<String>(list);
		ArrayList<String> result = new ArrayList<String>(set);
		return result;
	}

	// Removes whitespace from text area input for companies
	private String removeWhiteSpace(String str) {
		String[] a = str.split("\\s+");
		String b = "";
		for (int i = 0; i < a.length; i++) {
			if (a[i].compareTo(",") != 0) {
				b += a[i];
				if (!a[i].contains(",")) {
					b += ",";
				}
			}
		}
		return b;
	}
}
