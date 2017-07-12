package tp1.modelo.metodología;

import java.util.List;
import java.util.Optional;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public final class CondiciónTaxocomparativa extends Condición {
	
	private Condición condiciónTaxativa;
	private Condición condiciónComparativa;

	public CondiciónTaxocomparativa(Indicador indicador, int númeroDePeríodos,
			Evaluación evaluación, Orden orden, Optional<Double> valorDeReferencia) {
		this.condiciónTaxativa = new CondiciónTaxativa(indicador, númeroDePeríodos, evaluación, orden, valorDeReferencia);
		this.condiciónComparativa = new CondiciónComparativa(indicador, númeroDePeríodos, evaluación, orden);
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return condiciónComparativa.aplicar(condiciónTaxativa.aplicar(empresas));
	}
	
	Condición obtenerCondiciónTaxativa() {
		return condiciónTaxativa;
	}
	
	Condición obtenerCondiciónComparativa() {
		return condiciónComparativa;
	}
}