package tp1.modelo.metodología;

import java.util.Optional;

public final class ConstructorDeCondiciónTaxocomparativa extends ConstructorDeCondición<ConstructorDeCondiciónTaxocomparativa> {

	public ConstructorDeCondiciónTaxocomparativa(String nombre) {
		super(nombre);
	}
	
	public void establecerValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = valorDeReferencia;
	}
	
	public ConstructorDeCondiciónTaxocomparativa conValorDeReferencia(double valorDeReferencia) {
		establecerValorDeReferencia(valorDeReferencia);
		return this;
	}
	
	public void establecerPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
	}
	
	public ConstructorDeCondiciónTaxocomparativa conPrioridad(Prioridad prioridad) {
		establecerPrioridad(prioridad);
		return this;
	}

	@Override
	public CondiciónTaxocomparativa construir() {
		return new CondiciónTaxocomparativa(nombre, obtenerIndicador(), númeroDePeríodos,
				evaluación, orden, valorDeReferencia, prioridad);
	}

	@Override
	protected ConstructorDeCondiciónTaxocomparativa obtenerEsto() {
		return this;
	}

}