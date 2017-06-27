package tp1.modelo;

import org.uqbar.commons.utils.Observable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


@Observable
public class Cuenta implements Medida {
	
	@JsonProperty
	protected String nombre;
	
	@JsonProperty
	protected String descripción;
	
	@JsonProperty
	protected String nombreDeLaEmpresa;
	@JsonProperty
	protected short período;
	@JsonProperty
	private double valor;
	
	@JsonCreator
	public Cuenta(
			@JsonProperty("nombre") String nombre,
			@JsonProperty("descripción") String descripción,
			@JsonProperty("nombreDeLaEmpresa") String nombreDeLaEmpresa,
			@JsonProperty("período") short período,
			@JsonProperty("valor") double valor
			) {
		this.nombre = nombre;
		this.descripción = descripción;
		this.nombreDeLaEmpresa = nombreDeLaEmpresa;
		this.período = período;
		this.valor = valor;
	}
	
	public String obtenerNombre() {
		return nombre;
	}
	
	public String obtenerNombreDeLaEmpresa() {
		return nombreDeLaEmpresa;
	}
	
	public double obtenerValor(Empresa empresa, short período) {
		return valor;
	}
	
	public double obtenerValor() {
		return valor;
	}

	public short obtenerPeríodo() {
		return período;
	}
	
	public String obtenerDescripción() {
		return descripción;
	}
}
