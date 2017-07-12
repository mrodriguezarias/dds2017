package tp1.modelo.metodología;

import java.util.List;

import tp1.modelo.Empresa;

public interface Condicion {

	List<Empresa> evaluar(List<Empresa> empresas);
}
