package test;

import java.util.ArrayList;

import main.Investor;
import main.ProfileWriter;

import org.junit.*;

public class ProfileWriterTests {
	String firstName = "Qiao";
	String surname = "Kang";
	Investor user;
	String password="123";
	@Before
	public void setupBefore() {
		System.out.println("r-------------------------------------------Test for profile writer-------------------------------------------");
		System.out.println("Setting up");
		user=new Investor(surname,firstName,password);
		user.addInterested("HSBA");
		user.addInterested("BARC");
		user.addInvested("BARC");
		user.addInvested("CMBA");
	}
	@Test
	public void normalTest(){
		System.out.println("Normal Test");
		ProfileWriter pw=new ProfileWriter(",");
		pw.writeProfile("example.txt", user);
	}
}
