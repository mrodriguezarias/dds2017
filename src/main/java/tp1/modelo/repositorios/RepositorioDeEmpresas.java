package tp1.modelo.repositorios;

import java.util.List;

import tp1.modelo.Empresa;
import tp1.modelo.repositorios.fuentes.FuenteDeEmpresa;

public class RepositorioDeEmpresas {

	FuenteDeEmpresa source;
	
	List<Empresa> companies;
	
	public RepositorioDeEmpresas(FuenteDeEmpresa source){
		
		this.source = source;
		companies = this.source.cargar();
	}
	
	public List<Empresa> todos() {
		return companies;
	}
	
	public Empresa encontrar(String name) {
		return companies.stream().filter(c -> c.obtenerNombre().equals(name)).findFirst().orElse(null);
	}
	
}
