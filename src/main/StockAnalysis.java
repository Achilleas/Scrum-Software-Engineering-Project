package main;
/**
 * 
 * @author Qiao
 * @version 1.2
 * @see Analyzer
 * This class analyze and held the results as well as the comments for individual stocks
 */
public class StockAnalysis {
	private String index;
	/**
	 * Three strategies
	 */
	private boolean strategy_average;
	private boolean strategy_slope;
	private boolean strategy_cap;
	/**
	 * Comment on the stock. (Match which strategy?)
	 */
	private String comment;
	/**
	 * Internal fields.
	 */
	private double first_average;
	private double second_average;
	private double first_gradient;
	private double second_gradient;
	private double market_cap;
	private double market_price;
	public StockAnalysis(String index){
		this.index=index;
		comment="";
	}
	/**
	 * Process linear regression
	 * @param prices
	 * @return coefficient of slope
	 */
	private double linearRegression(Stock[] prices){
		double[] timeline=new double[prices.length];
		for(int i=0;i<timeline.length;i++){
			timeline[i]=i+1;
		}
		double n=prices.length;
		double coefficient=0;
		double x_mean=0;
		double y_mean=0;
		double xx_sum=0;
		double xy_sum=0;
		for(int i=0;i<prices.length;i++){
			xx_sum+=timeline[i]*timeline[i];
			x_mean+=timeline[i];
			xy_sum+=timeline[i]*prices[i].getClose();
			y_mean+=prices[i].getClose();
		}
		x_mean/=n;
		y_mean/=n;
		coefficient=(xy_sum-n*x_mean*y_mean)/(xx_sum-n*x_mean*x_mean);
		return coefficient;
	}
	/**
	 * Calculate the average price of a list of stocks.
	 */
	private double calculateDailyAverage(Stock[] prices) {
		double result = 0;
		for (int i = 0; i < prices.length; i++) {
			result += prices[i].getClose();
		}
		return result / prices.length;
	}
	/**
	 * User three strategies to analyze 52 weeks' and 26 weeks' data
	 * @param first_period
	 * @param second_period
	 * @param market_cap
	 * @param latest
	 * @param volume
	 */
	public void analyze(Stock[] first_period,Stock[] second_period,double market_cap,double latest,double volume){
		//Calculate averages
		first_average = calculateDailyAverage(first_period);
		second_average = calculateDailyAverage(second_period);
		//Strategy: Check if the distribution of data is heavy at right side
		if(strategy_average=first_average>second_average*1.05){
			comment="Analysis based on period average is significant.";
		}
		//Strategy: Check if price is increasing by linear regression and if the increase is speeding.
		first_gradient=linearRegression(first_period);
		second_gradient=linearRegression(second_period);
		if(strategy_slope=first_gradient>second_gradient&&first_gradient>0&&second_gradient>0){
			if(strategy_average){
				comment+="<br>";
			}
			comment+="Analysis based on period slope is significant.";
		}
		/*
		 * Strategy: Check if the volume*price>market capitalization. This one does not work very well because all FTSE 100 fails to match this strategy.
		 * You are welcome to ignore this approach.
		 */
		this.market_cap=market_cap*1000000000;
		market_price=volume*latest;
		if(strategy_cap=market_cap>market_price){
			if(strategy_average||strategy_slope){
				comment+="<br>";
			}
			comment+="<br>Analysis based on market capitalization is significant.";
		}
	}
	//GETTERS
	public double getFirstAverage(){
		return first_average;
	}
	public double getSecondAverage(){
		return second_average;
	}
	public double getFirstGradient(){
		return first_gradient;
	}
	public double getSecondGradient(){
		return second_gradient;
	}
	public double getMarketCap(){
		return market_cap;
	}
	public double getMarketPrice(){
		return market_price;
	}
	public boolean getAverageResult(){
		return strategy_average;
	}
	public boolean getGradientResult(){
		return strategy_slope;
	}
	public boolean getMarketCapResult(){
		return strategy_cap;
	}
	public String getIndex(){
		return index;
	}
	public String getComment(){
		return comment;
	}
	//END OF GETTERS
}
