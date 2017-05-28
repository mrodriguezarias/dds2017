package tp1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.Util;
import tp1.model.Parser.ParseFailedException;

public class Indicator extends Metric implements Comparable<Indicator> {
	
	public static final Indicator EMPTY = new Indicator("", "", "");
	
	@SuppressWarnings("serial")
	public class InvalidFormulaException extends Exception {
		private String cause;
		
		public InvalidFormulaException(String cause) {
			this.cause = cause;
		}
		
		@Override
		public String getMessage() { 
			return String.format("Fórmula inválida: %s.", cause);
		}
	}
	
	@JsonProperty
	private String formula;
	
	@JsonCreator
	public Indicator(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		super(name, description, 0);
		this.type = Type.INDICATOR;
		this.formula = formula;
		this.company = Company.EMPTY;
		this.period = Period.EMPTY;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setPeriod(Period period) {
		this.period = period;
	}
	
	@Override
	public String toString() {
		return name.isEmpty() ? "[Indicador]" : name;
	}
	
	public String getFormula() {
		return formula;
	}
	
	public void update(String name, String description, String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
	}
	
	public double tryGetValue() throws InvalidFormulaException {
		try {
			return new Parser(this).parse();
		} catch (ParseFailedException e) {
			throw new InvalidFormulaException(e.getMessage());
		}
	}
	
	@Override
	public String getValueString() {
		try {
			return Util.significantDigits(tryGetValue());
		} catch(InvalidFormulaException e) {
			return "";
		}
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.name.equals(((Indicator)obj).getName());
	}

	@Override
	public int compareTo(Indicator other) {
		return this.name.compareTo(other.getName());
	}
	
}
