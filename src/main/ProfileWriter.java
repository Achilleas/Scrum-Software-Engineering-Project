package main;

import java.io.*;

import org.joda.time.LocalDate;
import java.util.*;

/**------------------------------------------------------------------------------------------------
 * @author Qiao
 *------------------------------------------------------------------------------------------------
 * @version 1.3
 * Support default path
 * Add functionality that can verify if a user exit.
 *------------------------------------------------------------------------------------------------
 * @version 1.2
 * Support writing address and date.
 * ------------------------------------------------------------------------------------------------
 * @version 1.1
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
	private void writeDate(Investor user,int header) throws IOException{
		LocalDate date=user.getDateOfBirth();
		if(date!=null){
			bw.write(header+separator);
			bw.write(date.toString());
			bw.newLine();
		}
	}
	private void writeAddress(Investor user,int header) throws IOException{
		Address address=user.getAddress();
		if(address!=null){
			bw.write(header+"");
			String[] lines=address.getString();
			for(int i=0;i<lines.length;i++){
				bw.write(separator+lines[i]);
			}
		}
	}
	/**
	 * Write a user's profile
	 * @param user
	 */
	public void writeProfile(String filename,Investor user){
		try{
			bw=new BufferedWriter(new FileWriter(filename));
			/*
			 * Write everything!
			 */
			writeString(user.getSurname(),Investor.FIRSTNAME);
			writeString(user.getFirstName(),Investor.SURNAME);
			writeString(user.getPassword(),Investor.PASSWORD);
			writeString(user.getEmail(),Investor.EMAIL);
			writeString(user.getTelephone(),Investor.TELEPHONE);
			writeString(user.getUsername(),Investor.USERNAME);
			writeDate(user,Investor.DATEOFBIRTH);
			writeList(user.getInterestList(),Investor.INTERESTED);
			writeList(user.getInvestedList(),Investor.INVESTEDED);
			writeAddress(user,Investor.ADDRESS);
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.err.println("Writing errors.");
			e.printStackTrace();
		}
	}
	/**
	 * Write an investor profile to local file system
	 * filename is the user name of user +".txt"
	 */
	public void writeProfile(Investor user){
		writeProfile("Profiles\\"+user.getUsername()+".txt",user);
	}
	/**
	 * This method check if a specific file exist
	 * @param filename
	 * @return
	 */
	public static boolean checkDuplication(String username){
		return new File("Profiles\\"+username+".txt").exists();
	}
	/**
	 * This method check if the profile of an investor exists
	 */
	public static boolean checkDuplication(Investor user){
		return checkDuplication(user.getUsername());
	}
	public static void main(String args[]){
		
	}
}