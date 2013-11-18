package webpageOut;

import java.io.PrintWriter;

public class OverviewHTML extends WriteOut {
	
	public OverviewHTML(PrintWriter out){
		this.out=out;
		//Set title of HTML output
		this.title="Market Overview";
		writeOverview();
	}

	private void writeOverview() {
		htmlStart();
		writeHeader();
		out.println("<p>Dynamic Market Overview goes here. </p>");
		htmlEnd();
	}

}
