package tp1.modelo.repositorios.fuentes;

import java.util.List;

import tp1.modelo.indicador.Indicador;

public interface FuenteDelIndicador {

	public List<Indicador> cargar();
	
	public void guardar(List<Indicador> indicadores);
}
