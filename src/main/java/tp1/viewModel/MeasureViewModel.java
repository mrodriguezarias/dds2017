package tp1.viewModel;

import java.awt.Color;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.model.Indicator;

@Observable
public class MeasureViewModel {
	
	private MeasureComponent measure;

	public MeasureViewModel(MeasureComponent measure) {
		this.measure = measure;
	}
	
	public String title() {
		return String.format("%s %s de $%s en %s", measure.getType(),
				measure.getName(), measure.getCompanyName(), measure.getPeriod()); 
	}
	
	private boolean isIndicator() {
		return measure.getType().equals("Indicator");
	}
	
	@Dependencies("measure")
	public String getHeading() {
		if(isIndicator()) {
			Indicator indicator = ((Indicator) measure.getMeasure());
			return indicator.getFormula();
		}
		return String.format("%s(%s, %s)",
				measure.getName(), measure.getCompanyName(), measure.getPeriod());
	}

	@Dependencies("measure")
	public String getValue() {
		return measure.getFullValue();
	}

	@Dependencies("measure")
	public String getDescription() {
		return measure.getDescription();
	}
	
	@Dependencies("measure")
	public Color getColour() {
		if(isIndicator()) return Colour.BLUE.getValue();
		return measure.getDoubleValue() < 0 ? Colour.RED.getValue() : Colour.GREEN.getValue(); 
	}
	
}
