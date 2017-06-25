package tp1.viewModel;

import org.uqbar.commons.utils.Observable;

import tp1.model.Company;
import tp1.model.Measure;
import tp1.model.Metric;
import tp1.model.indicator.Indicator;

@Observable
public class MeasureComponent {

	private Measure measure;
	private Company company;
	private short period;
	private String type;
	
	private MeasureComponent(Measure measure, Company company, short period) {
		this.measure = measure;
		this.company = company;
		this.period = period;
	}
	
	public MeasureComponent(Metric metric, Company company, short period) {
		this((Measure) metric, company, period);
		this.type = "Cuenta";
	}

	public MeasureComponent(Indicator indicator, Company company, short period) {
		this((Measure) indicator, company, period);
		this.type = "Indicador";
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
	
	public String getName() {
		return this.measure.getName();
	}
	
	public String getDescription() {
		return this.measure.getDescription();
	}
	
	public String getValue() {
		return significantDigits(this.measure.getValue(company, period));
	}
	
	public String getFullValue() {
		return formatNumber(this.measure.getValue(company, period));
	}
	
	public double getDoubleValue() {
		return this.measure.getValue(company, period);
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
		long intval = (long) number;
		String intformat = intval / 1000 < 10 ? "%d" : "%,d";
		String integer = String.format(intformat, intval).replaceAll(",", "\u2009");
		
		String decimal = String.format("%f", Math.abs(number)).replaceFirst("[0-9]+\\.", ",")
				.replaceFirst(",([0-9]*?)0+$", ",$1").replaceFirst(",0*$", "");
		return integer + decimal;
	}
	
}
