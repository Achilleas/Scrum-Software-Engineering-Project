package webpageOut;

import java.io.PrintWriter;
import java.util.ArrayList;

import main.Investor;

/**
 * @author cwk4 Write HTML form for users to update their information through,
 *         containing their current details
 */
public class UPHTML extends WriteOut {

	String reqstar = "<span class=\"req\"> *</span>"; // Red asterisk indicating
														// field is required

	public UPHTML(PrintWriter out) {
		this.out = out;
		// Set title of HTML output
		this.title = "Update Profile";
	}

	public void writeHTML(Investor ip) {
		htmlStart2(ip);
		writeHeader();
		writeForm(ip);
		htmlEnd();
	}

	public void writeForm(Investor ip) {
		out.println("<p>Update any fields you would like to make changes to.</p>");
		out.println("<p>Please note that your username and date of birth cannot be changed.</p>");
		out.println("<form name=\"form2\" onsubmit=\"return checkPass()\" method=\"post\" action =\"/servlets/register\">");
		out.println("<h1>Update Details</h1>");
		out.println("<p>Username: " + ip.getUsername() + "</p>");
		hiddenValues(ip);
		writePass();
		out.println("<h2>Personal Info</h2>");
		writeName(ip);
		out.println("<p>Date of Birth: " + ip.getDateOfBirth() + "</p>");
		out.println("<h2>Contact Info</h2>");
		writeEmailPhone(ip);
		writeAddress(ip);
		out.println("<h2>Companies</h2>");
		writeCompanies(ip);
		out.println("<input type=\"submit\" name=\"Action\" value=\"Update Details\" />");
		out.println("<input type=\"reset\" value=\"Reset\" />");
		out.println("</form>");
	}

	// Writes hidden form values for date of birth
	public void hiddenValues(Investor ip) {
		String day = Integer.toString(ip.getDateOfBirth().getDayOfMonth());
		String month = Integer.toString(ip.getDateOfBirth().getMonthOfYear());
		String year = Integer.toString(ip.getDateOfBirth().getYear());

		out.println("<input type=\"hidden\" name=\"Username\" value=\""
				+ ip.getUsername() + "\">");
		out.println("<input type=\"hidden\" name=\"Day\" value=\"" + day
				+ "\">");
		out.println("<input type=\"hidden\" name=\"Month\" value=\"" + month
				+ "\">");
		out.println("<input type=\"hidden\" name=\"Year\" value=\"" + year
				+ "\">");
	}

	public void htmlStart2(Investor ip) {
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>" + title + "</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../static/Style.css\" />");
		writeJS(ip);
		out.println("</head>");
		out.println("<body>");
	}

	// Javascript function for checking password entries
	public void writeJS(Investor ip) {
		out.println("<script type=\"text/javascript\">\n"
				+ "function checkPass() {\n"
				+ "var op = document.getElementById('op');\n"
				+ "var pass = document.getElementById('p1');\n"
				+ "var pass2 = document.getElementById('p2');\n"
				+ "var check = "
				+ ip.getPassword()
				+ "\n"
				+

				// If no values entered, keeps original password
				"if(op.value==\"\"&&pass.value==\"\"&&pass2.value==\"\"){\n\n"
				+ "document.getElementById('p1').value= check;\n"
				+ "return true;\n"
				+ "}\n\n"
				+

				// If value entered for old password doesn't match, return error
				"else if(op.value!=check){\n"
				+ "alert(\"Old Password is incorrect!\");\n"
				+ "return false;\n"
				+ "}\n\n"
				+

				// If old password field is filled in but new ones left empty,
				// return error
				"else if(pass2.value==\"\"&&pass.value==\"\") {\n"
				+ "alert(\"No values entered for new password!\");\n"
				+ "return false;\n"
				+ "}\n\n"
				+

				// If new passwords don't match, return error
				"else if(pass2.value != pass.value) {\n"
				+ "alert(\"Passwords do not match!\");\n"
				+ "return false;\n"
				+ "}\n\n" +

				// Otherwise, all OK
				"else{\n" + "return true;\n" + "}\n}\n" + "</script>\n");
	}

	public void writePass() {
		out.println("<p>Old Password");
		out.println("<span class=\"sub\">(leave all blank if you do not wish to change)</span>");
		out.println("<br /><input type=\"password\" name=\"OldPassword\" id=\"op\" /></p>");

		out.println("<p>New Password");
		out.println("<span class=\"sub\">(6-14 digits length, include numbers, symbols, and different case letters)</span>");
		out.println("<br /><input type=\"password\" name=\"Password\" id=\"p1\" pattern=\".{6,14}\"/></p>");

		out.println("<p>Confirm New Password");
		out.println("<br /><input type=\"password\" name=\"Password2\" id=\"p2\" pattern=\".{6,14}\"/></p>");
	}

	public void writeName(Investor ip) {
		out.println("<p>Name" + reqstar + "<br />");
		out.println("<input type=\"text\" name=\"Firstname\" value=\""
				+ ip.getFirstName() + "\" required pattern=\"[A-Za-z]{1,}\"/>");
		out.println("<input type=\"text\" name=\"Surname\" value=\""
				+ ip.getSurname()
				+ "\" required pattern=\"[A-Za-z]{1,}\"/></p>");
	}

	public void writeEmailPhone(Investor ip) {
		out.println("<p>E-mail Address" + reqstar);
		out.println("<br /><input type=\"email\" name=\"Email\" value=\""
				+ ip.getEmail() + "\"required /><br />");
		out.println("Home telephone" + reqstar);
		out.println("<br /><input type=\"tel\" name=\"Telephone\" value=\""
				+ ip.getTelephone() + "\"required");
		out.println("pattern=\"[0-9]{5}[ ][0-9]{6}\" title=\"Valid 11 digit number with space, for example: 01122 123456\"/><br />");
	}

	public void writeAddress(Investor ip) {
		out.println("<p>Address<br />");
		// Line 1
		out.println("<input type=\"text\" name=\"Line1\" value=\""
				+ ip.getAddress().getAddString()[0]
				+ "\" required pattern=\"[0-9A-Za-z ]{1,}\"/>" + reqstar
				+ "<br />");
		// Line 2
		out.println("<input type=\"text\" name=\"Line2\" value=\""
				+ ip.getAddress().getAddString()[1]
				+ "\" pattern=\"[0-9A-Za-z ]{0,}\"><br />");
		// Town
		out.println("<input type=\"text\" name=\"Town\" value=\""
				+ ip.getAddress().getAddString()[2]
				+ "\"required pattern=\"[.,A-Za-z ]{1,}\"/>" + reqstar
				+ "<br />");
		// County
		out.println("<input type=\"text\" name=\"County\" value=\""
				+ ip.getAddress().getAddString()[3]
				+ "\" pattern=\"[A-Za-z ]{0,}\"/><br />");
		// Postcode
		out.println("<input type=\"text\" name=\"Postcode\" value=\""
				+ ip.getAddress().getAddString()[4] + "\"required");
		out.println("pattern=\"[A-Z]{2}[0-9]{1,2}[ ][0-9]{1,2}[A-Z]{2}\"");
		out.println("title=\"Valid Postcode with a space, for example: KY16 9LY\"/>"
				+ reqstar + "<br />");
		// Country
		out.println("<input type=\"text\" name=\"Country\" value=\""
				+ ip.getAddress().getAddString()[5]
				+ "\" required pattern=\"[A-Za-z ]{1,}\" />" + reqstar
				+ "<br /></p>");
	}

	public void writeCompanies(Investor ip) {
		out.println("<p>Companies Invested in<br />");
		out.println("<textarea id=\"textarea\" name=\"Invested\" style=\"resize:none; maxlength:200; height:100px; width:300px\">");
		out.println(listToString(ip.getCompaniesInvested())
				+ "</textarea><br />");

		out.println("<p>Companies Interested in<br />");
		out.println("<textarea id=\"textarea\" name=\"Interested\" style=\"resize:none; maxlength:200; height:100px; width:300px\">");
		out.println(listToString(ip.getCompaniesInterested())
				+ "</textarea></p>");
	}

	// Transforms an ArrayList of Strings into one String with comma separated
	// entries
	public String listToString(ArrayList<String> l) {
		String s = "";
		for (int i = 0; i < l.size(); i++) {
			s += l.get(i);
			if (i != (l.size() - 1))
				s += ",";
		}
		return s;
	}

}
