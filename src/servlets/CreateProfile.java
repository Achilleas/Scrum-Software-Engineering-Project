package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

//Takes values from registration form and creates an Investor object for user
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
        companiesInvested = new ArrayList<String>();
        companiesInterested = new ArrayList<String>();
		
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
		parseCompanies(value, companiesInvested);
		value = servlet_request.getParameter("Interested");
		parseCompanies(value, companiesInterested);
		
		Integer accessCount = new Integer(0);
		
		if (session.isNew()) {
		} else {
			Integer oldAccessCount =
			// Use getAttribute, not getValue, in version
			// 2.2 of servlet API.
			(Integer) session.getAttribute("accessCount");
			if (oldAccessCount != null) {
				accessCount = new Integer(oldAccessCount.intValue() + 1);
			}
		}
		// Use putAttribute in version 2.2 of servlet API.
		session.setAttribute("accessCount", accessCount);

		// Print Session Info
	    /*out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
	                "<H1 ALIGN=\"CENTER\">" + heading + "</H1>\n" +
	                "<H2>Information on Your Session:</H2>\n" +
	                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
	                "<TR BGCOLOR=\"#FFAD00\">\n" +
	                "  <TH>Info Type<TH>Value\n" +
	                "<TR>\n" +
	                "  <TD>ID\n" +
	                "  <TD>" + session.getId() + "\n" +
	                "<TR>\n" +
	                "  <TD>Creation Time\n" +
	                "  <TD>" + new Date(session.getCreationTime()) + "\n" +
	                "<TR>\n" +
	                "  <TD>Time of Last Access\n" +
	                "  <TD>" + new Date(session.getLastAccessedTime()) + "\n" +
	                "<TR>\n" +
	                "  <TD>Number of Previous Accesses\n" +
	                "  <TD>" + accessCount + "\n" +
	                "</TABLE>\n" +
	                "</BODY></HTML>");*/
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
			pw.writeProfile(ip, true);
		} catch (UserExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ip.storeAllDetails();
		System.out.println("valid");
		return ip;
	}

	// Method for parsing companies (given by Strings) from text area input
	private void parseCompanies(String str, ArrayList<String> list) {
		if (str == "") {
			str = "none";
		}
		str = removeWhiteSpace(str);
		String[] a = str.split(",");
		for (int i = 0; i < a.length; i++) {
			list.add(a[i]);
		}
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
