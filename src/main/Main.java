package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.LocalDate;

import jetty.JettyServer;

public class Main {

	public static void main(String[] args) throws IOException{
		
		String[] str = {"January", "February", "March", "April","May","June", "July","August", "September","October","November", "December"};
		
		JettyServer js = new JettyServer();
		try {
			js.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LocalDate fromDate = new LocalDate(2012,11,17);

		LocalDate toDate = new LocalDate();
		System.out.println(toDate.getYear());
		
		LinkedList<Stock> ll = FinanceQuery.getHistorical("BP", fromDate, toDate, "d");

		Iterator<Stock> iterator = ll.iterator();
		
		
		PrintWriter write = new PrintWriter("Data.js");

		String jsonData = "var jsonData = [";
		String priceData = "var priceData = [";
		String volumeData = "var volumeData = [";
		String summaryData = "var summaryData = [";
		int i=0;

		while(iterator.hasNext()){
			if(i!=0){
				jsonData+= ",";
				priceData+= ",";
				volumeData+= ",";
				
			}
			if(i%14==1){
				summaryData+= ",";
			}
			
			Stock s = iterator.next();
			
			jsonData+= "{date:'"+str[s.getDate().getMonthOfYear()-1]+" "+s.getDate().getDayOfMonth()+", "+s.getDate().getYear()+"',";
			jsonData+= "open:"+s.getOpen()+",";
			jsonData+= "high:"+s.getHigh()+",";
			jsonData+= "low:"+s.getLow()+",";
			jsonData+= "close:"+s.getClose()+",";
			jsonData+= "volume:"+s.getVolume()+"}";
			//-----------------------------
			priceData+= "["+i+","+s.getClose()+"]";
			//-----------------------------
			volumeData+= "["+i+","+s.getVolume()+"]";
			//-----------------------------
			if(i%14==0)
				summaryData+= "["+i+","+s.getClose()+"]";
			
			i++;
		}
		write.println(jsonData+"];");
		write.println(priceData+"];");
		write.println(volumeData+"];");
		write.println(summaryData+"];");

		write.close();
	}

}
