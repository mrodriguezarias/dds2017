package tp1.modelo.indicador;

import java.util.Set;

import tp1.modelo.Empresa;
import tp1.modelo.repositorios.Repositorios;

public class CalculableIndicador implements Calculable {

	String nombreIndicador;
	
	public CalculableIndicador(String nombre) {
		this.nombreIndicador = nombre;
	}
	
	@Override
	public double calcular(Empresa company, short period) {
		Indicador indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreIndicador);
		return indicador.calcular(company, period);
	}

	@Override
	public Set<String> getCuentas() {
		Indicador indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreIndicador);
		return indicador.getCuentas();
	}

}
