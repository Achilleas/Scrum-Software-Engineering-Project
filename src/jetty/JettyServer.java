package jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import servlets.*;

/**
 * @author cl72 Creates and runs the jetty server
 */
public class JettyServer {

	private static final int MAX_PORT = 65535; // the maximum port number a
												// server can run on
	private static final int MIN_PORT = 1024; // the minimum port number a
												// server can run on
	private static final int DEFAULT_PORT = 8282; // the port number which is
													// used to set up the Jetty
													// Server
	private static Server server; // will hold the server we are running

	public void run(String[] args) {

		WebAppContext handler1 = new WebAppContext(); // configuring the server
														// for the static pages
		// e.g. HomePage.html & register.html
		handler1.setContextPath("/static");
		handler1.setResourceBase("WebRoot/static/");

		ServletContextHandler handler2 = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		handler2.setContextPath("/servlets"); // configures the server for the
												// servlets
		// these are all the servlets that deal with requests from the user
		handler2.addServlet(new ServletHolder(new CreateProfile()), "/register");
		handler2.addServlet(new ServletHolder(new ProfileSignin()), "/signin");
		handler2.addServlet(new ServletHolder(new Session()), "/session");
		handler2.addServlet(new ServletHolder(new CheckUsername()), "/user-check");
		handler2.addServlet(new ServletHolder(new ProfilePage()), "/profile");
		handler2.addServlet(new ServletHolder(new SignOut()), "/signout");
		handler2.addServlet(new ServletHolder(new MarketOverview()), "/overview");
		handler2.addServlet(new ServletHolder(new RefreshOverview()), "/refreh-overview");
		handler2.addServlet(new ServletHolder(new StockChooser()), "/stocks");
		handler2.addServlet(new ServletHolder(new RefreshStockChooser()), "/refresh-stocks");
		handler2.addServlet(new ServletHolder(new VisShare()), "/share-vis");
		handler2.addServlet(new ServletHolder(new VisAllShare()), "/all-share-vis");
		handler2.addServlet(new ServletHolder(new UpdateProfile()), "/update");
		//RecommendShares rs = new RecommendShares();
		//rs.setUpAnalyzer(); // analysis of all the shares in the ftse 100
		//handler2.addServlet(new ServletHolder(rs), "/recommend");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { handler1, handler2 });

		server = new Server(getPort(args)); // creates server

		server.setHandler(handlers); // adds the handlers to the server

		try {
			server.start();// starts the server
		} catch (Exception e) {
			System.out
					.println("Problem connect to the server. System with terminate.");
			System.exit(1);
		}
	}

	/**
	 * Gets the port number that the server will connect on
	 * 
	 * @param args
	 *            the port numbers
	 * @return the port number that will be used for the server to connect on
	 */
	private static int getPort(String[] args) {
		int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
		if (port < MIN_PORT || port > MAX_PORT) {
			return -1;
		}
		return port;
	}

}
