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
	
	public String getValue() {
		return significantDigits(this.measure.getValue(company, period));
	}
	
	private String significantDigits(double figure) {
		String[] units = {"m", "M", "mM", "B", "mB", "T", "mT"};
		int index = -1;
		while(Math.abs(figure) > 1000) {
			index++;
			figure /= 1000;
		}
		String unit = index < 0 ? "" : " " + units[index]; 
		return formatNumber(figure) + unit;
	}
	
	private String formatNumber(double number) {
		String integer = String.format("%,d", (long) number).replaceAll(",", "\u2009");
		String decimal = String.format("%f", Math.abs(number)).replaceFirst("[0-9]+\\.", ",")
				.replaceFirst(",([0-9]*?)0+$", ",$1").replaceFirst(",0*$", "");
		return integer + decimal;
	}
	
}
