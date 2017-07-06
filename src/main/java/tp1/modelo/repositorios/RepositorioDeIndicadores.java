package tp1.modelo.repositorios;

import java.util.ArrayList;
import java.util.List;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.fuentes.FuenteDeIndicador;
import tp1.modelo.repositorios.fuentes.FuenteJsonDelIndicador;

public class RepositorioDeIndicadores {

	private FuenteDeIndicador fuente;

	public List<Indicador> indicadores;

	public RepositorioDeIndicadores(FuenteDeIndicador fuente) {
		this.fuente = fuente;
		indicadores = new ArrayList<>(fuente.cargar());
	}
	
	public RepositorioDeIndicadores(String nombreDelArchivo) {
		List<Indicador> indicadores = new FuenteJsonDelIndicador(nombreDelArchivo).cargar();
		this.indicadores.addAll(indicadores);
	}

	public List<Indicador> todos() {

		return indicadores;
	}
	
	public Indicador encontrar(String nombre) {
		return indicadores.stream().filter(i -> i.obtenerNombre().equals(nombre)).findFirst().orElse(null);
	}
	
	public boolean contiene(String nombre) {
		return indicadores.size() == 0 || indicadores.stream().anyMatch(i -> i.obtenerNombre().equals(nombre));
	}

	public void agregar(Indicador indicador) {
		indicadores.add(indicador);
		guardar();
	}

	public void reemplazar(Indicador viejoIndicador, Indicador nuevoIndicador) {
		indicadores.remove(viejoIndicador);
		indicadores.add(nuevoIndicador);
		guardar();
	}

	public void remover(Indicador indicador) {
		indicadores.remove(indicador);
		guardar();
	}

	private void guardar() {
		fuente.guardar(indicadores);
	}

}
