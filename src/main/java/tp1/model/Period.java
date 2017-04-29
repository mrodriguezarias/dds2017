package tp1.model;

public class Period {
	
	private int year;
	
	public Period(int year) {
		this.year = year;
	}
	
	public String toString() {
		return year == 0 ? "(Período)" : String.valueOf(year);
	}
	
	public int getYear() {
		return year;
	}
}
