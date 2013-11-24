package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import webpageOut.WriteOut;
import main.Constants;
import main.FinanceQuery;
import main.Stock;

public class VisAllShare extends HttpServlet{

	protected void doGet(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws ServletException,
			IOException {
		processRequest(servlet_request, servlet_response);
	}
	
	private void processRequest(HttpServletRequest servlet_request,
			HttpServletResponse servlet_response) throws IOException {

		HttpSession session = servlet_request.getSession(false);
		
		if(session==null || session.getAttribute("user")==null){
			servlet_response.sendRedirect("/static/HomePage.html");
			return;
		}
		
		servlet_response.setContentType("text/html"); //the response will be of the type html
		servlet_response.setStatus(HttpServletResponse.SC_OK); //and the HTTP response code

		PrintWriter out = servlet_response.getWriter(); //creates writer
		//used to send the html page to the client
		
		String sExchange = servlet_request.getParameter("exchange");
		String index="";

		if(sExchange.equals(Constants.FTSE100)){
			index="FTSE 100";
		}
		else if(sExchange.equals(Constants.NASDAQ100)){
			index="NASDAQ 100";
		}
		
		String ftse = FinanceQuery.getComponents(sExchange);
		LinkedList<Stock> list = FinanceQuery.getLatestPrice(ftse);

		Iterator<Stock> it = list.iterator();

		WriteOut w = new WriteOut();
		w.setWriter(out);
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		  out.println("<head>");
		  out.println("<script type=\"text/javascript\" src=\"../static/Vis_Files/lib/flotr/base64.js\"></script>");
		    out.println("<script src=\"../static/VisAllShare/protovis.js\" type=\"text/javascript\"></script>");
		    out.println("<script src=\"../static/VisAllShare/jquery-1.4.2.min.js\" type=\"text/javascript\"></script>");
		    out.println("<script src=\"../static/VisAllShare/jquery.tipsy.js\" type=\"text/javascript\"></script>");
		    out.println("<script src=\"../static/VisAllShare/tipsy.js\" type=\"text/javascript\"></script>");
		    out.println("<link href=\"../static/VisAllShare/tipsy.css\" type=\"text/css\" rel=\"stylesheet\"/>");
		    out.println("<title>Vis All</title>");
		    out.println("<style type=\"text/css\">");

		out.println("body {");
		  out.println("font: 10px sans-serif;");
		out.println("}");

	    out.println("</style>");
	  out.println("</head>");
	  out.println("<body>");
	   w.writeHeader(); //Write Header
	   out.println("<h1>Visualisation of shares in "+index+"</h1>");
	  out.println("<script type=\"text/javascript+protovis\">");
	    out.println("var data = [");
	    
		Stock stock;
		String stockID="";
		int i=0;
		double max=0;
		while (it.hasNext()) {
			stock = it.next();
			if(i>0){
				out.print(",");
				stockID+=",";
				if(stock.getLatest()>max){
					max=stock.getLatest();
				}
			}
			else{
				max=stock.getLatest();
			}
			i++;
			
			out.print(stock.getLatest());
			stockID=stockID+"\""+stock.getId()+"\"";
		}
		
		int a = (int)max/100;
		
		System.out.println(a);
		
		out.print("],");
		out.println("share = ["+stockID+"],");
		out.println("w =1200,");
		out.println("h = 2000,");
		out.print("x = pv.Scale.linear(0,"+((a+1)*100)+").range(0, w),");
		out.println("y = pv.Scale.ordinal(pv.range("+list.size()+")).splitBanded(0, h, 4/5);");
		
		out.println("var vis = new pv.Panel().width(w).height(h).bottom(20).left(50).right(10).top(5);");

		out.println("var bar = vis.add(pv.Bar).data(data).top(function() y(this.index)).height(y.range().band).left(0).width(x).title(function(d) d.toFixed(2)).event(\"mouseover\", pv.Behavior.tipsy({gravity: \"w\", fade: true}));");

		out.println("bar.anchor(\"left\").add(pv.Label).textMargin(10).textAlign(\"right\").text(function() share[this.index]);");

		out.println("vis.add(pv.Rule).data(x.ticks()).left(function(d) Math.round(x(d)) - .5).strokeStyle(function(d) d ? \"rgba(0,0,0,.3)\" : \"#000\").add(pv.Rule).bottom(0).height(5).strokeStyle(\"#000\").anchor(\"bottom\").add(pv.Label).text(function(d) d.toFixed(1));");

		out.println("vis.render();");

		    out.println("</script>");
		    out.println("<div id=\"example\"></div>");
		  out.println("</body>");
		out.println("</html>");
		
	}
	
}
