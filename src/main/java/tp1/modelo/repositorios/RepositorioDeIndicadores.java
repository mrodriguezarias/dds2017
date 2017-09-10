package tp1.modelo.repositorios;

import java.util.ArrayList;
import java.util.List;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.fuentes.FuenteDeIndicador;

public class RepositorioDeIndicadores {

	private FuenteDeIndicador source;

	public List<String> nombres;
	public List<Indicador> indicators;

	public RepositorioDeIndicadores(FuenteDeIndicador source) {
		this.source = source;
		nombres = source.obtenerNombres();
//		indicators = new ArrayList<>(source.load());
	}

	public void crearIndicadores() { //perdon por esto, no se me ocurre otra cosa de momento
		indicators = new ArrayList<>(source.cargar());
	}
	public List<Indicador> todos() {
		return indicators;
	}
	
	public Indicador encontrar(String name) {
		return indicators.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
	}

	public void agregar(Indicador indicator) {
		nombres.add(indicator.getName());
		indicators.add(indicator);
		guardar();
	}

	public boolean existeIndicador(String nombre){
		return nombres.stream().anyMatch(n -> n.equals(nombre));
	}
	
	public void reemplazar(Indicador viejo, Indicador nuevo) {
		indicators.remove(viejo);
		nombres.remove(viejo.getName());
		indicators.add(nuevo);
		nombres.add(nuevo.getName());
		source.actualizar(viejo, nuevo);
	}

	public void remover(Indicador indicador) {
		nombres.remove(indicador.getName());
		indicators.remove(indicador);
		source.remover(indicador);
		guardar();
	}

	private void guardar() {
		source.guardar(indicators);
	}

}
