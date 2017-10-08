package model.repositorios.fuentes;

import java.util.List;

import model.Empresa;

public interface FuenteDeEmpresa {

	public List<Empresa> cargar();
}