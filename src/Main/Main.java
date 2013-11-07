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
		
		LinkedList<DailyPrice> l;
		CSVParser p = new CSVParser(";");
		l = p.parseCSV(file);
		
		
		int a = 0;
		for(DailyPrice d: l){
			d.printDailyPrice();
			a++;
		}
		System.out.println(a);
	}

}
