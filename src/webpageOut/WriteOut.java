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
	
	/**
	 * The last part of the standard html code
	 */
	public void htmlEnd(){
		out.println("</body>");
		out.println("</html>");
	}
}
