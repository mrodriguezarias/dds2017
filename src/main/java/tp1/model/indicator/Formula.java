package tp1.model.indicator;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.model.Company;
import tp1.model.indicator.Parser.ParseFailedException;

public class Formula {
	
	@SuppressWarnings("serial")
	public class InvalidForContextException extends Exception {}
	
	@JsonProperty
	private String representation;
	private Expression expression;
	
	@JsonProperty
	private Set<String> metrics;
	
	/*
	 * Constructor privado que usa Jackson para deserializar la fórmula.
	 * Es necesario para que pueda crear el árbol de sintaxis (Expression) a partir de la fórmula,
	 * ya que como Expression es una interfaz funcional, no se puede guardar directamente en el archivo JSON.
	 */
	@JsonCreator
	private Formula(
			@JsonProperty("representation") String representation,
			@JsonProperty("metrics") Set<String> metrics) {
		this.representation = representation;
		try {
			this.expression = new Parser().getExpressionFromFormula(representation);
		} catch (ParseFailedException e) {}
		this.metrics = metrics;
	}
	
	public Formula(String representation, Expression expression, Set<String> metrics) {
		this.representation = representation;
		this.expression = expression;
		this.metrics = metrics;
	}
	
	public String asString() {
		return representation;
	}
	
	public Expression asExpression() {
		return expression;
	}
	
	public boolean isValidForContext(Company company, short period) {
		return company.getMetrics(period).stream().map(m -> m.getName()).collect(Collectors.toSet()).containsAll(metrics);
	}
	
	public double eval(Company company, short period) throws InvalidForContextException {
		if(!isValidForContext(company, period)) {
			throw new InvalidForContextException();
		}
		return expression.eval(company, period); 
	}
}
