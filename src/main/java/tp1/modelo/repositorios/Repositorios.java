package tp1.modelo.repositorios;

import tp1.modelo.repositorios.fuentes.FuenteJsonDeLaEmpresa;
import tp1.modelo.repositorios.fuentes.FuenteJsonDelIndicador;

public class Repositorios {
	
	private static RepositorioDeEmpresas repositorioDeEmpresas;
	private static RepositorioDeIndicadores repositorioDeIndicadores;
	
	public static RepositorioDeEmpresas obtenerRepositorioDeEmpresas() {
		if(repositorioDeEmpresas == null) {
			final String ARCHIVO_DE_CUENTAS = "cuentas.json";
			FuenteJsonDeLaEmpresa fuenteDeEmpresa = new FuenteJsonDeLaEmpresa(ARCHIVO_DE_CUENTAS);
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
			FuenteJsonDelIndicador fuenteDeIndicador = new FuenteJsonDelIndicador(ARCHIVO_DE_INDICADORES);
			repositorioDeIndicadores = new RepositorioDeIndicadores(fuenteDeIndicador);
		}
		return repositorioDeIndicadores;
	}
	
	public static void establecerRepositorioDeIndicadores(RepositorioDeIndicadores repositorio) {
		repositorioDeIndicadores = repositorio;
	}
}
