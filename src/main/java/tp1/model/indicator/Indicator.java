package tp1.model.indicator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.model.Company;
import tp1.model.Measure;
import tp1.model.indicator.Formula.InvalidForContextException;

public class Indicator implements Measure {

	@JsonProperty
	private String name;

	@JsonProperty
	private String description;

	@JsonProperty
	private Formula formula;

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
			@JsonProperty("formula") Formula formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
	}

	public Formula getFormula() {
		return formula;
	}

	public String getName(){
		return name;		
	}

	public String getDescription(){
		return description;
	}

	public double getValue(Company company, short period) {
		try {
			return formula.eval(company, period);
		} catch (InvalidForContextException e) {
			/*
			 * Esta excepción puede ignorarse en este contexto porque no debería pasar nunca.
			 */
			return 0;
		}
	}
}
