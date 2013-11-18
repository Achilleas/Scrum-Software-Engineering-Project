package main;
import java.util.*;
public class StockAnalysis {
	private String index;
	private boolean strategy_average;
	private boolean strategy_slope;
	private String comment;
	double first_average;
	double second_average;
	double first_gradient;
	double second_gradient;
	public StockAnalysis(String index){
		this.index=index;
		strategy_average=false;
		strategy_slope=false;
		comment="No particular significant comment.";
	}
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
			xy_sum+=timeline[i]*prices[i].getClose();
			x_mean+=timeline[i];
			y_mean+=prices[i].getClose();
		}
		x_mean/=n;
		y_mean/=n;
		coefficient=(xy_sum-n*x_mean*y_mean)/(xx_sum-n*x_mean*x_mean);
		return coefficient;
	}
	/**
	 * Calculate the average of a list of prices
	 */
	private double calculateDailyAverage(Stock[] prices) {
		double result = 0;
		for (int i = 0; i < prices.length; i++) {
			result += prices[i].getClose();
		}
		return result / prices.length;
	}
	private Stock[] convertToArray(LinkedList<Stock> list){
		Stock[] array=new Stock[list.size()];
		for(int i=0;i<array.length;i++){
			array[i]=list.pollFirst();
		}
		return array;
	}
	public void analyze(LinkedList<Stock> first_period_list,LinkedList<Stock> second_period_list){
		Stock[] first_period=convertToArray(first_period_list);
		Stock[] second_period=convertToArray(second_period_list);
		first_average = calculateDailyAverage(first_period);
		second_average = calculateDailyAverage(second_period);
		if(first_average>second_average*1.05){
			strategy_average=true;
			comment="Analysis based on period average is significant.";
		}
		first_gradient=linearRegression(first_period);
		second_gradient=linearRegression(second_period);
		if(first_gradient>second_gradient&&first_gradient>0&&second_gradient>0){
			strategy_slope=true;
			if(strategy_average){
				comment="Both slope and average analysis is significant!";
			}else{
				comment="Analysis based on period slope is significant.";
			}
		}
	}
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
	public boolean getAverageResult(){
		return strategy_average;
	}
	public boolean getGradientResult(){
		return strategy_slope;
	}
	public String getIndex(){
		return index;
	}
	public String getComment(){
		return comment;
	}
}
