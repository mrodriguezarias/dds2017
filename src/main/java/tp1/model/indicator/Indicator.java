package tp1.model.indicator;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.model.Company;
import tp1.model.Measure;
import tp1.model.indicator.Parser.ParseFailedException;

public class Indicator implements Measure,Calculable {

	@JsonProperty
	private String name;

	@JsonProperty
	private String description;

	@JsonProperty
	private String formula;
	
	private Calculable calculable;

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
	@SuppressWarnings("serial")
	public class InvalidForContextException extends Exception {}
	
	
	@JsonCreator
	public Indicator(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
		try {
			this.calculable = new Parser().obtenerCalculable(formula);
		} catch (ParseFailedException e) {}		
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

	public double getValue(Company company, short period) { //fixme: arreglar esto
		if(!isValidForContext(company, period)) {
			try {
				throw new InvalidForContextException();
			} catch (InvalidForContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.calcular(company, period); 
	}

	@Override
	public double calcular(Company company, short period) {
		return calculable.calcular(company, period); //fixme reemplazar alguno de los 2 metodos :s
	}

	@JsonIgnore
	@Override
	public Set<String> getCuentas() {
		return calculable.getCuentas(); //fixme: quedarse con uno
	}
	
	public boolean isValidForContext(Company company, short period) {
		return company.getMetrics(period).stream()
				.map(m -> m.getName()).collect(Collectors.toSet())
				.containsAll(calculable.getCuentas());
	}
	
}
