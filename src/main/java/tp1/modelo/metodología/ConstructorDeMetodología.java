package tp1.modelo.metodología;

import java.util.ArrayList;
import java.util.List;

public class ConstructorDeMetodología {
	
	private String nombre;
	private List<CondiciónTaxativa> condicionesTaxativas;
	private List<CondiciónComparativa> condicionesComparativas;
	
	public ConstructorDeMetodología(String nombre) {
		this.nombre = nombre;
		this.condicionesTaxativas = new ArrayList<>();
		this.condicionesComparativas = new ArrayList<>();
	}
	
	public void agregarCondición(CondiciónTaxativa condiciónTaxativa) {
		condicionesTaxativas.add(condiciónTaxativa);
	}
	
	public void agregarCondición(CondiciónComparativa condiciónComparativa) {
		condicionesComparativas.add(condiciónComparativa);
	}
	
	public void agregarCondición(CondiciónTaxocomparativa condiciónTaxocomparativa) {
		condicionesTaxativas.add(condiciónTaxocomparativa.obtenerCondiciónTaxativa());
		condicionesComparativas.add(condiciónTaxocomparativa.obtenerCondiciónComparativa());
	}
	
	public Metodología construir() {
		return new Metodología(nombre, condicionesTaxativas, condicionesComparativas);
	}
	
}