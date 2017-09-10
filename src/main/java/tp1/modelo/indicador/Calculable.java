package tp1.modelo.indicador;

import java.util.Set;

import tp1.modelo.Empresa;

public interface Calculable {
	double calcular(Empresa company, short period);
	Set<String> getCuentas();
}
