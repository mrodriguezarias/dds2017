package tp1.modelo.repositorios;

import tp1.modelo.repositorios.fuentes.FuenteJsonDeEmpresa;
import tp1.modelo.repositorios.fuentes.FuenteJsonDeIndicador;

public class Repositorios {
	
	private static RepositorioDeEmpresas repositorioDeEmpresas;
	private static RepositorioDeIndicadores repositorioDeIndicadores;
	
	public static RepositorioDeEmpresas obtenerRepositorioDeEmpresas() {
		if(repositorioDeEmpresas == null) {
			final String ARCHIVO_DE_CUENTAS = "cuentas.json";
			FuenteJsonDeEmpresa fuenteDeEmpresa = new FuenteJsonDeEmpresa(ARCHIVO_DE_CUENTAS);
			repositorioDeEmpresas = new RepositorioDeEmpresas(fuenteDeEmpresa);
		}
		return repositorioDeEmpresas;
	}
	
	public static void establecerRepositorioDeEmpresas(RepositorioDeEmpresas repositorio) {
		repositorioDeEmpresas = repositorio;
	}
	
	public static RepositorioDeIndicadores obtenerRepositorioDeIndicadores() {
		if(repositorioDeIndicadores == null) {
			final String ARCHIVO_DE_INDICADORES = "indicadores.json";
			FuenteJsonDeIndicador fuenteDeIndicador = new FuenteJsonDeIndicador(ARCHIVO_DE_INDICADORES);
			repositorioDeIndicadores = new RepositorioDeIndicadores(fuenteDeIndicador);
			repositorioDeIndicadores.crearIndicadores();
		}
		return repositorioDeIndicadores;
	}
	
	public static void establecerRepositorioDeIndicadores(RepositorioDeIndicadores repositorio) {
		repositorioDeIndicadores = repositorio;
	}
}