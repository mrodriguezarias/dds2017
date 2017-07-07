package tp1.modelo.metodología;

import java.util.Optional;

public final class ConstructorDeCondiciónTaxocomparativa extends ConstructorDeCondición {

	public ConstructorDeCondiciónTaxocomparativa(String nombreDelIndicador) {
		super(nombreDelIndicador);
	}
	
	public void establecerValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = Optional.of(valorDeReferencia);
	}

	@Override
	public Condición construir() {
		return new CondiciónTaxocomparativa(indicador, númeroDePeríodos, evaluación, orden, valorDeReferencia);
	}

}
