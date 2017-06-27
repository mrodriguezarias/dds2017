package tp1.modelo.repositorios;

import java.util.ArrayList;
import java.util.List;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.fuentes.FuenteDelIndicador;

public class RepositorioDelIndicador {

	private FuenteDelIndicador fuente;

	public List<Indicador> indicadores;

	public RepositorioDelIndicador(FuenteDelIndicador fuente) {
		this.fuente = fuente;
		indicadores = new ArrayList<>(fuente.cargar());
	}

	public List<Indicador> todos() {

		return indicadores;
	}
	
	public Indicador encontrar(String nombre) {
		return indicadores.stream().filter(x -> x.obtenerNombre().equals(nombre)).findFirst().orElse(null);
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

	public void remove(Indicador indicator) {
		indicadores.remove(indicator);
		guardar();
	}

	private void guardar() {
		fuente.guardar(indicadores);
	}

}
