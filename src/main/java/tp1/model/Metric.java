package tp1.model;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

@Observable
public class Metric {
	public enum Type {METRIC, INDICATOR};
	
	private Type type = Type.METRIC;
	private String name;
	private long value;
	private String company;
	private short period;
	
	@Override
	public String toString() {
		return String.format("%s for %s in %d: %(,d", name, company, period, value);
	}

	public String getName() {
		return name;
	}

	public long getValue() {
		return value;
	}

	public String getCompany() {
		return company;
	}

	public short getPeriod() {
		return period;
	}
	
	public Type getType() {
		return type;
	}
	
	@Dependencies("value")
	public String getValueString() {
		return String.format("%(,d", value);
	}
	
	@Dependencies("type")
	public String getTypeString() {
		return type == Type.INDICATOR ? "Indicador" : "Cuenta";
	}
	
}
