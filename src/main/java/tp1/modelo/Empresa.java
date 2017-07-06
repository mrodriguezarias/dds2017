package tp1.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Empresa {
	
	private String nombre;
	private List<Cuenta> cuentas;
	
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
	
	public List<Short> obtenerPeríodos() {
		return cuentas.stream().map(cuenta -> cuenta.período).distinct().sorted().collect(Collectors.toList());
	}
	
//	public boolean esMejorQue(Empresa otraEmpresa)	{
//		return this.metodología.esMejor(this, otraEmpresa);
//	}
//	
//	public boolean convieneInvertir()	{
//		return this.metodología.convieneInvertir(this);
//	}
	
	/*
	 * Sigue sin cerrarme la longevidad como una propiedad de la empresa.
	 * Para poder usarla como condición de la metodología de Buffet tiene que ser un indicador.
	 * Y el año de fundación como atributo tampoco me parece, porque eso significaría que cada vez que
	 * se crea una empresa tendríamos que declarar su año de fundación, con lo cual habría que agregarlo
	 * como un campo más a cada entrada del archivo metrics.json, que es de donde se leen las empresas.
	 * Por ahora lo dejo comentado.
	 */
//	public int getLongevidad()	{
//		int anoActual = Calendar.getInstance().get(Calendar.YEAR);
//		return añoActual - this.añoFundación;
//	}
}
