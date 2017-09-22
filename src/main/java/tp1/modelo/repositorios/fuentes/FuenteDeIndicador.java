package tp1.modelo.repositorios.fuentes;

import java.util.List;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.RepositorioDeIndicadores;

public interface FuenteDeIndicador {

	public List<Indicador> cargar();
	
	public List<String> obtenerNombres();
	
	public void guardar(RepositorioDeIndicadores repositorio, List<Indicador> indicators);

	public void remover(Indicador indicador);

	public default void actualizar(Indicador original, Indicador nuevo) {}
}
