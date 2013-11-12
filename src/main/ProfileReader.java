package main;
import java.io.*;
import java.util.*;
public class ProfileReader {
	private String separator;
	private Investor user;
	public ProfileReader(String separator){
		this.separator=separator;
	}
	private void readElement(String line){
		String[] elements=line.split(separator);
		int header=Integer.parseInt(elements[0]);
		switch(header){
		case Investor.FIRSTNAME:{
			user.setFirstName(elements[1]);
			break;
		}
		case Investor.SURNAME:{
			user.setSurName(elements[1]);
			break;
		}
		case Investor.PASSWORD:{
			user.changePassword("", elements[1]);
			break;
		}
		case Investor.INTERESTED:{
			for(int i=1;i<elements.length;i++){
				user.addInterested(elements[i]);
			}
			break;
		}
		case Investor.INVESTEDED:{
			for(int i=1;i<elements.length;i++){
				user.addInvested(elements[i]);
			}
			break;
		}
		case Investor.EMAIL:{
			user.setEmail(elements[1]);
			break;
		}
		default:{
			System.err.println("Unexpected header "+header);
		}
		}
	}
	public Investor readProfile(String filename){
		try{
			BufferedReader br=new BufferedReader(new FileReader(filename));
			user=new Investor();
			String line;
			while((line=br.readLine())!=null){
				System.out.println(line);
				readElement(line);
			}
			br.close();
		}catch(FileNotFoundException e1){
			System.out.println("Cannot find the file");
		}catch(IOException e2){
			e2.printStackTrace();
		}
		return user;
	}
}
