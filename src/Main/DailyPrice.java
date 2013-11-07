package Main;

import java.util.Date;

public class DailyPrice {
	private String stock_id;
	private Date date;
	private double open_price;
	private double close_price;
	private double high_price;
	private double low_price;
	private int volume;
	
	public DailyPrice(String stock_id,Date date,double open_price,double high_price,double low_price, double close_price, int volume){
		this.stock_id=stock_id;
		this.date=date;
		this.open_price=open_price;
		this.high_price=high_price;
		this.low_price=low_price;
		this.close_price=close_price;
		this.volume = volume;
	}
	public String getStockId() {
		return stock_id;
	}
	public double getLowPrice() {
		return low_price;
	}
	public double getHighPrice() {
		return high_price;
	}
	public double getOpenPrice() {
		return open_price;
	}
	public double getClosePrice() {
		return close_price;
	}
	public Date getDate() {
		return date;
	}
	
	public int getVolume(){
		return volume;
	}
	
	public void printDailyPrice(){
		System.out.print("Stock: " + stock_id + " ");
		System.out.print("Date: " + date.toString() + " ");
		System.out.print("Open Price: " + open_price + " ");
		System.out.print("High Price: " + high_price + " ");
		System.out.print("Low Price: " + low_price + " ");
		System.out.print("Close Price: " + close_price + " ");
		System.out.print("Volume: " + volume);
		System.out.println();	
	}
}
