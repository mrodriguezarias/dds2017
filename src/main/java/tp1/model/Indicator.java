package tp1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.model.Parser.ParseFailedException;

public class Indicator implements Measure {

	@JsonProperty
	private String name;

	@JsonProperty
	private String description;

	@JsonProperty
	private String formula;

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
	private Indicator(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
		try {
			this.expression = new Parser().parse(formula);
		} catch (ParseFailedException e) {
			e.printStackTrace();
		}
	}

	public Indicator(String name, String description, String formula, Expression expression) {
		this.name = name;
		this.description = description;
		this.formula = formula;
		this.expression = expression;
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

	public double getValue(Company company, short period) {
		return expression.eval(company, period);
	}
}
