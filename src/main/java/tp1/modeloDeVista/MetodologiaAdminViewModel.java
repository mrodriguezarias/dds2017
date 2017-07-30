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
	private List<Condición> condiciones;
	private Condición condicionSeleccionada;
	
	private ConstructorDeMetodología builder;

	public MetodologiaAdminViewModel() {
		nombreMetodologias = Repositorios.obtenerRepositorioDeMetodologias().obtenerNombres();
		builder = new ConstructorDeMetodología();
		setCreateMode();
	}
	
	public String getNombreMetodologia() {
		return nombreMetodologia;
	}

	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
		isEditing = nombreMetodologia != null;
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		if(metodologia != null)
			this.builder = new ConstructorDeMetodología(metodologia);
		else 
			this.builder = new ConstructorDeMetodología();
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
	
	public void eliminarCondicion(){
		if(condicionSeleccionada != null)
			builder.eliminarCondicion(condicionSeleccionada.getNombre());
		actualizarTabla();
	}
	
	
	@Dependencies("nombreMetodologia")
	public List<Condición> getCondiciones(){
		actualizarTabla();
		return condiciones;		
	}
	
	public void actualizarTabla() {
		condiciones = this.builder.getCondiciones();		
	}

	public String getError() {
		return error;
	}
	
	@Dependencies("condicionSeleccionada")
	public boolean getIsCondicionSeleccionada(){
		return condicionSeleccionada != null;
	}

	public void clearForm() {
		this.nombreMetodologia = "";
		this.nombre = "";
		this.error = "";
	}
	
	private void updateForm() {
		Metodología metodologia = Repositorios.obtenerRepositorioDeMetodologias().encontrar(nombreMetodologia);
		if(metodologia == null) return;
		this.nombre = metodologia.obtenerNombre();
		this.error = "";
		actualizarTabla();
	}
	
	public void setCreateMode() {
		this.builder = new ConstructorDeMetodología();
		clearForm();
		this.nombreMetodologia = null;
		isEditing = false;
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
			this.error = "Error: el nombre no puede estar vacío";
			return "";
		}
		if (this.builder.getCondiciones().isEmpty()){
			this.error = "Error: Debe existir al menos una Condición";
			return "";
		}
		this.builder.setNombre(nombre);
		
		Metodología metodologia = builder.construir(); 
		
		if(isEditing) {
			replaceMetodologia(metodologia);
		} else {
			addMetodologia(metodologia);
		}
		
		this.error = "";
		String operacion = isEditing ? "actualizada" : "creada";
		this.isEditing = true;
		return String.format("%s %s", nombre, operacion);
	}
	
	public ConstructorDeMetodología getBuilderMetodologia()	{
		return this.builder;
	}
	
}
