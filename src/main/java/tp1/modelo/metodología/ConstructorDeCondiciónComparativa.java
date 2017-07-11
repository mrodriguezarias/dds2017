package tp1.modelo.metodología;

public class ConstructorDeCondiciónComparativa extends ConstructorDeCondición<ConstructorDeCondiciónComparativa> {

	public ConstructorDeCondiciónComparativa(String nombreDelIndicador) {
		super(nombreDelIndicador);
	}

	@Override
	public CondiciónComparativa construir() {
		return new CondiciónComparativa(indicador, númeroDePeríodos, evaluación, orden);
	}

	@Override
	protected ConstructorDeCondiciónComparativa obtenerEsto() {
		return this;
	}

}
