package tp1.modelo.metodología;

import java.util.Optional;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.Repositorios;

public abstract class ConstructorDeCondición<T extends ConstructorDeCondición<T>> {

	protected Indicador indicador;
	protected int númeroDePeríodos;
	protected Evaluación evaluación;
	protected Orden orden;
	protected Optional<Double> valorDeReferencia;
	
	public ConstructorDeCondición(String nombreDelIndicador) {
		// Valores obligatorios
		// Precondición: que el indicador exista
		this.indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreDelIndicador);
		
		// Valores por defecto de los opcionales
		this.valorDeReferencia = Optional.empty();
		this.númeroDePeríodos = 1;
		this.evaluación = Evaluación.PROMEDIO;
		this.orden = Orden.MAYOR;
	}
	
	public void establecerNúmeroDePeríodos(int númeroDePeríodos) {
		this.númeroDePeríodos = númeroDePeríodos;
	}
	
	public void establecerEvaluación(Evaluación evaluación) {
		this.evaluación = evaluación;
	}
	
	public void establecerOrden(Orden orden) {
		this.orden = orden;
	}
	
	public T conNúmeroDePeríodos(int númeroDePeríodos) {
		establecerNúmeroDePeríodos(númeroDePeríodos);
		return obtenerEsto();
	}
	
	public T conEvaluación(Evaluación evaluación) {
		establecerEvaluación(evaluación);
		return obtenerEsto();
	}
	
	public T conOrden(Orden orden) {
		establecerOrden(orden);
		return obtenerEsto();
	}
	
	public abstract Condición construir();
	
	protected abstract T obtenerEsto();
}
