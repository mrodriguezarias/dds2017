package tp1.modelo.repositorios.fuentes.jpa;

import java.util.List;

import tp1.modelo.Empresa;
import tp1.modelo.repositorios.fuentes.FuenteDeEmpresa;

public class FuenteJPADeEmpresa implements FuenteDeEmpresa {

	AdministradorJPA<Empresa> jpa = new AdministradorJPA<>(Empresa.class);
	
	@Override
	public List<Empresa> cargar() {
		return jpa.obtenerTodos();
	}

}
