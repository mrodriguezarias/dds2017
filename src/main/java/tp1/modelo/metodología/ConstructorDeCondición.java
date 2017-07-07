package tp1.modelo.metodología;

import java.util.Optional;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.Repositorios;

public abstract class ConstructorDeCondición {

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
	
	public abstract Condición construir();
}
