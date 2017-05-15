package tp1.model;

public class Period {
	
	public static final Period EMPTY = new Period(0);
	
	private int year;
	
	public Period(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}
	
	@Override
	public String toString() {
		return year == 0 ? "[Período]" : String.valueOf(year);
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.getYear() == ((Period)obj).getYear();
	}
}
