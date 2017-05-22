package tp1.model;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import tp1.Util;

@Observable
public class Metric {
	
	public enum Type { METRIC, INDICATOR };
	
	protected Type type;
	protected String name;
	protected String description;
	protected Company company;
	protected Period period;
	private double value;
	
	@JsonCreator
	public Metric(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("value") double value) {
		this.type = Type.METRIC;
		this.name = name;
		this.description = description;
		this.value = value;
	}
	
	@JsonSetter("company")
	private void setCompanySymbol(String symbol) {
		this.company = new Company(symbol);
	}
	
	@JsonSetter("period")
	private void setPeriodYear(short year) {
		this.period = new Period(year);
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	public String getCompany() {
		return company.getSymbol();
	}

	public short getPeriod() {
		return period.getYear();
	}
	
	public String getDescription() {
		return description;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getTypeString() {
		if(type == Type.INDICATOR) return "Indicador";
		return "Cuenta";
	}
	
	@Dependencies("value")
	public String getValueString() {
		return Util.significantDigits(getValue());
	}
}
