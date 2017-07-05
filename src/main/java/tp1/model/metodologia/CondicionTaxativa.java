package tp1.model.metodologia;

import java.util.List;

import tp1.model.Empresa;
import tp1.model.indicator.Indicator;

public class CondicionTaxativa implements Condicion{

	short desde;
	short hasta; 
	Indicator indicador;
	double valor;
	
	
	@Override
	public List<Empresa> evaluar(List<Empresa> empresas) {
		//TODO
		return empresas;
	}

}
