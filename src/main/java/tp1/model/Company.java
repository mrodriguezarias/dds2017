package tp1.model;

public class Company implements Comparable<Company> {
	
	public static final Company EMPTY = new Company("");
	
	private String symbol;
	
	public Company(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol.isEmpty() ? "[Empresa]" : symbol;
	}
	
	@Override
	public int hashCode() {
		return this.getSymbol().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.symbol.equals(((Company)obj).getSymbol());
	}

	@Override
	public int compareTo(Company other) {
		return this.getSymbol().compareTo(other.getSymbol());
	}
}
