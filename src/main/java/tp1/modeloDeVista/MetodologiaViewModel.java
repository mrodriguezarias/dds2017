package tp1.modeloDeVista;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import tp1.modelo.repositorios.Repositorios;

@Observable
public class MetodologiaViewModel {

	private String nombreEmpresa;
	private List<String> nombreMetodologias;
	
	public MetodologiaViewModel(String nombreEmpresa){
		
		this.nombreEmpresa = nombreEmpresa;
		this.nombreMetodologias = Repositorios.obtenerRepositorioDeMetodologias().obtenerNombres();
	}
	
	public String title() {
		return String.format("Aplicar metodología a $%s ", nombreEmpresa);
	}
	
	public List<String> obtenerNombreMetodologias() {
		return nombreMetodologias;
	}
}
