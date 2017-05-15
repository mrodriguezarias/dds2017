package tp1.viewModel;

import java.util.List;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.model.Company;
import tp1.model.Period;
import tp1.model.Metric;
import tp1.model.Database;

@Observable
public class MainViewModel {
	
	private List<Metric> metrics;
	private Metric selectedMetric;
	
	private List<Company> companies;
	private Company selectedCompany;
	
	private List<Period> periods;
	private Period selectedPeriod;
	
	public MainViewModel() {
		metrics = Database.getInstance().getMetrics();
		
		companies = Database.getInstance().getCompanies();
		selectedCompany = companies.get(0);
		
		periods = Database.getInstance().getPeriods();
		selectedPeriod = periods.get(0);
	}
	
	public List<Metric> getMetrics() {
		return metrics;
	}
	
	public Metric getSelectedMetric() {
		return selectedMetric;
	}
	
	public void setSelectedMetric(Metric selectedMetric) {
		this.selectedMetric = selectedMetric;
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
		metrics = Database.getInstance().getMetrics(selectedCompany, selectedPeriod);
	}
	
	public void viewMetric() {
		System.out.println(selectedMetric);
	}
	
	@Dependencies("selectedMetric")
	public boolean getViewMetricEnabled() {
		return selectedMetric != null;
	}
	
	@Dependencies({"selectedCompany", "selectedPeriod"})
	public boolean getApplyIndicatorEnabled() {
		return selectedCompany != Company.EMPTY && selectedPeriod != Period.EMPTY;
	}

}
