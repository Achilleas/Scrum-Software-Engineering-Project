package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VisShare extends HttpServlet{

	PrintWriter out;
			
	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		
		servlet_response.setContentType("text/html"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title> Share Data </title>");
		out.println("<link rel=\"stylesheet\" href=\"../js/finance.css\" type=\"text/css\" />");
		out.println("<script type=\"text/javascript\" src=\"../lib/prototype/prototype.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../lib/prototype/scriptaculous.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../lib/flotr/excanvas.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../lib/flotr/base64.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../lib/flotr/canvas2image.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../lib/flotr/canvastext.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../lib/flotr/flotr.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../js/HumbleFinance.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../js/data2.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"../js/demo.js\"></script>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"body-container\" >");
		out.println("<div id=\"header-container\"><div id=\"header\"></div></div>");
		out.println("<div id=\"links-container\"><div id=\"links\"></div></div>");
		out.println("<div id=\"content-container\"><div id=\"content\">");
		out.println("<body>");
		out.println("<h3>Share Data</h3>");
		out.println("<div id=\"humblefinance\" style=\"position: relative; margin: 40px 0px; width: 600px; border: 1px solid #99CCFF;\";></div>");
		out.println("</div></div>");
		out.println("<div id=\"footer-container\"><div id=\"footer\"></div></div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.close(); 
		}

}
