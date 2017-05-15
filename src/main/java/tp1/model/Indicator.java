package tp1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Indicator {
	
	public static final Indicator EMPTY = new Indicator("", "", "");
	
	private String name;
	private String description;
	private String formula;
	
	@JsonCreator
	public Indicator(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
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
	
	public long compute(Company company, Period period) {
		return 0;
	}
	
}
