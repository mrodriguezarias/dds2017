package tp1.modelo.repositorios.fuentes;

import java.util.List;
import tp1.modelo.metodología.Metodología;


public interface FuenteDeMetodologia {
	
	public List<Metodología> cargar();
		
	public default void guardar(List<Metodología> metodologias) {}
	
	public default void persistir(List<Metodología> metodologías) {}
	
	public default void remover(Metodología metodologia) {}

	public default void actualizar(Metodología original, Metodología nuevo) {}
}
