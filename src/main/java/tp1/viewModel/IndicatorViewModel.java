package tp1.viewModel;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.App;
import tp1.model.Company;
import tp1.model.indicator.Indicator;

@Observable
public class IndicatorViewModel {

	private String companyName;
	private short period;
	
	private List<String> indicatorNames;
	private String indicatorName;

	public IndicatorViewModel(String companyName, short period) {
		this.companyName = companyName;
		this.period = period;
		
		Company company = App.companyRepository.find(companyName);
		this.indicatorNames = App.indicatorRepository.all().stream()
				.filter(i -> i.isValidForContext(company, period))
				.map(i -> i.getName()).collect(Collectors.toList());
	}
	
	public String title() {
		return String.format("Aplicar indicador a $%s en %s", companyName, period);
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public List<String> getIndicatorNames() {
		return indicatorNames;
	}
	
	@Dependencies("indicatorName")
	public String getDescription() {
		Indicator indicator = App.indicatorRepository.find(indicatorName);
		return indicator.getDescription();
	}
	
	@Dependencies("indicatorName")
	public String getValue() {
		Indicator indicator = App.indicatorRepository.find(indicatorName);
		Company company = App.companyRepository.find(companyName);
		MeasureComponent component = new MeasureComponent(indicator, company, period);
		return component.getFullValue();
	}
	
	@Dependencies("indicatorName")
	public String getFormula() {
		Indicator indicator = App.indicatorRepository.find(indicatorName);
		return indicator.getFormula();
	}

}
