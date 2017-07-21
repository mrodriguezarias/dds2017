package tp1.modelo.metodología;

import java.util.List;
import java.util.Optional;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public final class CondiciónTaxocomparativa extends Condición {
	
	private CondiciónTaxativa condiciónTaxativa;
	private CondiciónComparativa condiciónComparativa;

	CondiciónTaxocomparativa(String nombre, Indicador indicador, int númeroDePeríodos,
			Evaluación evaluación, Orden orden, Optional<Double> valor, Prioridad prioridad) {
		super(nombre, indicador, númeroDePeríodos, evaluación, orden);
		condiciónTaxativa = new CondiciónTaxativa(nombre, indicador, númeroDePeríodos, evaluación, orden, valor);
		condiciónComparativa = new CondiciónComparativa(nombre, indicador, númeroDePeríodos, evaluación, orden, prioridad);
	}
	
	public double obtenerValorDeReferencia() {
		return condiciónTaxativa.obtenerValorDeReferencia();
	}
	
	public Prioridad obtenerPrioridad() {
		return condiciónComparativa.obtenerPrioridad();
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return condiciónComparativa.aplicar(condiciónTaxativa.aplicar(empresas));
	}
	
	CondiciónTaxativa obtenerCondiciónTaxativa() {
		return condiciónTaxativa;
	}
	
	CondiciónComparativa obtenerCondiciónComparativa() {
		return condiciónComparativa;
	}
}