package main;

import java.util.LinkedList;

import jetty.JettyServer;

public class Main {

	public static void main(String[] args) throws Exception {
		String file = "example_table.csv";
		
		LinkedList<DailyPrice> l;
		CSVParser p = new CSVParser(";");
		l = p.parseCSV(file);
		
		
		int a = 0;
		for(DailyPrice d: l){
			d.printDailyPrice();
			a++;
		}
		
		System.out.println(a);
		
		JettyServer js = new JettyServer();
		js.run(args);
	}

}
