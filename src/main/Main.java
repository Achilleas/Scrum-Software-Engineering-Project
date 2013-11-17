package main;

import jetty.JettyServer;

public class Main {

	public static void main(String[] args) throws Exception {
		
		JettyServer js = new JettyServer();
		js.run(args);
	}
}
