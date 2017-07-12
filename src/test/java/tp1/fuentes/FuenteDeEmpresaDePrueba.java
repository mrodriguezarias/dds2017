package tp1.fuentes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		crearEmpresas();
		return empresas;
	}
	
	protected void crearEmpresas() {
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
			crearCuenta("C3", empresa, 2011, 241);
			crearCuenta("C3", empresa, 2012, 434);
			crearCuenta("C3", empresa, 2013, 127);
		});
		
		crearEmpresa("E4", empresa -> {
			crearCuenta("C3", empresa, 2010, 123324432);
			crearCuenta("C3", empresa, 2011, 524);
			crearCuenta("C3", empresa, 2012, 413);
			crearCuenta("C3", empresa, 2013, 657);
		});
	}
	
	protected void crearEmpresa(String nombre, Consumer<Empresa> consumidor) {
		Empresa empresa = new Empresa(nombre);
		consumidor.accept(empresa);
		empresas.add(empresa);
	}
	
	protected void crearCuenta(String nombre, Empresa empresa, int período, double valor) {
		Cuenta cuenta = new Cuenta(nombre, "", empresa.obtenerNombre(), (short) período, valor);
		empresa.agregarCuenta(cuenta);
	}
	
	public static List<Empresa> empresas() {
		return Collections.emptyList();
	}
	
	public static List<Empresa> empresas(String... nombres) {
		return Arrays.asList(nombres).stream().map(nombre ->
			Repositorios.obtenerRepositorioDeEmpresas().encontrar(nombre)).collect(Collectors.toList());
	}
	
	public static boolean empresasSonIguales(List<Empresa> empresas1, List<Empresa> empresas2) {
		int tamaño = empresas1.size();
		if(tamaño != empresas2.size()) {
			return false;
		}
		for(int i = 0; i < tamaño; i++) {
			Empresa empresa1 = empresas1.get(i);
			Empresa empresa2 = empresas2.get(i);
			if(!empresa1.obtenerNombre().equals(empresa2.obtenerNombre())) {
				return false;
			}
		}
		return true;
	}
}