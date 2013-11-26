package main;

/**
 * Class containing constant values for use across whole program
 */
public final class Constants {
	
	public static final String FTSE100 = "^FTSE";
	public static final String NASDAQ100 = "^NDX";
	public static final String DAILY_INTERVAL = "d";
	public static final String WEEKLY_INTERVAL = "w";
	public static final String MONTHLY_INTERVAL = "m";
	public static final String PROFILE_PATH = "profiles/";
	//Constants for ProfileWriter and ProfileReader
	public static final int FIRSTNAME=0;
	public static final int SURNAME=1;
	public static final int INVESTED=2;
	public static final int INTERESTED=3;
	public static final int EMAIL=4;
	public static final int PASSWORD = 5;
	public static final int DATEOFBIRTH=6;
	public static final int ADDRESS=7;
	public static final int TELEPHONE=8;
	public static final int USERNAME=9;
			
	private Constants(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	}
}
