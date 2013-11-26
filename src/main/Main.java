package main;

import java.io.IOException;
import webpageOut.StockListHTML;
import jetty.JettyServer;

/**
 * Main method for starting up and running the application
 *
 */
public class Main {

	public static void main(String[] args) throws IOException{
		
		(new Thread(new FinanceQuery())).start();
		(new Thread(new StockListHTML(null))).start();
		//creates and runs the jetty server
		JettyServer js = new JettyServer();
		js.run(args);
	}

}
