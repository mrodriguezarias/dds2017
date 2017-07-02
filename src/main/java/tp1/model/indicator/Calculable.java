package tp1.model.indicator;

import java.util.Set;

import tp1.model.Company;


public interface Calculable {
	double calcular(Company company, short period);
	Set<String> getCuentas();
}
