package tp1.viewModel;

import java.awt.Color;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

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
				metric.getName(), metric.getCompanyName(), metric.getPeriod()); 
	}
	
	@Dependencies("metric")
	public String getHeading() {
//		if(metric.getType() == Metric.Type.INDICATOR) {
//			return ((Indicator)metric).getFormula();
//		}
		return String.format("%s(%s, %s)",
				metric.getName(), metric.getCompanyName(), metric.getPeriod());
	}

	@Dependencies("metric")
	public String getValue() {
		return "";
//		if(metric.getType() == Type.INDICATOR) return metric.getValueString();
//		return Util.formatNumber(metric.getValue());
	}

	@Dependencies("metric")
	public String getDescription() {
		return metric.getDescription();
	}
	
	@Dependencies("metric")
	public Color getColour() {
		if(metric.getType() == Type.INDICATOR) return Colour.BLUE.getValue();
		return metric.getValue() < 0 ? Colour.RED.getValue() : Colour.GREEN.getValue(); 
	}
	
}
