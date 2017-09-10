package tp1.modelo.repositorios.fuentes.json;

import java.util.List;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.fuentes.FuenteDeIndicador;

public class FuenteJsonDeIndicador implements FuenteDeIndicador {
	
	private static final String ARCHIVO_DE_INDICADORES = "indicadores.json";
	
	CodificadorJson coder;
	
	public FuenteJsonDeIndicador(String nombreDeArchivo) {
		coder = new CodificadorJson(nombreDeArchivo, Indicador.class);
	}
	
	public FuenteJsonDeIndicador() {
		this(ARCHIVO_DE_INDICADORES);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Indicador> cargar() {
		
		return (List<Indicador>) coder.read();
	}

	@Override
	public void guardar(List<Indicador> indicators) {
		
		coder.write(indicators);		
	}

	@Override
	public List<String> obtenerNombres() {
		return coder.obtenerCampo("name");
	}

	@Override
	public void remover(Indicador indicador) {
		// TODO Auto-generated method stub
		
	}

}
