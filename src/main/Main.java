package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.LocalDate;

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
