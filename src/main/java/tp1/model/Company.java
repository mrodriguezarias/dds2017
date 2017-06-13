package tp1.model;

public class Company implements Comparable<Company> {
	
	public static final Company EMPTY = new Company("");
	
	private String name;
	private String symbol;
	
	public Company(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.symbol.equals(((Company)obj).getName());
	}

	@Override
	public int compareTo(Company other) {
		return this.getName().compareTo(other.getName());
	}
}
