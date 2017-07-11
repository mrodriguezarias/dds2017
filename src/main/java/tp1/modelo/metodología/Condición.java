package tp1.modelo.metodología;

import java.util.List;
import java.util.stream.Collectors;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public abstract class Condición {
	
	protected Indicador indicador;
	protected int númeroDePeríodos;
	protected Evaluación evaluación;
	protected Orden orden;
	
	protected Condición() {}
	
	protected Condición(Indicador indicador, int númeroDePeríodos, Evaluación evaluación, Orden orden) {
		this.indicador = indicador;
		this.númeroDePeríodos = númeroDePeríodos;
		this.evaluación = evaluación;
		this.orden = orden;
	}
	
	public abstract List<Empresa> aplicar(List<Empresa> empresas);
	
	public boolean esAplicable(List<Empresa> empresas) {
		return empresas.stream().allMatch(empresa -> períodosAEvaluar(empresa).stream()
				.allMatch(período -> indicador.obtenerFórmula().esVálidaParaContexto(empresa, período)));
	}
	
	protected List<Short> períodosAEvaluar(Empresa empresa) {
		List<Short> períodos = empresa.obtenerPeríodos();
		if(períodos.size() == 0) return períodos;
		short períodoFinal = períodos.get(períodos.size()-1);
		short períodoInicial = (short)(períodoFinal - númeroDePeríodos + 1);
		int índiceInicial = períodos.indexOf(períodoInicial);
		if(índiceInicial == -1) índiceInicial++;
		return períodos.subList(índiceInicial, períodos.size());
	}
	
	protected List<Double> valoresAEvaluar(Empresa empresa) {
		return períodosAEvaluar(empresa).stream().map(período -> indicador.obtenerValor(empresa, período))
				.collect(Collectors.toList());
	}
}
