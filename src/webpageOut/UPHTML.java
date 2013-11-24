package webpageOut;

import java.io.PrintWriter;
import java.util.ArrayList;

import main.Investor;

public class UPHTML extends WriteOut {

	public UPHTML(PrintWriter out) {
		this.out = out;
		// Set title of HTML output
		this.title = "Update Profile";
	}

	public void writeHTML(Investor ip) {
		htmlStart();
		writeHeader();
		writeForm(ip);
		htmlEnd();
	}

	public void writeForm(Investor ip) {
		out.println("<p>Update any fields you would like to make changes to.</p>");
		out.println("<p>Please note that your username and date of birth cannot be changed.</p>");
		out.println("<form name=\"form2\" method=\"post\" action =\"/servlets/register\">");
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

	public void hiddenValues(Investor ip){
		String day = Integer.toString(ip.getDateOfBirth().getDayOfMonth());
		String month = Integer.toString(ip.getDateOfBirth().getMonthOfYear());
		String year = Integer.toString(ip.getDateOfBirth().getYear());

		out.println("<input type=\"hidden\" name=\"Username\" value=\""+ip.getUsername()+"\">");
		out.println("<input type=\"hidden\" name=\"Day\" value=\""+day+"\">");
		out.println("<input type=\"hidden\" name=\"Month\" value=\""+month+"\">");
		out.println("<input type=\"hidden\" name=\"Year\" value=\""+year+"\">");
	}
	
	public void writePass() {
		out.println("<p>Old Password");
		out.println("<br /><input type=\"password\" name=\"OldPassword\" required /></p>");

		out.println("<p>New Password");
		out.println("<br /><input type=\"password\" name=\"Password\" required /></p>");
	}

	public void writeName(Investor ip) {
		out.println("<p>Name<br />");
		out.println("<input type=\"text\" name=\"Firstname\" value=\""
				+ ip.getFirstName() + "\" required />");
		out.println("<input type=\"text\" name=\"Surname\" value=\""
				+ ip.getSurname() + "\" required /></p>");
	}

	public void writeEmailPhone(Investor ip) {
		out.println("<p>E-mail Address");
		out.println("<br /><input type=\"email\" name=\"Email\" value=\""
				+ ip.getEmail() + "\"required /><br />");
		out.println("Home telephone");
		out.println("<br /><input type=\"tel\" name=\"Telephone\" value=\""
				+ ip.getTelephone() + "\"required");
		out.println("pattern=\"[0-9]{5}[ ][0-9]{6}\" title=\"Valid 11 digit number with space, for example: 01122 123456\"/><br />");
	}

	public void writeAddress(Investor ip) {
		out.println("<p>Address<br />");
		out.println("<input type=\"text\" name=\"Line1\" value=\""
				+ ip.getAddress().getString()[0] + "\" required /><br />");
		out.println("<input type=\"text\" name=\"Line2\" value=\""
				+ ip.getAddress().getString()[1] + "\"><br />");
		out.println("<input type=\"text\" name=\"Town\" value=\""
				+ ip.getAddress().getString()[2] + "\"required /><br />");
		out.println("<input type=\"text\" name=\"County\" value=\""
				+ ip.getAddress().getString()[3] + "\"/><br />");
		out.println("<input type=\"text\" name=\"Postcode\" value=\""
				+ ip.getAddress().getString()[4] + "\"required");
		out.println("pattern=\"[A-Z]{2}[0-9]{1,2}[ ][0-9]{1,2}[A-Z]{2}\"");
		out.println("title=\"Valid Postcode with a space, for example: KY16 9LY\"/><br />");
		out.println("<input type=\"text\" name=\"Country\" value=\""
				+ ip.getAddress().getString()[5] + "\" required /><br /></p>");
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
