package tp1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.Util;

public class Indicator extends Metric {
	
	public static final Indicator EMPTY = new Indicator("", "", "");
	
	private String formula;
	
	@JsonCreator
	public Indicator(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		super(name, description, 0);
		this.type = Type.INDICATOR;
		this.formula = formula;
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
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getFormula() {
		return formula;
	}
	
	public double tryGetValue() throws Parser.ParseFailedException {
		Parser parser = new Parser();
		parser.setMetrics(Database.getInstance().getMetrics(company, period));
		parser.setIndicators(Database.getInstance().getIndicators(company, period));
		return parser.parse(getFormula());
	}
	
	@Override
	public String getValueString() {
		try {
			return Util.significantDigits(tryGetValue());
		} catch(Parser.ParseFailedException e) {
			return "";
		}
	}
	
}
