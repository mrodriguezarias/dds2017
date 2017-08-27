package tp1.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.uqbar.commons.model.annotations.Observable;

@Observable @Entity
public class Empresa {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	
	@Transient // TODO: persistir esto
	private List<Cuenta> cuentas;
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<>();
	}
	
	public Empresa(String nombre, List<Cuenta> cuentas) {
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	public boolean tieneCuenta(String nombre) {
		return cuentas.stream().anyMatch(x -> x.getName().equals(nombre));
	}

	public List<Cuenta> obtenerCuentas() {
		return cuentas;
	}
	
	public List<Cuenta> obtenerCuentas(short período) {
		return cuentas.stream().filter(m -> m.getPeriod() == período).collect(Collectors.toList());
	}

	public Cuenta obtenerCuenta(String nombre, short período) {
		return cuentas.stream()
		            .filter(x -> x.getName().equals(nombre) && x.getPeriod() == período)
		            .findFirst().orElse(null);
	}
	
	public List<Short> obtenerPeríodos() {
		return cuentas.stream().map(cuenta -> cuenta.getPeriod()).distinct().sorted().collect(Collectors.toList());
	}
}
