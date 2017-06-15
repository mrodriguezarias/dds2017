package tp1.viewModel;

import tp1.model.Company;
import tp1.model.Indicator;
import tp1.model.Measure;
import tp1.model.Metric;

public class MeasureComponent {

	private Measure measure;
	private Company company;
	private short period;
	private String type;
	
	public void init(Metric metric, Company company, short period){
		this.measure = metric;
		this.company = company;
		this.period = period;
		this.type = "CUENTA";
		
	}
	
	public void init(Indicator indicator, Company company, short period){
		this.measure = indicator;
		this.company = company;
		this.period = period;
		this.type = "INDICADOR";
		
	}
	
	public Measure getMeasure() {
		return measure;
	}

	public Company getCompany() {
		return company;
	}

	public short getPeriod() {
		return period;
	}

	public String getType() {
		return type;
	}
	
	public String getCompanyName() {
		return this.company.getName();
	}
	
	public String getMeasureName(){
		return this.measure.getName();
	}
	
	public String getDescription() {
		return this.measure.getDescription();
	}
	
	
}
