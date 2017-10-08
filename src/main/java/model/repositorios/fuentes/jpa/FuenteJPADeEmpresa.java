package model.repositorios.fuentes.jpa;

import java.util.List;

import model.Empresa;
import model.repositorios.fuentes.FuenteDeEmpresa;

public class FuenteJPADeEmpresa implements FuenteDeEmpresa {

	AdministradorJPA<Empresa> jpa = new AdministradorJPA<>(Empresa.class);
	
	@Override
	public List<Empresa> cargar() {
		return jpa.obtenerTodos();
	}

}
