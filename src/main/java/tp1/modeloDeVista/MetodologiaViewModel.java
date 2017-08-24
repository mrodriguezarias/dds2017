package tp1.modeloDeVista;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.model.annotations.Dependencies;
import org.uqbar.commons.model.annotations.Observable;

import tp1.modelo.Empresa;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.Repositorios;

@Observable
public class MetodologiaViewModel {

	private List<String> nombreMetodologias;
	private String nombreMetodologia;

	public MetodologiaViewModel() {
		this.nombreMetodologias = Repositorios.obtenerRepositorioDeMetodologias().todos().stream()
				.filter(m -> m.esAplicable(getEmpresas())).map(m -> m.obtenerNombre()).collect(Collectors.toList());
	}
	
	public List<String> getNombreMetodologias() {
		return nombreMetodologias;
	}
	
	public String getNombreMetodologia() {
		return nombreMetodologia;
	}
	
	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
	}
	
	@Dependencies("nombreMetodologia")
	public List<Empresa> getEmpresas() {
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		List<Empresa> empresasTabla = Repositorios.obtenerRepositorioDeEmpresas().todos();
		if (metodologia == null) return empresasTabla;
		return metodologia.aplicar(empresasTabla);
	}
	
}
