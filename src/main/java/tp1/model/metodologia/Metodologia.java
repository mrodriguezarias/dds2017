package tp1.model.metodologia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import tp1.model.Empresa;

public class Metodologia {
	
	public Metodologia(List<Condicion> condiciones) {
		this.condiciones = condiciones;
		this.nombreEmpresas = new ArrayList<>();
		this.tabla = new TreeMap<String, Integer>();
	}
	public List<Condicion> condiciones;
	public List<String> nombreEmpresas;
	private TreeMap<String, Integer> tabla;
	
	public List<String> evaluar(List<Empresa> empresas){ //fixme no 
		
		evaluarCondiciones(empresas);
			
		// stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
		tabla.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
        .collect(Collectors.toMap(
          Map.Entry::getKey, 
          Map.Entry::getValue, 
          (e1, e2) -> e1, 
          LinkedHashMap::new
        ));
		
		return new ArrayList<String>(tabla.keySet());
		
	}

	private void evaluarCondiciones(List<Empresa> empresas) {
		
		evaluarCondTaxativas(empresas); 	
		//Inicializo La tabla 
		nombreEmpresas = empresas.stream().map(e -> e.getName()).collect(Collectors.toList());
		nombreEmpresas.stream().forEach(n-> tabla.put(n, 0)); 
		
		evaluarCondMixtas(empresas);
		evaluarCondComparativas(empresas);		
	}


	private void evaluarCondTaxativas(List<Empresa> empresas) {
		List<Condicion> condicionesTax = condiciones.stream()
				.filter(c-> c.getClass().getSimpleName().equals("CondicionTaxativa"))
				.collect(Collectors.toList()); //FIXME alguna forma mas facil de filtrar esto
		
		condicionesTax.stream().forEach(c-> c.evaluar(empresas)); 
		
	}
	
	private void evaluarCondMixtas(List<Empresa> empresas) {
		//FIXME codigo repetido con evaluarCondComparativas
		int i;
		Condicion unaCondicion;
		List<Condicion> condicionesMix = condiciones.stream()
				.filter(c-> c.getClass().getSimpleName().equals("CondicionComparativa"))
				.collect(Collectors.toList()); //FIXME alguna forma mas facil de filtrar esto
	
		for (i=0; i < condicionesMix.size(); i++){
			unaCondicion =  condicionesMix.get(i);
			unaCondicion.evaluar(empresas);
			actualizarTabla(empresas);			
		}	
		
	}
	private void evaluarCondComparativas(List<Empresa> empresas) {
		int i;
		Condicion unaCondicion;
		List<Condicion> condicionesComp = condiciones.stream()
				.filter(c-> c.getClass().getSimpleName().equals("CondicionComparativa"))
				.collect(Collectors.toList()); //FIXME alguna forma mas facil de filtrar esto
	
		for (i=0; i < condicionesComp.size(); i++){
			unaCondicion =  condicionesComp.get(i);
			unaCondicion.evaluar(empresas);
			actualizarTabla(empresas);			
		}		
	}



	private void actualizarTabla(List<Empresa> empresas) {
		int i,k, aux;
		Empresa empresa;
		//elimino de la tabla todas las empresas que ya no estan en la lista (por condMixtas)
		this.nombreEmpresas = empresas.stream().map(e -> e.getName()).collect(Collectors.toList());
		this.tabla.entrySet().removeIf(x-> !(nombreEmpresas.contains(x.getKey())));
		
		//agrego puntos segun posicion
		
		i= empresas.size();
		for (k=0; k< empresas.size(); k++){ //itero con for porque forEach no permite usar var local.
			empresa = empresas.get(k);
			aux = tabla.get(empresa.getName());
			tabla.put(empresa.getName(), aux + --i); //no reemplaza; si existe lo actualiza			
		}		
	}
	
}
