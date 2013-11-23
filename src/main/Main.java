package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.LocalDate;

import webpageOut.StockListHTML;
import jetty.JettyServer;

public class Main {

	public static void main(String[] args) throws IOException{
		
		//String[] str = {"January", "February", "March", "April","May","June", "July","August", "September","October","November", "December"};
		
		(new Thread(new FinanceQuery())).start();
		(new Thread(new StockListHTML(null))).start();
		JettyServer js = new JettyServer();
		try {
			js.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ftse = FinanceQuery.getComponents(Constants.FTSE100);
		LinkedList<Stock> list = FinanceQuery.getLatestPrice(ftse);

		Iterator<Stock> it = list.iterator();

		Stock stock;
		while (it.hasNext()) {
			stock = it.next();


		}
	}

}
