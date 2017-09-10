package tp1.modelo.repositorios;

import tp1.modelo.repositorios.fuentes.jpa.FuenteJPADeEmpresa;
import tp1.modelo.repositorios.fuentes.jpa.FuenteJPADeIndicador;
import tp1.modelo.repositorios.fuentes.json.FuenteJsonDeMetodologia;

public class Repositorios {
	
	private static RepositorioDeEmpresas repositorioDeEmpresas;
	private static RepositorioDeIndicadores repositorioDeIndicadores;
	private static RepositorioDeMetodologias repositorioDeMetodologias;
	
	public static RepositorioDeEmpresas obtenerRepositorioDeEmpresas() {
		if(repositorioDeEmpresas == null) {
			FuenteJPADeEmpresa fuenteDeEmpresa = new FuenteJPADeEmpresa();
			repositorioDeEmpresas = new RepositorioDeEmpresas(fuenteDeEmpresa);
		}
		return repositorioDeEmpresas;
	}
	
	public static void establecerRepositorioDeEmpresas(RepositorioDeEmpresas repositorio) {
		repositorioDeEmpresas = repositorio;
	}
	
	public static RepositorioDeIndicadores obtenerRepositorioDeIndicadores() {
		if(repositorioDeIndicadores == null) {
			FuenteJPADeIndicador fuenteDeIndicador = new FuenteJPADeIndicador();
			repositorioDeIndicadores = new RepositorioDeIndicadores(fuenteDeIndicador);
			repositorioDeIndicadores.crearIndicadores();
		}
		return repositorioDeIndicadores;
	}
	
	public static void establecerRepositorioDeIndicadores(RepositorioDeIndicadores repositorio) {
		repositorioDeIndicadores = repositorio;
	}
	
	public static RepositorioDeMetodologias obtenerRepositorioDeMetodologias() {
		if(repositorioDeMetodologias == null) {
			FuenteJsonDeMetodologia fuenteDeMetodologia = new FuenteJsonDeMetodologia();
			repositorioDeMetodologias = new RepositorioDeMetodologias(fuenteDeMetodologia);
		}
		return repositorioDeMetodologias;
	}
	
	public static void establecerRepositorioDeMetodologias(RepositorioDeMetodologias repositorio) {
		repositorioDeMetodologias = repositorio;
	}
}