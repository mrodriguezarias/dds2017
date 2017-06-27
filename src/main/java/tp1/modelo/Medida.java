package tp1.modelo;

public interface Medida {
	
	public String obtenerNombre();
	
	public String obtenerDescripción();
	
	public double obtenerValor(Empresa empresa, short período);

}
