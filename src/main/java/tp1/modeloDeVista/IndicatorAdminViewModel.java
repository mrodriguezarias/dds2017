package tp1.modeloDeVista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.indicador.ConstructorDeIndicador;
import tp1.modelo.indicador.ConstructorDeIndicador.ExcepciónDeFórmulaInválida;
import tp1.modelo.repositorios.Repositorios;

@Observable
public class IndicatorAdminViewModel {
	
	private boolean isEditing;
	
	private String name;
	private String description;
	private String formula;
	private String error;

	private List<String> indicatorNames;
	private String indicatorName;
	
	public IndicatorAdminViewModel() {
		indicatorNames = Repositorios.obtenerRepositorioDeIndicadores().todos().stream().map(i -> i.getName())
				.sorted().collect(Collectors.toList());
		setCreateMode();
	}
	
	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
		isEditing = indicatorName != null;
		updateForm();
	}

	public List<String> getIndicatorNames() {
		return indicatorNames;
	}
	
	public void setIndicatorNames(List<String> indicatorNames) {
		this.indicatorNames = indicatorNames;
	}
	
	public boolean getIsEditing() {
		return isEditing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String getError() {
		return error;
	}

	private void clearForm() {
		this.name = "";
		this.description = "";
		this.formula = "";
		this.error = "";
	}
	
	private void updateForm() {
		Indicador indicator = Repositorios.obtenerRepositorioDeIndicadores().encontrar(indicatorName);
		if(indicator == null) return;
		this.name = indicator.getName();
		this.description = indicator.obtenerDescripción();
		this.formula = indicator.obtenerFórmula();
		this.error = "";
	}
	
	public void setCreateMode() {
		clearForm();
		isEditing = false;
		indicatorName = null;
	}
	
	public void deleteIndicator() {
		Indicador indicator = Repositorios.obtenerRepositorioDeIndicadores().encontrar(indicatorName);
		indicatorNames.remove(indicatorName);
		Repositorios.obtenerRepositorioDeIndicadores().remover(indicator);
		setCreateMode();
	}
	
	private void addIndicatorName(String name) {
		List<String> names = new ArrayList<>(indicatorNames);
		names.add(name);
		Collections.sort(names);
		indicatorNames = names;
		indicatorName = name;
	}
	
	private void addIndicator(Indicador indicator) {
		if(indicatorNames.contains(indicator.getName())) {
			replaceIndicator(indicator);
			return;
		}
		addIndicatorName(indicator.getName());
		Repositorios.obtenerRepositorioDeIndicadores().agregar(indicator);
	}
	
	private void replaceIndicator(Indicador indicator) {
		Indicador prev = Repositorios.obtenerRepositorioDeIndicadores().encontrar(indicatorName);
		if(!indicatorName.equals(indicator.getName())) {
			addIndicatorName(indicator.getName());
			indicatorNames = indicatorNames.stream().filter(n -> !n.equals(prev.getName())).collect(Collectors.toList());
		}
		Repositorios.obtenerRepositorioDeIndicadores().reemplazar(prev, indicator);
	}
	
	public String saveChanges() {
		if(name.isEmpty() || formula.isEmpty()) {
			String error = "Error: ";
			error += name.isEmpty() ? "el nombre" : "la fórmula";
			error += " no puede quedar vací";
			error += name.isEmpty() ? "o." : "a.";
			this.error = error;
			return "";
		}
		
		ConstructorDeIndicador builder = new ConstructorDeIndicador();
		builder.establecerNombre(name);
		builder.establecerDescripción(description);
		builder.establecerFórmula(formula);
		
		try {
			Indicador indicator = builder.construir();
			
			if(isEditing) {
				replaceIndicator(indicator);
			} else {
				addIndicator(indicator);
			}
		} catch (ExcepciónDeFórmulaInválida e) {
			this.error = String.format("Error: %s.", e.getMessage());
			return "";
		}
		
		this.error = "";
		String operation = isEditing ? "actualizado" : "creado";
		this.isEditing = true;
		return String.format("%s %s", name, operation);
	}
	
}
