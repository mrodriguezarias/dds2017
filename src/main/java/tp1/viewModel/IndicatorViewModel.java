package tp1.viewModel;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.Util;
import tp1.model.Company;
import tp1.model.Database;
import tp1.model.Indicator;
import tp1.model.Parser;
import tp1.model.Period;

@Observable
public class IndicatorViewModel {

	private Company company;
	private Period period;
	
	private List<Indicator> indicators;
	private Indicator indicator;
	
	private String error;

	public IndicatorViewModel(Company company, Period period) {
		this.company = company;
		this.period = period;
		this.error = "";
		
		indicators = new ArrayList<>(Database.getInstance().getIndicators(company, period));
		indicators.add(0, Indicator.EMPTY);
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
		String result = "";
		error = "";
		if(indicator != Indicator.EMPTY) {
			try {
				double value = indicator.tryGetValue();
				result = Util.formatNumber(value);
			} catch (Parser.ParseFailedException e) {
				error = e.getMessage();
			}
		}
		return result;
	}
	
	public String getError() {
		return error;
	}
	
	@Dependencies("error")
	public boolean getIsError() {
		return !error.isEmpty();
	}
	
	@Dependencies("indicator")
	public String getFormula() {
		return indicator.getFormula();
	}

}
