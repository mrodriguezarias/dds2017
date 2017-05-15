package tp1.model;

public class Company {
	
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
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.getSymbol().equals(((Company)obj).getSymbol());
	}
}
