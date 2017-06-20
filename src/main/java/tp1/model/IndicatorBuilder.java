package tp1.model;

import tp1.model.Parser.ParseFailedException;

public class IndicatorBuilder {
	@SuppressWarnings("serial")
	public class InvalidFormulaException extends Exception {
		public InvalidFormulaException(String message) {
			super(message);
		}
	}
	
	String name;
	String description;
	String formula;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setFormula(String formula){
		this.formula = formula;
	}


	public Indicator build() throws InvalidFormulaException {
		Parser parser = new Parser();
		Expression expression = null;
		
		try {
			expression = parser.parse(formula);
		} catch (ParseFailedException e) {
			throw new InvalidFormulaException(e.getMessage());
		}
		
		Indicator indicator = new Indicator(name, description, formula, expression);
		return indicator;
	}
}
