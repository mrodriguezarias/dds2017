package tp1.modelo.metodología;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public final class CondiciónTaxativa extends Condición {
	
	private Optional<Double> valorDeReferencia;

	CondiciónTaxativa(String nombre, Indicador indicador, int númeroDePeríodos,
			Evaluación evaluación, Orden orden, Optional<Double> valorDeReferencia) {
		super(nombre, indicador, númeroDePeríodos, evaluación, orden);
		this.valorDeReferencia = valorDeReferencia;
	}
	
	public double obtenerValorDeReferencia() {
		return valorDeReferencia.orElse(null);
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return empresas.stream().filter(this::esConveniente).collect(Collectors.toList());
	}
	
	private boolean esConveniente(Empresa empresa) {
		List<Double> valores = valoresAEvaluar(empresa);
		
		if(valorDeReferencia.isPresent()) {
			return orden.comparar(evaluación.evaluar(valores), valorDeReferencia.get());
		}
		
		// Si no hay valor de referencia, se evalúa la tendencia
		for(int i = 1, cantidad = valores.size(); i < cantidad; i++) {
			if(!orden.comparar(valores.get(i), valores.get(i-1))) {
				return false;
			}
		}
		return true;
	}
}