package tp1.modelo.repositorios;

import java.util.List;

import tp1.modelo.Empresa;
import tp1.modelo.repositorios.fuentes.FuenteDeEmpresa;

public class RepositorioDeEmpresas {

	FuenteDeEmpresa fuente;
	
	List<Empresa> empresas;
	
	public RepositorioDeEmpresas(FuenteDeEmpresa fuente){
		
		this.fuente = fuente;
		empresas = this.fuente.cargar();
	}
	
	public List<Empresa> todos() {
		return empresas;
	}
	
	public Empresa encontrar(String nombre) {
		return empresas.stream().filter(c -> c.obtenerNombre().equals(nombre)).findFirst().orElse(null);
	}
	
}
