package tp1;

import java.util.ArrayList;
import java.util.List;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.fuentes.FuenteDelIndicador;

public class TestIndicatorSource implements FuenteDelIndicador {

	@Override
	public List<Indicador> cargar() {
		return new ArrayList<>();
	}

	@Override
	public void guardar(List<Indicador> indicators) {
		
	}
}