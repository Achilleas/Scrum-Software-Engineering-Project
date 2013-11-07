package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class CSVParser {
	
	private String separator;
	
	public CSVParser(String separator){
		this.separator = separator;
	}
	
	public CSVParser(){}
	
	public LinkedList<DailyPrice> parseCSV(String filename) {
		BufferedReader r = null;
		String line;
		LinkedList<DailyPrice> l = new LinkedList<DailyPrice>();

		try {
			r = new BufferedReader(new FileReader(filename));
			r.readLine(); // skip first line
			while ((line = r.readLine()) != null) {
				String[] dp = line.split(separator);
				DailyPrice dailyprice = getDailyPrice(dp);
				l.add(dailyprice);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
		} catch (NumberFormatException e) {
			System.out.println("Number format exception:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
					System.out.println("IO exception: " + e.getMessage());
				}
			}
		}
		return l;
	}

	private Date getDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
					.parse(date);
		} catch (ParseException e) {
			System.out.println("Date could not be parsed");
		}
		return null;
	}

	private DailyPrice getDailyPrice(String[] price_info) {
		Date date = getDate(price_info[0]);
		double open = Double.parseDouble(price_info[1]);
		double high = Double.parseDouble(price_info[2]);
		double low = Double.parseDouble(price_info[3]);
		double close = Double.parseDouble(price_info[4]);
		int volume = Integer.parseInt(price_info[5]);

		return new DailyPrice("THE STOCK NAME", date, open, high, low, close,
				volume);
	}
	
	public void setSeparator(String s){
		this.separator = s;
	}
}
