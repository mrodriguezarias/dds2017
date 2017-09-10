package tp1.modeloDeVista;

import org.uqbar.commons.model.annotations.Observable;

import tp1.modelo.CuentaDeEmpresa;
import tp1.modelo.Empresa;
import tp1.modelo.Medida;
import tp1.modelo.indicador.Indicador;

@Observable
public class MeasureComponent {

	private Medida measure;
	private Empresa company;
	private short period;
	private String type;
	
	private MeasureComponent(Medida measure, Empresa company, short period) {
		this.measure = measure;
		this.company = company;
		this.period = period;
	}
	
	public MeasureComponent(CuentaDeEmpresa metric, Empresa company, short period) {
		this((Medida) metric, company, period);
		this.type = "Cuenta";
	}

	public MeasureComponent(Indicador indicator, Empresa company, short period) {
		this((Medida) indicator, company, period);
		this.type = "Indicador";
	}
	
	public Medida getMeasure() {
		return measure;
	}

	public short getPeriod() {
		return period;
	}

	public String getType() {
		return type;
	}
	
	public String getCompanyName() {
		return this.company.getNombre();
	}
	
	public String getMeasureName(){
		return this.measure.getName();
	}
	
	public String getName() {
		return this.measure.getName();
	}
	
	public String getDescription() {
		return this.measure.obtenerDescripción();
	}
	
	public String getValue() {
		return significantDigits(this.measure.obtenerValor(company, period));
	}
	
	public String getFullValue() {
		return formatNumber(this.measure.obtenerValor(company, period));
	}
	
	public double getDoubleValue() {
		return this.measure.obtenerValor(company, period);
	}
	
	private String significantDigits(double figure) {
		if(figure < 10000) return formatNumber(figure);
		String[] units = {"mil", "millones", "mil millones", "billones", "mil billones", "trillones", "mil trillones"};
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
		String intformat = Math.abs(intval) / 1000 < 10 ? "%d" : "%,d";
		String integer = String.format(intformat, intval).replaceAll(",", "\u2009");
		
		String decimal = String.format("%f", Math.abs(number)).replaceFirst("[0-9]+\\.", ",")
				.replaceFirst(",([0-9]*?)0+$", ",$1").replaceFirst(",0*$", "");
		return integer + decimal;
	}
	
}
