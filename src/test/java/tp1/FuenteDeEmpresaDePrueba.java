package tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import tp1.modelo.Cuenta;
import tp1.modelo.Empresa;
import tp1.modelo.repositorios.Repositorios;
import tp1.modelo.repositorios.fuentes.FuenteDeEmpresa;

public class FuenteDeEmpresaDePrueba implements FuenteDeEmpresa {
	
	private List<Empresa> empresas;

	@Override
	public List<Empresa> cargar() {
		empresas = new ArrayList<>();
		
		crearEmpresa("E1", empresa -> {
			crearCuenta("C1", empresa, 2009, 13);
			crearCuenta("C1", empresa, 2010, 14);
			crearCuenta("C2", empresa, 2010, 3598732);
			crearCuenta("C3", empresa, 2010, 2347806);
		});
		
		crearEmpresa("E2", empresa -> {
			crearCuenta("C1", empresa, 2011, 4368973);
			crearCuenta("C3", empresa, 2011, 6329852);
		});
		
		crearEmpresa("E3", empresa -> {
			crearCuenta("C1", empresa, 2010, 1);
			crearCuenta("C1", empresa, 2011, 2);
			crearCuenta("C1", empresa, 2012, 0);
			crearCuenta("C1", empresa, 2013, 4);
		});
		
		return empresas;
	}
	
	private void crearEmpresa(String nombre, Consumer<Empresa> consumidor) {
		Empresa empresa = new Empresa(nombre);
		consumidor.accept(empresa);
		empresas.add(empresa);
	}
	
	private void crearCuenta(String nombre, Empresa empresa, int período, double valor) {
		Cuenta cuenta = new Cuenta(nombre, "", empresa.obtenerNombre(), (short) período, valor);
		empresa.agregarCuenta(cuenta);
	}
	
	public static List<Empresa> empresas(String... nombres) {
		return Arrays.asList(nombres).stream().map(nombre ->
			Repositorios.obtenerRepositorioDeEmpresas().encontrar(nombre)).collect(Collectors.toList());
	}
}