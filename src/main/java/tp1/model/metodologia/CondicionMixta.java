package tp1.model.metodologia;

import java.util.List;
import java.util.stream.Collectors;

import tp1.model.Empresa;

public class CondicionMixta implements Condicion {

	public CondicionMixta(Condicion cTaxativa, Condicion cComparativa) {
		this.cTaxativa = cTaxativa;
		this.cComparativa = cComparativa;
	}

	Condicion cTaxativa;
	Condicion cComparativa;
	
	@Override
	public List<Empresa> evaluar(List<Empresa> empresas) {
		// TODO 
		return empresas;
	}
	

}
