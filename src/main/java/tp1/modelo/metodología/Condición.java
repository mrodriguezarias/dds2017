package tp1.modelo.metodología;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

@Observable
public abstract class Condición {
	
	protected String nombre;
	protected Indicador indicador;
	protected int númeroDePeríodos;
	protected Evaluación evaluación;
	protected Orden orden;
	
	protected Condición() {}
	
	protected Condición(String nombre, Indicador indicador, int númeroDePeríodos, Evaluación evaluación, Orden orden) {
		this.nombre = nombre;
		this.indicador = indicador;
		this.númeroDePeríodos = númeroDePeríodos;
		this.evaluación = evaluación;
		this.orden = orden;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Indicador obtenerIndicador() {
		return indicador;
	}
	
	public int obtenerNúmeroDePeríodos() {
		return númeroDePeríodos;
	}
	
	public Evaluación obtenerEvaluación() {
		return evaluación;
	}
	
	public Orden obtenerOrden() {
		return orden;
	}

	public abstract List<Empresa> aplicar(List<Empresa> empresas);
	
	public boolean esAplicable(List<Empresa> empresas) {
		return empresas.stream().allMatch(empresa -> períodosAEvaluar(empresa).stream()
				.allMatch(período -> indicador.esVálidoParaContexto(empresa, período)));
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
	
	public abstract String getTipo();
}