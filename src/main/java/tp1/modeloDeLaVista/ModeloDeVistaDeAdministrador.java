package tp1.modeloDeLaVista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import tp1.Aplicación;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.indicador.ConstructorDelIndicador;
import tp1.modelo.indicador.ConstructorDelIndicador.ExcepciónDeFórmulaInválida;

@Observable
public class ModeloDeVistaDeAdministrador {
	
	private boolean estáEditando;
	
	private String nombre;
	private String descripción;
	private String fórmula;
	private String error;

	private List<String> nombresDeLosIndicadores;
	private String nombreDelIndicador;
	
	public ModeloDeVistaDeAdministrador() {
		nombresDeLosIndicadores = Aplicación.repositorioDeIndicadores.todos().stream().map(i -> i.obtenerNombre())
				.sorted().collect(Collectors.toList());
		setCreateMode();
	}
	
	public String getNombreDelIndicador() {
		return nombreDelIndicador;
	}

	public void setNombreDelIndicador(String nombreDelIndicador) {
		this.nombreDelIndicador = nombreDelIndicador;
		estáEditando = nombreDelIndicador != null;
		actualizarFormulario();
	}

	public List<String> getNombresDeLosIndicadores() {
		return nombresDeLosIndicadores;
	}
	
	public void setNombresDeLosIndicadores(List<String> nombresDeLosIndicadores) {
		this.nombresDeLosIndicadores = nombresDeLosIndicadores;
	}
	
	public boolean getEstáEditando() {
		return estáEditando;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}

	public String getFórmula() {
		return fórmula;
	}

	public void setFórmula(String fórmula) {
		this.fórmula = fórmula;
	}
	
	public String getError() {
		return error;
	}

	private void vaciarFormulario() {
		this.nombre = "";
		this.descripción = "";
		this.fórmula = "";
		this.error = "";
	}
	
	private void actualizarFormulario() {
		Indicador indicador = Aplicación.repositorioDeIndicadores.encontrar(nombreDelIndicador);
		if(indicador == null) return;
		this.nombre = indicador.obtenerNombre();
		this.descripción = indicador.obtenerDescripción();
		this.fórmula = indicador.obtenerFórmula().comoCadenaDeCaracteres();
		this.error = "";
	}
	
	public void setCreateMode() {
		vaciarFormulario();
		estáEditando = false;
		nombreDelIndicador = null;
	}
	
	public void eliminarIndicador() {
		Indicador indicator = Aplicación.repositorioDeIndicadores.encontrar(nombreDelIndicador);
		nombresDeLosIndicadores.remove(nombreDelIndicador);
		Aplicación.repositorioDeIndicadores.remove(indicator);
		setCreateMode();
	}
	
	private void agregarNombreDeIndicador(String nombre) {
		List<String> names = new ArrayList<>(nombresDeLosIndicadores);
		names.add(nombre);
		Collections.sort(names);
		nombresDeLosIndicadores = names;
		nombreDelIndicador = nombre;
	}
	
	private void agregarIndicador(Indicador indicador) {
		if(nombresDeLosIndicadores.contains(indicador.obtenerNombre())) {
			reemplazarIndicador(indicador);
			return;
		}
		agregarNombreDeIndicador(indicador.obtenerNombre());
		Aplicación.repositorioDeIndicadores.agregar(indicador);
	}
	
	private void reemplazarIndicador(Indicador indicador) {
		Indicador prev = Aplicación.repositorioDeIndicadores.encontrar(nombreDelIndicador);
		if(!nombreDelIndicador.equals(indicador.obtenerNombre())) {
			agregarNombreDeIndicador(indicador.obtenerNombre());
			nombresDeLosIndicadores = nombresDeLosIndicadores.stream().filter(n -> !n.equals(prev.obtenerNombre())).collect(Collectors.toList());
		}
		Aplicación.repositorioDeIndicadores.reemplazar(prev, indicador);
	}
	
	public String guardarCambios() {
		if(nombre.isEmpty() || fórmula.isEmpty()) {
			String error = "Error: ";
			error += nombre.isEmpty() ? "el nombre" : "la fórmula";
			error += " no puede quedar vací";
			error += nombre.isEmpty() ? "o." : "a.";
			this.error = error;
			return "";
		}
		
		ConstructorDelIndicador constructor = new ConstructorDelIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerDescripción(descripción);
		constructor.establecerFórmula(fórmula);
		
		try {
			Indicador indicador = constructor.construir();
			
			if(estáEditando) {
				reemplazarIndicador(indicador);
			} else {
				agregarIndicador(indicador);
			}
		} catch (ExcepciónDeFórmulaInválida e) {
			this.error = String.format("Error: %s.", e.getMessage());
			return "";
		}
		
		this.error = "";
		String operación = estáEditando ? "actualizado" : "creado";
		this.estáEditando = true;
		return String.format("%s %s", nombre, operación);
	}
	
}
