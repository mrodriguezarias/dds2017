package tp1.viewModel;

import java.awt.Color;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.Util;
import tp1.model.Indicator;
import tp1.model.Metric;
import tp1.model.Metric.Type;

@Observable
public class MetricViewModel {
	
	private Metric metric;

	public MetricViewModel(Metric metric) {
		this.metric = metric;
	}
	
	public String title() {
		return String.format("%s %s de $%s en %s", metric.getTypeString(),
				metric.getName(), metric.getCompany(), metric.getPeriod()); 
	}
	
	@Dependencies("metric")
	public String getHeading() {
		if(metric.getType() == Metric.Type.INDICATOR) {
			return ((Indicator)metric).getFormula();
		}
		return String.format("%s(%s, %s)",
				metric.getName(), metric.getCompany(), metric.getPeriod());
	}

	@Dependencies("metric")
	public String getValue() {
		if(metric.getType() == Type.INDICATOR) return metric.getValueString();
		return Util.formatNumber(metric.getValue());
	}

	@Dependencies("metric")
	public String getDescription() {
		return metric.getDescription();
	}
	
	@Dependencies("metric")
	public Color getColour() {
		if(metric.getType() == Type.INDICATOR) return Util.BLUE_COLOUR;
		return metric.getValue() < 0 ? Util.RED_COLOUR : Util.GREEN_COLOUR; 
	}
	
}
