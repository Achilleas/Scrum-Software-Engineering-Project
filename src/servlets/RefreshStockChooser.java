package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webpageOut.StockListHTML;

/**
 * 
 * Refresh Stock List table only
 * 
 * @author jiaheng
 *
 */
public class RefreshStockChooser extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8877237117239698065L;
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
		
		HttpSession session = servlet_request.getSession(false);
		out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		if(session!=null && session.getAttribute("user")!=null){
	
			out = servlet_response.getWriter(); //creates writer
			//used to send the html page to the client
			
			StockListHTML s = new StockListHTML(out); //Write HTML
			s.writeTable();
		} else {
			out.write("");
		}
		out.close();
	}
}
