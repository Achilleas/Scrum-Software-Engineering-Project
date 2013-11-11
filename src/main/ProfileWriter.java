package main;

import java.io.*;
import java.util.ArrayList;

/**------------------------------------------------------------------------------------------------
 * @version 1.0
 * ------------------------------------------------------------------------------------------------
 * @author Qiao
 * This class can write users' profile as well as their preferences stored in array list.
 * The separator and filename can be specified to the constructor.
 * ------------------------------------------------------------------------------------------------
 */
public class ProfileWriter {
	private String separator;
	private BufferedWriter bw;
	/**
	 * Constructor
	 * @param separator
	 */
	public ProfileWriter(String separator){
		this.separator = separator;
	}
	/**
	 * Write lists of stocks' name, each list will occupy a single line.
	 * Names of stocks are separated by predefined separator
	 * @param list
	 * @throws IOException
	 */
	private void writeList(ArrayList<String> list) throws IOException{
		if(list==null)
			return;
		for(int i=0;i<list.size()-1;i++){
			bw.write(list.get(i)+separator);
		}
		bw.write(list.get(list.size()-1));
		bw.newLine();
	}
	/**
	 * Write a user's profile
	 * @param user
	 */
	public void writeProfile(String filename,Investor user){
		System.out.println("Profile write");
		try{
			bw=new BufferedWriter(new FileWriter(filename));
			bw.write(user.getSurname()+separator);
			bw.write(user.getFirstName());
			bw.newLine();
			writeList(user.getInterestList());
			writeList(user.getInvestedList());
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.err.println("Writing errors.");
			e.printStackTrace();
		}
	}

}