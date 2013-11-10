package webpageOut;

import java.io.PrintWriter;

public class InvestorProfile extends WriteOut{
	
	public InvestorProfile(PrintWriter out){
		this.out=out;
	}

	public void writeProfile(InvestorProfile ip){
		htmlStart();
		
		htmlEnd();
		out.close();
	}
	
	public void writeDetails(InvestorProfile ip){
		
	}
	
}
