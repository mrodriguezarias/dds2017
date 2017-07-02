package tp1.model.indicator;

import java.util.HashSet;
import java.util.Set;

import tp1.model.Company;
import tp1.model.Metric;

public class CalculableCuenta implements Calculable {

	String nombreCuenta;
	
	public CalculableCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	
	@Override
	public double calcular(Company Empresa, short periodo) {
		Metric cuenta;
		cuenta = Empresa.getMetric(nombreCuenta, periodo);
		return cuenta.getValue(Empresa, periodo);
	}

	@Override
	public Set<String> getCuentas() {
		Set<String> set = new HashSet<String>();
		set.add(nombreCuenta);
		return set;
	}
}
