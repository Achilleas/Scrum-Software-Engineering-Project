package main;
//Date of Birth Class
public class DOB {
	private int day;
	private int month;
	private int year;
	
	public DOB(int day, int month, int year){
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int[] getBirthday() {
		int[] birthday_array = new int[3];
		birthday_array[0] = day;
		birthday_array[1] = month;
		birthday_array[2] = year;
		return birthday_array;
	}
}
