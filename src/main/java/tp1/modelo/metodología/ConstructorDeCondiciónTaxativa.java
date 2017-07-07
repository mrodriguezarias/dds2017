package tp1.modelo.metodología;

import java.util.Optional;

public final class ConstructorDeCondiciónTaxativa extends ConstructorDeCondición {
	
	public ConstructorDeCondiciónTaxativa(String nombreDelIndicador) {
		super(nombreDelIndicador);
	}

	public void establecerValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = Optional.of(valorDeReferencia);
	}
	
	@Override
	public CondiciónTaxativa construir() {
		return new CondiciónTaxativa(indicador, númeroDePeríodos, evaluación, orden, valorDeReferencia);
	}
}
