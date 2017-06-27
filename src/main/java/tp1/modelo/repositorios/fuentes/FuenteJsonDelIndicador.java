package tp1.modelo.repositorios.fuentes;

import java.util.List;

import tp1.modelo.CodificadorJson;
import tp1.modelo.indicador.Indicador;

public class FuenteJsonDelIndicador implements FuenteDelIndicador{
	
	CodificadorJson codificador;
	
	public FuenteJsonDelIndicador(String nombreDeArchivo){
		codificador = new CodificadorJson(nombreDeArchivo, Indicador.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Indicador> cargar() {
		
		return (List<Indicador>) codificador.leer();
	}

	@Override
	public void guardar(List<Indicador> indicadores) {
		
		codificador.escribir(indicadores);		
	}
	

}
