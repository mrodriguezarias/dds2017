package tp1.modelo.metodología;

import java.util.Optional;

public final class ConstructorDeCondiciónTaxocomparativa extends ConstructorDeCondición<ConstructorDeCondiciónTaxocomparativa> {

	public ConstructorDeCondiciónTaxocomparativa(String nombreDelIndicador) {
		super(nombreDelIndicador);
	}
	
	public void establecerValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = Optional.of(valorDeReferencia);
	}
	
	public ConstructorDeCondiciónTaxocomparativa conValorDeReferencia(double valorDeReferencia) {
		establecerValorDeReferencia(valorDeReferencia);
		return this;
	}

	@Override
	public CondiciónTaxocomparativa construir() {
		return new CondiciónTaxocomparativa(indicador, númeroDePeríodos, evaluación, orden, valorDeReferencia);
	}

	@Override
	protected ConstructorDeCondiciónTaxocomparativa obtenerEsto() {
		return this;
	}

}
