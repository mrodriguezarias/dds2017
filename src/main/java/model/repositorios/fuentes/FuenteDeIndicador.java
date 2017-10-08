package model.repositorios.fuentes;

import java.util.List;

import model.indicador.Indicador;
import model.repositorios.RepositorioDeIndicadores;

public interface FuenteDeIndicador {

	public List<Indicador> cargar();
	
	public List<String> obtenerNombres();
	
	public void guardar(RepositorioDeIndicadores repositorio, List<Indicador> indicators);

	public void remover(Indicador indicador);

	public default void actualizar(Indicador original, Indicador nuevo) {}
}
