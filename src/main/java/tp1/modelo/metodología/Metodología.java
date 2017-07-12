package tp1.modelo.metodología;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tp1.modelo.Empresa;

public class Metodología {
	
	private String nombre;
	private List<Condición> condicionesTaxativas;
	private List<Condición> condicionesComparativas;
	
	public Metodología(String nombre, List<Condición> condicionesTaxativas, List<Condición> condicionesComparativas) {
		this.nombre = nombre;
		this.condicionesTaxativas = condicionesTaxativas;
		this.condicionesComparativas = condicionesComparativas;
	}

	public String obtenerNombre() {
		return nombre;
	}
	
	public boolean esAplicable(List<Empresa> empresas) {
		return Stream.concat(condicionesTaxativas.stream(), condicionesComparativas.stream())
				.allMatch(condición -> condición.esAplicable(empresas));
	}

	public List<Empresa> aplicar(List<Empresa> empresas) {
		return aplicarCondicionesComparativas(aplicarCondicionesTaxativas(empresas));
	}
	
	private List<Empresa> aplicarCondicionesTaxativas(List<Empresa> empresas) {
		for(Condición condiciónTaxativa : condicionesTaxativas) {
			empresas = condiciónTaxativa.aplicar(empresas);
		}
		return empresas;
	}
	
	private List<Empresa> aplicarCondicionesComparativas(List<Empresa> empresas) {
		Map<String, Integer> pesos = new HashMap<>();
		
		for(Condición condiciónComparativa : condicionesComparativas) {
			actualizarPesos(pesos, obtenerFactor(condiciónComparativa), condiciónComparativa.aplicar(empresas));
		}
		
		return obtenerEmpresasOrdenadasPorPeso(pesos, empresas);
	}

	private void actualizarPesos(Map<String, Integer> pesos, int factor, List<Empresa> empresas) {
		// Agrego puntos segun posición
		int i = empresas.size();
		for(String empresa : empresas.stream().map(e -> e.obtenerNombre()).collect(Collectors.toList())) {
			Integer peso = pesos.get(empresa);
			if(peso == null) peso = 0;
			
			peso += factor * i--;
			pesos.put(empresa, peso); // no reemplaza; si existe lo actualiza			
		}		
	}
	
	private int obtenerFactor(Condición condiciónComparativa) {
		int índice = condicionesComparativas.indexOf(condiciónComparativa);
		int tamaño = condicionesComparativas.size();
		return tamaño - índice;
	}
	
	private List<Empresa> obtenerEmpresasOrdenadasPorPeso(Map<String, Integer> pesos, List<Empresa> empresas) {
		return pesos.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.map(e -> obtenerEmpresaDeNombre(e.getKey(), empresas)).collect(Collectors.toList());
	}
	
	private Empresa obtenerEmpresaDeNombre(String nombre, List<Empresa> empresas) {
		return empresas.stream().filter(empresa -> empresa.obtenerNombre().equals(nombre)).findFirst().get();
	}
	
}
