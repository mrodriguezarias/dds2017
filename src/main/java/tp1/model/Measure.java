package tp1.model;

public interface Measure {
	
	public String getName();
	
	public String getDescription();
	
	public double getValue(Empresa company, short period);

}
