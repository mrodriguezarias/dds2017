package tp1.modeloDeVista;

import java.util.List;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.modelo.Empresa;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.Repositorios;

@Observable
public class MetodologiaViewModel {

	private List<String> nombreMetodologias;
	private List<Empresa> empresas;	
	
	private String nombreMetodologia;


	public MetodologiaViewModel(){
		
		this.nombreMetodologias = Repositorios.obtenerRepositorioDeMetodologias().obtenerNombres();
		this.empresas = Repositorios.obtenerRepositorioDeEmpresas().todos();
	}
	
	public String title() {
		return String.format("Aplicar metodología");
	}
	
	public List<String> getNombreMetodologias() {
		return nombreMetodologias;
	}
	
	public String getNombreMetodologia(){
		return nombreMetodologia;
	}
	
	public void setNombreMetodologia(String nombreMetodologia){
		this.nombreMetodologia = nombreMetodologia;
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	@Dependencies("nombreMetodologia")
	public List<Empresa> getListaEmpresas(){
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		if (metodologia == null) return null;
		return metodologia.aplicar(empresas);
	}
	


}
