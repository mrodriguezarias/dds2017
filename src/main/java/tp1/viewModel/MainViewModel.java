package tp1.viewModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.App;
import tp1.model.Company;

@Observable
public class MainViewModel {
	
	private List<MeasureComponent> measures;
	private MeasureComponent selectedMeasure;
	
	private List<String> companyNames;
	private String selectedCompanyName;
	
	private List<Short> periods;
	private short selectedPeriod;
	
	public MainViewModel() {
		List<Company> companies = App.companyRepository.all(); 
		
		companyNames = companies.stream().map(c -> c.getName())
				.sorted().collect(Collectors.toList());
		selectedCompanyName = companyNames.get(0);
		
		periods = companies.stream().map(c -> c.getMetrics())
			.flatMap(l -> l.stream()).map(m -> m.getPeriod()).distinct()
			.sorted(Collections.reverseOrder()).collect(Collectors.toList());
		selectedPeriod = periods.get(0);
		
		updateMeasures();
	}
	
	public List<MeasureComponent> getMeasures() {
		return measures;
	}
	
	public MeasureComponent getSelectedMeasure() {
		return selectedMeasure;
	}
	
	public void setSelectedMeasure(MeasureComponent selectedMeasure) {
		this.selectedMeasure = selectedMeasure;
	}
	
	public List<String> getCompanyNames() {
		return companyNames;
	}
	
	public String getSelectedCompanyName() {
		return selectedCompanyName;
	}
	
	public void setSelectedCompanyName(String selectedCompanyName) {
		this.selectedCompanyName = selectedCompanyName;
		updateMeasures();
	}
	
	public List<Short> getPeriods() {
		return periods;
	}
	
	public short getSelectedPeriod() {
		return selectedPeriod;
	}
	
	public void setSelectedPeriod(short selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
		updateMeasures();
	}
	
	public void updateMeasures() {
		this.measures = null;
		Company selectedCompany = App.companyRepository.find(selectedCompanyName);
		
		List<MeasureComponent> measures = selectedCompany.getMetrics().stream()
				.filter(metric -> metric.getPeriod() == selectedPeriod)
				.map(metric -> new MeasureComponent(metric, selectedCompany, selectedPeriod))
				.collect(Collectors.toList());
		
		List<MeasureComponent> indicators = App.indicatorRepository.all().stream()
				.filter(indicator -> indicator.getFormula().isValidForContext(selectedCompany, selectedPeriod))
				.map(indicator -> new MeasureComponent(indicator, selectedCompany, selectedPeriod))
				.collect(Collectors.toList());
		measures.addAll(indicators);
		
		this.measures = measures;
	}
	
	@Dependencies("selectedMeasure")
	public boolean getViewMetricEnabled() {
		return selectedMeasure != null;
	}
}
