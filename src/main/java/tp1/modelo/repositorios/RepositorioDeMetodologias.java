package tp1.modelo.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.fuentes.FuenteDeMetodologia;

public class RepositorioDeMetodologias {
	
	private FuenteDeMetodologia source;
	
	public List<Metodología> metodologias;
	
	public RepositorioDeMetodologias(FuenteDeMetodologia source){
		
		this.source = source;
		metodologias = new ArrayList<>(this.source.cargar());
	}
	
	public List<Metodología> todos(){
		return metodologias;
	}
	
	public Metodología encontrar(String nombre){
		return metodologias.stream().filter(c -> c.obtenerNombre().equals(nombre)).findFirst().orElse(null);
	}
	
	public void agregar(Metodología metodologia) {
		metodologias.add(metodologia);
		guardar();
	}

	public void remover(Metodología metodologia) {
		metodologias.remove(metodologia);
		source.remover(metodologia);
		guardar();
	}
	
	public void reemplazar(Metodología oldMetodologia, Metodología newMetodologia) {
		metodologias.remove(oldMetodologia);
		metodologias.add(newMetodologia);
		source.actualizar(oldMetodologia, newMetodologia);
		guardar();
	}

	private void guardar() {
		source.guardar(metodologias);
	}
	
	public List<String> obtenerNombres(){
		
		return metodologias.stream().map(m -> m.obtenerNombre()).collect(Collectors.toList());
	}
	

}
