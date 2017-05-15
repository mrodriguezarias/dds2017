package tp1.viewModel;

import java.awt.Color;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.Util;
import tp1.model.Metric;

@Observable
public class MetricViewModel {
	
	private Metric metric;

	public MetricViewModel(Metric metric) {
		this.metric = metric;
	}
	
	public String title() {
		return String.format("Cuenta %s de $%s en %d",
				metric.getName(), metric.getCompany(), metric.getPeriod()); 
	}
	
	@Dependencies("metric")
	public String getHeading() {
		return String.format("%s(%s, %d)",
				metric.getName(), metric.getCompany(), metric.getPeriod());
	}

	@Dependencies("metric")
	public String getValue() {
		return Util.formatNumber(metric.getValue());
	}

	@Dependencies("metric")
	public String getDescription() {
		return metric.getDescription();
	}
	
	@Dependencies("metric")
	public Color getColour() {
		return metric.getValue() < 0 ? Util.RED_COLOUR : Util.GREEN_COLOUR; 
	}
	
}
