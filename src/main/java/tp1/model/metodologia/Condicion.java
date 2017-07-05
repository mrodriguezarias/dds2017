package tp1.model.metodologia;

import java.util.List;

import tp1.model.Empresa;

public interface Condicion {

	List<Empresa> evaluar(List<Empresa> empresas);
}
