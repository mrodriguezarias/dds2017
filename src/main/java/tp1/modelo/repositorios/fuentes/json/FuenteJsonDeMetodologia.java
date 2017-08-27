package tp1.modelo.repositorios.fuentes.json;

import java.util.List;

import tp1.modelo.CodificadorJson;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.fuentes.FuenteDeMetodologia;

public class FuenteJsonDeMetodologia implements FuenteDeMetodologia {

	public CodificadorJson coder;
	
	public FuenteJsonDeMetodologia(String fileName){
		coder = new CodificadorJson(fileName, Metodología.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Metodología> cargar() {
		
		return (List<Metodología>) coder.read();
	}

	@Override
	public void guardar(List<Metodología> metodologias) {

		coder.write(metodologias);
	}

}
