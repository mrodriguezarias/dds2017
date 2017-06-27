package tp1.modelo.indicador;

import tp1.modelo.Empresa;

@FunctionalInterface
public interface Expresión {
	double eval(Empresa empresa, short período);
}
