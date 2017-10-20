package model.repositorios;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import model.Empresa;
import model.indicador.Indicador;
import model.repositorios.fuentes.FuenteDeEmpresa;

public class RepositorioDeEmpresas {

	FuenteDeEmpresa source;
	
	List<Empresa> companies;
	
	public RepositorioDeEmpresas(FuenteDeEmpresa source){
		
		this.source = source;
		companies = this.source.cargar();
	}
	
	public List<Empresa> todos() {
		return companies;
	}
	
	public List<String> obtenerNombres(){
		return companies.stream().map(e -> e.getNombre()).collect(Collectors.toList());
	}
	
	public Empresa encontrar(String name) {
		return companies.stream().filter(c -> c.getNombre().equals(name)).findFirst().orElse(null);
	}

	public List<Short> getAllPeriodos()	{
		List<Short> periodos = companies.stream().map(unaEmpresa -> unaEmpresa.obtenerPeríodos())
				.flatMap(unaListaDePeriodos -> unaListaDePeriodos.stream()).distinct()
				.sorted(Collections.reverseOrder()).collect(Collectors.toList());
		return periodos;
	}
}
