package tp1.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.uqbar.commons.model.annotations.Observable;

@Observable @Entity(name="Cuentas")
public class Cuenta {

	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	
	private String descripción;
	
	@SuppressWarnings("unused")
	private Cuenta() {}
	
	public Cuenta(String nombre, String descripción) {
		this.nombre = nombre;
		this.descripción = descripción;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}
}
