package tp1.model;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;


@Observable
public class Metric implements Measure {
	
	public enum Type { METRIC, INDICATOR };
	
	protected Type type;
	
	@JsonProperty
	protected String name;
	
	@JsonProperty
	protected String description;
	
	protected String companyName;
	protected short period;
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
	private void setCompany(String name) {
		this.companyName = name;
	}
	
	@JsonSetter("period")
	private void setPeriodYear(short year) {
		this.period = year;
	}

	public String getName() {
		return name;
	}

	public double getValue(Company company, short period) {
		return value;
	}

	public String getCompany() {
		return companyName;
	}

	public short getPeriod() {
		return period;
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
		return ""; //fixme
		//return Util.significantDigits(getValue());
	}
}
