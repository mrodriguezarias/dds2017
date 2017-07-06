package tp1.modelo.metodología;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.Repositorios;

public class CondiciónTaxativa implements Condición {
	
	private Indicador indicador;
	private Optional<Double> valorDeReferencia;
	private int númeroDePeríodos;
	private Evaluación evaluación;
	private Orden orden;

	public CondiciónTaxativa(String nombreDelIndicador, Optional<Double> valorDeReferencia, int númeroDePeríodos,
			Evaluación evaluación, Orden orden) {
		// Precondición: que el indicador exista
		this.indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreDelIndicador);
		this.valorDeReferencia = valorDeReferencia;
		this.númeroDePeríodos = númeroDePeríodos;
		this.evaluación = evaluación;
		this.orden = orden;
	}
	
	@Override
	public boolean esAplicable(List<Empresa> empresas) {
		return empresas.stream().allMatch(empresa -> períodosAEvaluar(empresa).stream()
				.allMatch(período -> indicador.obtenerFórmula().esVálidaParaContexto(empresa, período)));
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return empresas.stream().filter(this::esConveniente).collect(Collectors.toList());
	}
	
	private List<Short> períodosAEvaluar(Empresa empresa) {
		List<Short> períodos = empresa.obtenerPeríodos();
		if(períodos.size() == 0) return períodos;
		short períodoFinal = períodos.get(períodos.size()-1);
		short períodoInicial = (short)(períodoFinal - númeroDePeríodos + 1);
		int índiceInicial = períodos.indexOf(períodoInicial);
		if(índiceInicial == -1) índiceInicial++;
		return períodos.subList(índiceInicial, períodos.size());
	}
	
	private boolean esConveniente(Empresa empresa) {
		List<Double> valores = períodosAEvaluar(empresa).stream().map(período -> indicador.obtenerValor(empresa, período))
				.collect(Collectors.toList());
		
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
