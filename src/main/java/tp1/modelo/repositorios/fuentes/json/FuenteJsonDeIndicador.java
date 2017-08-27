package tp1.modelo.repositorios.fuentes.json;

import java.util.List;

import tp1.modelo.CodificadorJson;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.fuentes.FuenteDeIndicador;

public class FuenteJsonDeIndicador implements FuenteDeIndicador{
	
	CodificadorJson coder;
	
	public FuenteJsonDeIndicador(String fileName){
		coder = new CodificadorJson(fileName, Indicador.class);
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
	

}
