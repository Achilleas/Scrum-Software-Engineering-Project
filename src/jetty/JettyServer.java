package jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import servlets.*;

public class JettyServer {

	private static final int MAX_PORT = 65535; //the maximum port number a server can run on
	private static final int MIN_PORT = 1024; //the minimum port number a server can run on
	private static final int DEFAULT_PORT = 8282; //the port number which is used to set up the Jetty Server
	
	public void run(String[] args) throws Exception{
		
		WebAppContext handler1 = new WebAppContext(); //configuring the server for the static page
		handler1.setContextPath("/static"); //there will be only one static page - which gets the ID from the user
		handler1.setResourceBase("WebRoot/static/");
		
		ServletContextHandler handler2 = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler2.setContextPath("/servlets"); //configures the server for the servlet
		handler2.addServlet(new ServletHolder(new CreateProfile()), "/register"); 
		handler2.addServlet(new ServletHolder(new ProfileSignin()), "/signin");
		handler2.addServlet(new ServletHolder(new Session()), "/session");
		handler2.addServlet(new ServletHolder(new CheckUsername()), "/user-check");
		handler2.addServlet(new ServletHolder(new ProfilePage()), "/profile");
		handler2.addServlet(new ServletHolder(new SignOut()), "/signout");
		handler2.addServlet(new ServletHolder(new MarketOverview()), "/overview");
		handler2.addServlet(new ServletHolder(new StockChooser()), "/stocks");
		handler2.addServlet(new ServletHolder(new VisShare()), "/share-vis");
		handler2.addServlet(new ServletHolder(new RecommendShares()), "/recommend");
		
		/*WebAppContext handler3 = new WebAppContext();
		handler1.setContextPath("/jsp");
		handler1.setResourceBase("WebRoot/jsp/");*/
		
		HandlerList handlers = new HandlerList(); //stores all the handlers in an array
		handlers.setHandlers(new Handler[] { handler1, handler2 });

		Server server = new Server(getPort(args)); //creates the server
		server.setHandler(handlers); //adds the handlets to the server
		server.start(); //starts the server
	}

	/**
	 * Gets the port number that the server will connect on
	 * @param args the port numbers
	 * @return the port number that will be used for the server to connect on
	 */
	private static int getPort(String[] args) throws IllegalPortException{
		int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
		if (port < MIN_PORT || port > MAX_PORT) throw new IllegalPortException(args[0]);
		return port;
	}
}
