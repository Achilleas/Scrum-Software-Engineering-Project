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

public class Main {

	public static void main(String[] args) {
		String file = "example_table.csv";
		BufferedReader r;
		String reg = ";";
		String line;
		LinkedList<DailyPrice> l = new LinkedList<DailyPrice>();

		try {
			r = new BufferedReader(new FileReader(file));
			r.readLine(); //skip first line
			while((line = r.readLine()) != null){
				String[] dp = line.split(reg);
				System.out.println(dp[0]);
				Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dp[0]);
				double open = Double.parseDouble(dp[1]);
				double high = Double.parseDouble(dp[2]);
				double low = Double.parseDouble(dp[3]);
				double close = Double.parseDouble(dp[4]);
				int volume = Integer.parseInt(dp[5]);
				DailyPrice dailyprice = new DailyPrice("THE STOCK NAME",date, open , high, low, close, volume);
				l.add(dailyprice);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
		} catch (ParseException e) {
			System.out.println("Date could not be parsed");
		} catch (NumberFormatException e) {
			System.out.println("Number format exception:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		}
		
		int a = 0;
		for(DailyPrice d: l){
			d.printDailyPrice();
			a++;
		}
		System.out.println(a);
	}

}
