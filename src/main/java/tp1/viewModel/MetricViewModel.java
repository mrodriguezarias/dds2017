package tp1.viewModel;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import tp1.Util;
import tp1.model.Company;
import tp1.model.Period;
import tp1.model.Metric;
import tp1.model.Database;

@Observable
public class MetricViewModel {
	
	private List<Metric> metrics;
	
	private List<Company> companies;
	private Company selectedCompany;
	
	private List<Period> periods;
	private Period selectedPeriod;
	
	public MetricViewModel() {
		metrics = Database.getInstance().getMetrics();
		
		companies = Database.getInstance().getCompanies();
		selectedCompany = companies.get(0);
		
		periods = Database.getInstance().getPeriods();
		selectedPeriod = periods.get(0);
	}
	
	public List<Metric> getMetrics() {
		return metrics;
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
	
	public Company getSelectedCompany() {
		return selectedCompany;
	}
	
	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
		updateMetrics();
	}
	
	public List<Period> getPeriods() {
		return periods;
	}
	
	public Period getSelectedPeriod() {
		return selectedPeriod;
	}
	
	public void setSelectedPeriod(Period selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
		updateMetrics();
	}
	
	public void updateMetrics() {
		metrics = Database.getInstance().getMetrics();
		
		if(!selectedCompany.getSymbol().isEmpty()) {
			metrics = Util.filterList(metrics, metric -> metric.getCompany().equals(selectedCompany.getSymbol()));
		}
		
		if(selectedPeriod.getYear() > 0) {
			metrics = Util.filterList(metrics, metric -> metric.getPeriod() == selectedPeriod.getYear());
		}
	}

}
