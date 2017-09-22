package tp1.modelo.repositorios.fuentes;

import java.util.List;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.RepositorioDeMetodologias;


public interface FuenteDeMetodologia {
	
	public List<Metodología> cargar();
		
	public default void guardar(RepositorioDeMetodologias repositorio, List<Metodología> metodologias) {}
		
	public default void remover(Metodología metodologia) {}

	public default void actualizar(Metodología original, Metodología nuevo) {}
}
