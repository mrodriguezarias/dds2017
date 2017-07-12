package tp1.modelo.repositorios.fuentes;

import java.util.List;

import tp1.modelo.Empresa;

public interface FuenteDeEmpresa {

	public List<Empresa> cargar();
}