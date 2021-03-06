package test;

import static main.Constants.FTSE100;

import java.util.ArrayList;

import main.Analyzer;
import main.FinanceQuery;
import main.Investor;

import org.junit.*;
import java.io.*;

public class AnalyzerTest {
	String filename1="FullAnalysis.html";
	String filename2="SingleAnalysis.html";
	String firstname = "Qiao";
	String surname = "Kang";
	ArrayList<String> companiesInvested;
	ArrayList<String> companiesInterested;
	Investor user;
	String password="123";
	@BeforeClass
	public static void setupBeforeClass() {
		(new Thread(new FinanceQuery())).start();
	}
	
	@Before
	public void setupUser(){
		user=new Investor();
		user.setFirstName(firstname);
		user.setSurname(surname);
		user.setPassword(password);
		/*
		 * Add some random indices
		 */
		String[] indices = FinanceQuery.getComponents(FTSE100).split(",");
		for(int i=0;i<indices.length;i++){
			if(Math.random()<.5){
				user.addInterested(indices[i]);
			}
			if(Math.random()<.3){
				user.addInvested(indices[i]);
			}
		}
	}
	@Test
	public void overviewTest(){
		Analyzer analyzer = new Analyzer( ",");
		analyzer.analyze();
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(filename1));
			bw.write(analyzer.getFullReport(user, null));
			bw.flush();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	@Test
	public void singleTest(){
		Analyzer analyzer = new Analyzer( ",");
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(filename2));
			bw.write(analyzer.getFullReport(user,"GKN.L"));
			bw.flush();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
