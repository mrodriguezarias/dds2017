package tp1.modelo;

public interface Medida {
	
	public String getName();
	
	public String obtenerDescripción();
	
	public double obtenerValor(Empresa company, short period);

}
