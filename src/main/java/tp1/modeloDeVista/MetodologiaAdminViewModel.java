package tp1.modeloDeVista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.modelo.metodología.Condición;
import tp1.modelo.metodología.ConstructorDeMetodología;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.Repositorios;

@Observable
public class MetodologiaAdminViewModel {

	
	private boolean isEditing;
	
	private String nombre;
	private String error;

	private List<String> nombreMetodologias;
	private String nombreMetodologia;
	private Condición condicionSeleccionada;

	public MetodologiaAdminViewModel() {
		nombreMetodologias = Repositorios.obtenerRepositorioDeMetodologias().obtenerNombres();
		setCreateMode();
	}
	
	public String getNombreMetodologia() {
		return nombreMetodologia;
	}

	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
		isEditing = nombreMetodologia != null;
		updateForm();
	}

	public List<String> getNombreMetodologias() {
		return nombreMetodologias;
	}
	
	public void setNombreMetodologias(List<String> nombreMetodologias) {
		this.nombreMetodologias = nombreMetodologias;
	}
	
	public Condición getCondicionSeleccionada() {
		return condicionSeleccionada;
	}

	public void setCondicionSeleccionada(Condición condicionSeleccionada) {
		this.condicionSeleccionada = condicionSeleccionada;
	}
	public boolean getIsEditing() {
		return isEditing;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Dependencies("nombreMetodologia")
	public List<String> getCondiciones(){
		
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		if(metodologia == null) return null;
		return metodologia.getNombreCondiciones();		
	}
	
	public String getError() {
		return error;
	}

	private void clearForm() {
		this.nombre = "";
		this.error = "";
	}
	
	private void updateForm() {
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		if(metodologia == null) return;
		this.nombre = metodologia.obtenerNombre();
		this.error = "";
	}
	
	public void setCreateMode() {
		clearForm();
		isEditing = false;
		nombreMetodologia = null;
	}
	
	public void deleteMetodologia() {
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		nombreMetodologias.remove(nombreMetodologia);
		Repositorios.obtenerRepositorioDeMetodologias().remover(metodologia);
		setCreateMode();
	}
	
	private void addNombreMetodologia(String nombre) {
		List<String> listaNombres = new ArrayList<>(nombreMetodologias);
		listaNombres.add(nombre);
		Collections.sort(listaNombres);
		nombreMetodologias = listaNombres;
		nombreMetodologia = nombre;
	}
	
	private void addMetodologia(Metodología metodologia) {
		if(nombreMetodologias.contains(metodologia.obtenerNombre())) {
			replaceMetodologia(metodologia);
			return;
		}
		addNombreMetodologia(metodologia.obtenerNombre());
		Repositorios.obtenerRepositorioDeMetodologias().agregar(metodologia);
	}
	
	private void replaceMetodologia(Metodología metodologia) {
		Metodología prev = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		if(!nombreMetodologia.equals(metodologia.obtenerNombre())) {
			addNombreMetodologia(metodologia.obtenerNombre());
			nombreMetodologias = nombreMetodologias.stream().filter(n -> !n.equals(prev.obtenerNombre())).collect(Collectors.toList());
		}
		Repositorios.obtenerRepositorioDeMetodologias().reemplazar(prev, metodologia);
	}
	
	public String saveChanges() {
		if(nombre.isEmpty()) {
			String error = "Error: el nombre no puede estar vacío";
			this.error = error;
			return "";
		}
		
		ConstructorDeMetodología builder = new ConstructorDeMetodología(nombre);
		
		Metodología metodologia = builder.construir(); 
		
		if(isEditing) {
			replaceMetodologia(metodologia);
		} else {
			addMetodologia(metodologia);
		}
		
		this.error = "";
		String operation = isEditing ? "actualizado" : "creado";
		this.isEditing = true;
		return String.format("%s %s", nombre, operation);
	}
}
