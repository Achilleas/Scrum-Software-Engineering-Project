package main;

import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;

/**
 * This class represents a stock of a day in the market
 */
public class Stock {
	
	private LocalDate date;
	private String id; 
	private String name;
	private double latest;
	private double open; 
	private double high;
	private double low;
	private double close;
	private int volume; 
	private double marketCap;

	/**
	 * The constructor of the Stock
	 * @param date date of the info
	 * @param stock_id id of stock
	 * @param stock_name name of stock
	 * @param latest Latest price
	 * @param open open price
	 * @param high high price
	 * @param low low price
	 * @param close Previous close price
	 * @param volume volume of stock
	 * @param market Cap market capitalization in billion
	 */
	public Stock(LocalDate date, String stock_id, String stock_name,
			double latest, double open, double high, double low,
			double close, int volume, double marketCap) {

		Validate.notNull(stock_id);
		Validate.notNull(date);

		this.date = date;
		this.id = stock_id;
		this.name = stock_name;
		this.latest = latest;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.marketCap = marketCap;
	}
	
	/**
	 * Stock date getter
	 * @return returns the date of the stock
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Stock id getter
	 * @return returns the id of the stock
	 */
	public String getId() {
		return id;
	}

	/**
	 * Stock name getter
	 * @return returns the name of the stock
	 */
	public String getName() {
		return name;
	}

	/**
	 * Latest stock price getter
	 * @return returns the latest stock price of the stock
	 */
	public double getLatest() {
		return latest;
	}

	/**
	 * Open price of stock getter
	 * @return returns the open price of the stock
	 */
	public double getOpen() {
		return open;
	}

	/**
	 * High stock price getter
	 * @return returns the highest stock price
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * Low stock price getter
	 * @return returns the lowest stock price
	 */
	public double getLow() {
		return low;
	}

	/**
	 * Closing stock price getter
	 * @return returns the closing stock price
	 */
	public double getClose() {
		return close;
	}
	
	/**
	 * Volume of stock getter
	 * @return returns the volume of the stock
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * Cap market capitalization getter
	 * @return returns the cap market capitalization of the stock
	 */
	public double getMarketCap() {
		return marketCap;
	}
}
