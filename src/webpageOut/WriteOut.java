package webpageOut;

import java.io.PrintWriter;

public class WriteOut {
	
	protected String title;
	protected PrintWriter out;
	
	/**
	 * The first part of the standard html code
	 */
	public void htmlStart(){
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>"+title+"</title>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../static/Style.css\" />");
		out.println("</head>");
		out.println("<body>");
	}
	
	public void writeHeader(){
		out.write("<div id=\"header\" style=\"text-align:center; background-color:#71C6E2\">");
		out.write("<h1>CS3098 Stock Analyser</h1></div>");
		out.write("<p><a href=\"/servlets/profile\" style=\"font-size:20px\">Profile</a> &nbsp;&nbsp;&nbsp;");
		out.write("<a href=\"/servlets/overview\" style=\"font-size:20px\">Market Overview</a> &nbsp;&nbsp;&nbsp;");
		out.write("<a href=\"/servlets/stocks\" style=\"font-size:20px\">Stock Visualisation</a>&nbsp;&nbsp;&nbsp;");
		out.write("<input type=\"button\" value=\"Sign Out\" onclick=\"location.href=\"/servlets/signout\";\"></p>");
	}
	
	/**
	 * The last part of the standard html code
	 */
	public void htmlEnd(){
		out.println("</body>");
		out.println("</html>");
	}
}
