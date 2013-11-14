package test;

import main.*;

import org.joda.time.LocalDate;
import org.junit.*;

public class ProfileWriterTest {
	String firstName = "Qiao";
	String surname = "Kang";
	Investor user;
	String password="123";
	LocalDate date;
	Address address;
	
	@Before
	public void setupBefore() {
		System.out.println("r-------------------------------------------Test for profile writer-------------------------------------------");
		System.out.println("Setting up");
		user = new Investor(surname,firstName,password); 
		user.addInterested("HSBA");
		user.addInterested("BARC");
		user.addInvested("BARC");
		user.addInvested("CMBA");
		date=new LocalDate();
		user.setDob(date);
		address=new Address("address1","address2","address3","address4","address5","address6");
		user.setAddress(address);
	}
	
	@Test
	public void normalTest() throws UserExistException{
		System.out.println("Normal Test");
		ProfileWriter pw=new ProfileWriter(",");
		try {
			pw.writeProfile("example.txt", user,true);
		} catch (UserExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProfileReader pr=new ProfileReader(",");
		user=pr.readProfile("james");
		System.out.println("----------------------user's attributes----------------------");
		System.out.println(user.getFirstName());
		System.out.println(user.getSurname());
		System.out.println(user.getAddress().getString()[1]);
		System.out.println(user.getDateOfBirth());
	}
}
