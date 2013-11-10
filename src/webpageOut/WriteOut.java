package webpageOut;

import java.io.PrintWriter;

public class WriteOut {
	
	protected PrintWriter out;
	
	/**
	 * The first part of the standard html code
	 */
	public void htmlStart(){
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Individual Details </title>");
		out.println("</head>");
		out.println("<body>");
	}
	
	/**
	 * The second part of the standard html code
	 */
	public void htmlEnd(){
		out.println("</body>");
		out.println("</html>");
	}
}
