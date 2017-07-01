package tp1.modelo.metodologia.comparadores;

public class Menor implements Comparador {
	
	private static Menor comparador = new Menor();
	
	private Menor()	{
		
	}
	
	public static Menor getInstance()	{
		return comparador;
	}

	@Override
	public boolean comparar(double indicadorMejorEmpresa, double indicadorPeorEmpresa) {
		return indicadorMejorEmpresa <= indicadorPeorEmpresa;
	}
}
