package Main;
/*
 * Please parse the data and put them in this format. Please also provide an interface that can return an array of objects of this class. so that I can use them.
 * Please put the date in integer format, i.e 20131106
 */
public class DailyPrice {
	private String index;
	private double open_price;
	private double close_price;
	private double high_price;
	private double low_price;
	private int date;
	public DailyPrice(String index,int date,double open_price,double close_price,double high_price,double low_price){
		this.index=index;
		this.open_price=open_price;
		this.close_price=close_price;
		this.high_price=high_price;
		this.low_price=low_price;
		this.date=date;
	}
	public String getIndex() {
		return index;
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
	public int getDate() {
		return date;
	}
	
}
