package tp1.modelo.repositorios.fuentes;

import java.util.List;

import tp1.modelo.metodología.Metodología;


public interface FuenteDeMetodologia {
	
	public List<Metodología> cargar();
	
	public void guardar(List<Metodología> metodologias);
}
