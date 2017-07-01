package tp1.modelo.metodologia.comparadores;

public class Mayor implements Comparador {
	
	private static Mayor comparador = new Mayor();
	
	public static Mayor getInstance()	{
		return comparador;
	}
	
	private Mayor()	{
		
	}

	@Override
	public boolean comparar(double indicadorMejorEmpresa, double indicadorPeorEmpresa) {
		return indicadorMejorEmpresa >= indicadorPeorEmpresa;
	}

}
