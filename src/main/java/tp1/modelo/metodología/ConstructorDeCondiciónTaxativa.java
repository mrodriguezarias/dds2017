package tp1.modelo.metodología;

import java.util.Optional;

public final class ConstructorDeCondiciónTaxativa extends ConstructorDeCondición<ConstructorDeCondiciónTaxativa> {
	
	public ConstructorDeCondiciónTaxativa(String nombreDelIndicador) {
		super(nombreDelIndicador);
	}

	public void establecerValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = Optional.of(valorDeReferencia);
	}
	
	public ConstructorDeCondiciónTaxativa conValorDeReferencia(double valorDeReferencia) {
		establecerValorDeReferencia(valorDeReferencia);
		return this;
	}
	
	@Override
	public CondiciónTaxativa construir() {
		return new CondiciónTaxativa(indicador, númeroDePeríodos, evaluación, orden, valorDeReferencia);
	}

	@Override
	protected ConstructorDeCondiciónTaxativa obtenerEsto() {
		return this;
	}
}