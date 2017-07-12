package tp1.modelo.metodología;

import java.util.List;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public class CondicionTaxativa implements Condicion{

	short desde;
	short hasta; 
	Indicador indicador;
	double valor;
	
	
	@Override
	public List<Empresa> evaluar(List<Empresa> empresas) {
		//TODO
		return empresas;
	}

}
