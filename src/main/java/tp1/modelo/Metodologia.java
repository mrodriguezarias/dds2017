package tp1.modelo;

import java.util.List;

public class Metodologia {
	List<Condicion> condiciones;
	
	public boolean esMejor(Empresa mejorEmpresa, Empresa peorEmpresa) {
		return condiciones.stream().allMatch(unaCondicion -> unaCondicion.esMejor(mejorEmpresa, peorEmpresa));
	}
	
	public boolean convieneInvertir(Empresa unaEmpresa)	{
		return condiciones.stream().allMatch(unaCondicion -> unaCondicion.seCumple(unaEmpresa));
	}
}
