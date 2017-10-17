package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.uqbar.commons.model.annotations.Observable;

@Observable @Entity(name="Empresas")
public class Empresa extends Entidad {
	
	private String nombre;
	
	@OneToMany @JoinColumn(name="empresa_id")
	private List<CuentaDeEmpresa> cuentas;
	
	@SuppressWarnings("unused")
	private Empresa() {}
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<>();
	}
	
	public Empresa(String nombre, List<CuentaDeEmpresa> cuentas) {
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void agregarCuenta(CuentaDeEmpresa cuenta) {
		cuentas.add(cuenta);
	}
	
	public boolean tieneCuenta(String nombre) {
		return cuentas.stream().anyMatch(x -> x.getName().equals(nombre));
	}

	public List<CuentaDeEmpresa> getCuentas() {
		return cuentas.stream().distinct().collect(Collectors.toList());
	}
	
	public List<CuentaDeEmpresa> obtenerCuentas(short período) {
		return cuentas.stream().filter(m -> m.getPeriod() == período).collect(Collectors.toList());
	}

	public CuentaDeEmpresa obtenerCuenta(String nombre, short período) {
		return cuentas.stream()
		            .filter(x -> x.getName().equals(nombre) && x.getPeriod() == período)
		            .findFirst().orElse(null);
	}
	
	public List<Short> obtenerPeríodos() {
		return cuentas.stream().map(cuenta -> cuenta.getPeriod()).distinct().sorted().collect(Collectors.toList());
	}
}
