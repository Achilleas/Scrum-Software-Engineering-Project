package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.LocalDate;

import webpageOut.StockListHTML;

import jetty.JettyServer;

public class Main {

	public static void main(String[] args) throws IOException{
		
		String[] str = {"January", "February", "March", "April","May","June", "July","August", "September","October","November", "December"};
		
		(new Thread(new FinanceQuery())).start();
		(new Thread(new StockListHTML(null))).start();
		JettyServer js = new JettyServer();
		try {
			js.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LocalDate fromDate = new LocalDate(2010,11,17);
		
		LocalDate toDate = new LocalDate();
		System.out.println(toDate.getYear());
		
		LinkedList<Stock> ll = FinanceQuery.getHistorical("BP", fromDate, toDate, "d");
		
		System.out.println(ll.getFirst().getId());

		Iterator<Stock> iterator = ll.iterator();
		
		
		PrintWriter write = new PrintWriter("Data.js");

		StringBuilder jsonData = new StringBuilder("");
		StringBuilder priceData = new StringBuilder("");
		StringBuilder volumeData = new StringBuilder("");
		StringBuilder summaryData = new StringBuilder("");
		int i=0;

		while(iterator.hasNext()){
			String jD = "";
			String pD = "";
			String vD = "";
			String sD = "";			
			Stock s = iterator.next();
			
			jD+= "{date:'"+str[s.getDate().getMonthOfYear()-1]+" "+s.getDate().getDayOfMonth()+", "+s.getDate().getYear()+"',";
			jD+= "open:"+s.getOpen()+",";
			jD+= "high:"+s.getHigh()+",";
			jD+= "low:"+s.getLow()+",";
			jD+= "close:"+s.getClose()+",";
			jD+= "volume:"+s.getVolume()+"}";
			//-----------------------------
			pD+= "["+(ll.size()-1-i)+","+s.getClose()+"]";
			//-----------------------------
			vD+= "["+(ll.size()-1-i)+","+s.getVolume()+"]";
			//-----------------------------
			if(i%14==0)
				sD+= "["+(ll.size()-1-i)+","+s.getClose()+"]";
			
			
			
			if(i!=0){
				jD+= ",";
				pD+= ",";
				vD+= ",";
				
			}
			if(i%14==1){
				sD+= ",";
			}
			i++;
			jsonData.insert(0,jD);
			priceData.insert(0, pD);
			volumeData.insert(0, vD);
			summaryData.insert(0, sD);
		}
		jsonData.insert(0,"var jsonData = [");
		priceData.insert(0, "var priceData = [");
		volumeData.insert(0, "var volumeData = [");
		summaryData.insert(0, "var summaryData = [");

		write.println(jsonData+"];");
		write.println(priceData+"];");
		write.println(volumeData+"];");
		write.println(summaryData+"];");
		
		write.close();
		
		System.out.println(ll.size());
	}

}
