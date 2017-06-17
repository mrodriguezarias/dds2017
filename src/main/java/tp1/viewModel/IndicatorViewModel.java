package tp1.viewModel;

import java.util.List;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.model.Company;
import tp1.model.Indicator;

@Observable
public class IndicatorViewModel {

	private Company company;
	private short period;
	
	private List<Indicator> indicators;
	private Indicator indicator;

	public IndicatorViewModel(Company company, short period) {
		this.company = company;
		this.period = period;
		
//		indicators = new ArrayList<>(Database.getInstance().getIndicators(company, period));
//		indicators.add(0, Indicator.EMPTY);
		indicator = indicators.get(0);
	}
	
	public String title() {
		return String.format("Aplicar indicador a $%s en %s", company, period);
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}
	
	@Dependencies("indicator")
	public String getDescription() {
		return indicator.getDescription();
	}
	
	@Dependencies("indicator")
	public String getValue() {
		return "";
//		return indicator.getValueString();
	}
	
	@Dependencies("indicator")
	public String getFormula() {
		return indicator.getFormula();
	}

}
