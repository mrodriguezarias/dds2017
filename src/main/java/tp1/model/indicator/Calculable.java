package tp1.model.indicator;

import java.util.Set;

import tp1.model.Empresa;


public interface Calculable {
	double calcular(Empresa company, short period);
	Set<String> getCuentas();
}
