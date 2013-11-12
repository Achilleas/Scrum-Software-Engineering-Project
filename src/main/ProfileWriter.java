package main;

import java.io.*;
import java.util.*;

/**------------------------------------------------------------------------------------------------
 * @version 1.1
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
	private void writeList(ArrayList<String> list,int header) throws IOException{
		bw.write(header+separator);
		for(int i=0;i<list.size()-1;i++){
			bw.write(list.get(i)+separator);
		}
		bw.write(list.get(list.size()-1));
		bw.newLine();
	}
	private void writeString(String content,int header) throws IOException{
		if(content!=null&&!content.equals("")){
			bw.write(header+separator+content);
			bw.newLine();
		}
	}
	/**
	 * Write a user's profile
	 * @param user
	 */
	public void writeProfile(String filename,Investor user){
		try{
			bw=new BufferedWriter(new FileWriter(filename));
			writeString(user.getSurname(),Investor.FIRSTNAME);
			writeString(user.getFirstName(),Investor.SURNAME);
			writeString(user.getPassword(),Investor.PASSWORD);
			writeString(user.getEmail(),Investor.EMAIL);
			writeString(user.getTelephone(),Investor.TELEPHONE);
			//pending......
			//writeString(user.getDateOfBirth().toString(),Investor.DATEOFBIRTH);
			writeString(user.getUsername(),Investor.USERNAME);
			//writeString(user.getAddress().toString(),Investor.ADDRESS);
			writeList(user.getInterestList(),Investor.INTERESTED);
			writeList(user.getInvestedList(),Investor.INVESTEDED);
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.err.println("Writing errors.");
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		
	}
}