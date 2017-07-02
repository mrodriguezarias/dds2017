package tp1.model.indicator;

import java.util.Collections;
import java.util.Set;

import tp1.model.Company;

public class CalculableNumerico implements Calculable{
	
	double numero;
	
	public CalculableNumerico(double numero) {
		this.numero = numero;
	}

	@Override
	public double calcular(Company Empresa, short periodo) {
		return numero;
	}


	@Override
	public Set<String> getCuentas() {
		return Collections.emptySet();
	}

}
