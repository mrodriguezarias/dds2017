package tp1.modelo;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;


@Observable
public class Cuenta implements Medida {
	
	public enum Type { METRIC, INDICATOR };
	
	protected Type type;
	
	@JsonProperty
	protected String name;
	
	@JsonProperty
	protected String description;
	
	@JsonProperty
	protected String companyName;
	@JsonProperty
	protected short period;
	@JsonProperty
	private double value;
	
	@JsonCreator
	public Cuenta(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("companyName") String companyName,
			@JsonProperty("period") short period,
			@JsonProperty("value") double value
			) {
		this.type = Type.METRIC;
		this.name = name;
		this.description = description;
		this.companyName = companyName;
		this.period = period;
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
	
	
	
	public String obtenerNombre() {
		return name;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public double obtenerValor(Empresa company, short period) {
		return value;
	}
	
	public double getValue() {
		return value;
	}

	public short getPeriod() {
		return period;
	}
	
	public String obtenerDescripción() {
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
