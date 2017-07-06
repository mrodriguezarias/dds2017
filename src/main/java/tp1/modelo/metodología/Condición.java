package tp1.modelo.metodología;

import java.util.List;

import tp1.modelo.Empresa;

public interface Condición {
	boolean esAplicable(List<Empresa> empresas);
	List<Empresa> aplicar(List<Empresa> empresas);
}
