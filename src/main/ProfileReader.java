package main;

import java.io.*;
import static main.Constants.*;
import org.joda.time.LocalDate;
import main.Constants;

public class ProfileReader {
	private String separator;

	/**
	 * ProfileReader constructor
	 * @param separator regex to be used
	 */
	public ProfileReader(String separator) {
		this.separator = separator;
	}

	private void readElement(String line, Investor user) {
		String[] elements = line.split(separator);
		int header = Integer.parseInt(elements[0]);
		switch (header) {
		case Constants.FIRSTNAME: {
			user.setFirstName(elements[1]);
			break;
		}
		case Constants.SURNAME: {
			user.setSurname(elements[1]);
			break;
		}
		case Constants.PASSWORD: {
			user.setPassword(elements[1]);
			break;
		}
		case Constants.INTERESTED: {
			for (int i = 1; i < elements.length; i++) {
				user.addInterested(elements[i]);
			}
			break;
		}
		case Constants.INVESTED: {
			for (int i = 1; i < elements.length; i++) {
				user.addInvested(elements[i]);
			}
			break;
		}
		case Constants.EMAIL: {
			user.setEmail(elements[1]);
			break;
		}
		case Constants.TELEPHONE: {
			user.setTelephone(elements[1]);
			break;
		}
		case Constants.USERNAME: {
			user.setUsername(elements[1]);
			break;
		}
		case Constants.DATEOFBIRTH: {
			user.setDob(LocalDate.parse(elements[1]));
			break;
		}
		case Constants.ADDRESS: {
			Address address = new Address(elements[1], elements[2],
					elements[3], elements[4], elements[5], elements[6]);
			user.setAddress(address);
			break;
		}
		default: {
			System.err.println("Unexpected header " + header);
		}
		}
	}

	/**
	 * 
	 * UPDATED (Jia Heng): change argument from filename to user.
	 * Previously, this method will return nothing if 
	 * the file/user not found 
	 * now it will RETURN NULL IF USER NOT FOUND
	 * 
	 * @param username
	 * @return
	 */
	public Investor readProfile(String username) {
		Investor user = new Investor();
		String filename = PROFILE_PATH + username + ".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				readElement(line, user);
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// user does not exist if file not found
			return null;
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return user;
	}
}
