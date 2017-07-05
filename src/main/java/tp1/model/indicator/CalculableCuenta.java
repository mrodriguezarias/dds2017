package tp1.model.indicator;

import java.util.HashSet;
import java.util.Set;

import tp1.model.Empresa;
import tp1.model.Cuenta;

public class CalculableCuenta implements Calculable {

	String nombreCuenta;
	
	public CalculableCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	
	@Override
	public double calcular(Empresa Empresa, short periodo) {
		Cuenta cuenta;
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
