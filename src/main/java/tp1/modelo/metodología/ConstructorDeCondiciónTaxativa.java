package tp1.modelo.metodología;

import java.util.Optional;

public class ConstructorDeCondiciónTaxativa {
	
	private String nombreDelIndicador;
	private Optional<Double> valorDeReferencia;
	private int númeroDePeríodos;
	private Evaluación evaluación;
	private Orden orden;
	
	public ConstructorDeCondiciónTaxativa(String nombreDelIndicador) {
		// Valores obligatorios
		this.nombreDelIndicador = nombreDelIndicador;
		
		// Valores por defecto de los opcionales
		this.valorDeReferencia = Optional.empty();
		this.númeroDePeríodos = 1;
		this.evaluación = Evaluación.PROMEDIO;
		this.orden = Orden.MAYOR;
	}
	
	public void establecerNombreDelIndicador(String nombreDelIndicador) {
		this.nombreDelIndicador = nombreDelIndicador;
	}
	
	public void establecerValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = Optional.of(valorDeReferencia);
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
	
	public CondiciónTaxativa construir() {
		return new CondiciónTaxativa(nombreDelIndicador, valorDeReferencia, númeroDePeríodos, evaluación, orden);
	}
}
