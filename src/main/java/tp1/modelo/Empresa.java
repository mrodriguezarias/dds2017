package tp1.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Empresa {
	
	private String nombre;
	private List<Cuenta> cuentas;
	private Metodologia metodologia; // Strategy, va cambiando según la metodología que querramos aplicar
	private int anioFundacion;
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		this.cuentas = new ArrayList<>();
	}
	
	public Empresa(String nombre, List<Cuenta> cuentas) {
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public String obtenerNombre() {
		return nombre;
	}
	
	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	public boolean tieneCuenta(String nombre) {
		return cuentas.stream().anyMatch(x -> x.obtenerNombre().equals(nombre));
	}

	public List<Cuenta> obtenerCuentas() {
		return cuentas;
	}
	
	public List<Cuenta> obtenerCuentas(short período) {
		return cuentas.stream().filter(m -> m.obtenerPeríodo() == período).collect(Collectors.toList());
	}

	public Cuenta obtenerCuenta(String nombre, short período) {
		Cuenta cuenta = cuentas.stream()
		            .filter(x -> x.obtenerNombre().equals(nombre) && x.obtenerPeríodo() == período)
		            .findFirst().orElse(null);
		return cuenta;
	}
	
	public boolean sosMejorQue(Empresa otraEmpresa)	{
		return this.metodologia.esMejor(this, otraEmpresa);
	}
	
	public boolean convieneInvertir()	{
		return this.metodologia.convieneInvertir(this);
	}
	
	public void setMetodologia(Metodologia metodologia)	{
		this.metodologia = metodologia;
	}
	
	public int getLongevidad()	{
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		return anioActual - this.anioFundacion;
	}
}
