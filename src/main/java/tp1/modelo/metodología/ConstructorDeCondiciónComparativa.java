package tp1.modelo.metodología;

public class ConstructorDeCondiciónComparativa extends ConstructorDeCondición {

	public ConstructorDeCondiciónComparativa(String nombreDelIndicador) {
		super(nombreDelIndicador);
	}

	@Override
	public CondiciónComparativa construir() {
		return new CondiciónComparativa(indicador, númeroDePeríodos, evaluación, orden);
	}

}
