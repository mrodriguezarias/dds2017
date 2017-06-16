package tp1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.Util;
import tp1.model.Parser.ParseFailedException;

public class Indicator implements Measure {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String description;
	
	@JsonProperty
	private String formula;
	
	@JsonProperty
	private Expression expression;
	
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
	
	
	@JsonCreator
	public Indicator(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula,
			@JsonProperty("expression") Expression expression) {
		this.name = name;
		this.description = description;
		this.formula = formula;
		this.expression = expression; //testear
		
	}
	
	@Override
	public String toString() {
		return name.isEmpty() ? "[Indicador]" : name;
	}
	
	public String getFormula() {
		return formula;
	}
	
	public String getName(){
		return name;		
	}
	
	public String getDescription(){
		return description;
	}
	public void update(String name, String description, String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
	}
	
	public double getValue(Company company, short period){
		return expression.eval(company, period);		
	}
	/*public String getValueString() {
		try {
			return Util.significantDigits(tryGetValue());
		} catch(InvalidFormulaException e) {
			return "";
		}
	}*/
	
		public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.name.equals(((Indicator)obj).getName());
	}

	/*@Override
	public int compareTo(Indicator other) {
		return this.name.compareTo(other.getName());
	}*/
	
}
