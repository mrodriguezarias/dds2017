package tp1.modelo.metodología;

import java.util.List;

import tp1.modelo.Empresa;

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
