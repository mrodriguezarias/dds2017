package tp1.model.indicator;

import tp1.model.indicator.Parser.ParseFailedException;

public class IndicatorBuilder {
	@SuppressWarnings("serial")
	public class InvalidFormulaException extends Exception {
		public InvalidFormulaException(String message) {
			super(message);
		}
	}
	
	String name;
	String description;
	String formulaAsString;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setFormula(String formula) {
		this.formulaAsString = formula;
	}


	public Indicator build() throws InvalidFormulaException {
		Parser parser = new Parser();
		Formula formula = null;
		
		try {
			formula = parser.parse(formulaAsString);
		} catch (ParseFailedException e) {
			throw new InvalidFormulaException(e.getMessage());
		}
		
		Indicator indicator = new Indicator(name, description, formula);
		return indicator;
	}
}
