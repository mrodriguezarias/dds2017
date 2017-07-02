package tp1.model.indicator;

import java.util.Set;

import tp1.App;
import tp1.model.Company;

public class CalcuableIndicador implements Calculable {

	String nombreIndicador;
	
	public CalcuableIndicador(String nombre) {
		this.nombreIndicador = nombre;
	}
	
	@Override
	public double calcular(Company company, short period) {
		Indicator indicador = App.indicatorRepository.find(nombreIndicador);
		return indicador.calcular(company, period);
	}

	@Override
	public Set<String> getCuentas() {
		Indicator indicador = App.indicatorRepository.find(nombreIndicador);
		return indicador.getCuentas();
	}

}
