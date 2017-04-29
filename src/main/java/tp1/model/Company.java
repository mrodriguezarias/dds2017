package tp1.model;

public class Company {
	
	private String symbol;
	
	public Company(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return symbol.isEmpty() ? "(Empresa)" : symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
}
