package main;

public final class Constants {
	
	public static final String FTSE100 = "^FTSE";
	public static final String DAILY_INTERVAL = "d";
	public static final String WEEKLY_INTERVAL = "w";
	public static final String MONTHLY_INTERVAL = "m";
	public static final String PROFILE_PATH = "profiles/";
			
	private Constants(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	}
}
