package tp1.modelo.repositorios.fuentes.json;

import java.util.List;

import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.fuentes.FuenteDeMetodologia;

public class FuenteJsonDeMetodologia implements FuenteDeMetodologia {

	private static final String ARCHIVO_DE_METODOLOGIAS = "Metodologias.json";
	
	public CodificadorJson coder;
	
	public FuenteJsonDeMetodologia(String nombreDeArchivo){
		coder = new CodificadorJson(nombreDeArchivo, Metodología.class);
	}
	
	public FuenteJsonDeMetodologia() {
		this(ARCHIVO_DE_METODOLOGIAS);
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

	@Override
	public void remover(Metodología metodologia) {}

	@Override
	public void persistir(List<Metodología> metodologías) {
		guardar(metodologías);
	}

}
