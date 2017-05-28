package tp1.model;

public class Period implements Comparable<Period> {
	
	public static final Period EMPTY = new Period((short)0);
	
	private short year;
	
	public Period(short year) {
		this.year = year;
	}
	
	public short getYear() {
		return year;
	}
	
	@Override
	public String toString() {
		return year == 0 ? "[Período]" : String.valueOf(year);
	}
	
	@Override
	public int hashCode() {
		return this.year;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.year == ((Period)obj).getYear();
	}

	@Override
	public int compareTo(Period other) {
		return this.year - other.year;
	}
}
