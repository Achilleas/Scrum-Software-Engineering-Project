package main;

import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;

public class Stock {
	
	private LocalDate date; // date of the info
	private String id;
	private String name;
	private double latest; // Latest price
	private double open;
	private double high;
	private double low;
	private double previousClose; // Previous close price
	private int volume;
	private double marketCap; // market capitalization in billion

	public Stock(LocalDate date, String stock_id, String stock_name,
			double latest, double open, double high, double low,
			double previous_close, int volume, double marketCap) {

		Validate.notNull(stock_id);
		Validate.notNull(date);

		this.date = date;
		this.id = stock_id;
		this.name = stock_name;
		this.latest = latest;
		this.open = open;
		this.high = high;
		this.low = low;
		this.previousClose = previous_close;
		this.volume = volume;
		this.marketCap = marketCap;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getLatest() {
		return latest;
	}

	public double getOpen() {
		return open;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public int getVolume() {
		return volume;
	}

	public double getMarketCap() {
		return marketCap;
	}
}
