package tp1.modelo.metodología;

import java.util.List;
import java.util.stream.Collectors;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public final class CondiciónComparativa extends Condición {

	public CondiciónComparativa(Indicador indicador, int númeroDePeríodos, Evaluación evaluación, Orden orden) {
		super(indicador, númeroDePeríodos, evaluación, orden);
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return empresas.stream().sorted(this::compararEmpresas).collect(Collectors.toList());
	}
	
	private int compararEmpresas(Empresa empresa1, Empresa empresa2) {
		double valor1 = evaluación.evaluar(valoresAEvaluar(empresa1));
		double valor2 = evaluación.evaluar(valoresAEvaluar(empresa2));
		return orden.comparar(valor1, valor2) ? 1 : -1;
	}

}