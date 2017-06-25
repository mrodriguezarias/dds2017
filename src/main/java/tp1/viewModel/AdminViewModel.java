package tp1.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import tp1.App;
import tp1.model.indicator.Indicator;
import tp1.model.indicator.IndicatorBuilder;
import tp1.model.indicator.IndicatorBuilder.InvalidFormulaException;

@Observable
public class AdminViewModel {
	
	private boolean isEditing;
	
	private String name;
	private String description;
	private String formula;
	private String error;

	private List<String> indicatorNames;
	private String indicatorName;
	
	public AdminViewModel() {
		indicatorNames = App.indicatorRepository.all().stream().map(i -> i.getName())
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
		Indicator indicator = App.indicatorRepository.find(indicatorName);
		if(indicator == null) return;
		this.name = indicator.getName();
		this.description = indicator.getDescription();
		this.formula = indicator.getFormula().asString();
		this.error = "";
	}
	
	public void setCreateMode() {
		clearForm();
		isEditing = false;
		indicatorName = null;
	}
	
	public void deleteIndicator() {
		Indicator indicator = App.indicatorRepository.find(indicatorName);
		indicatorNames.remove(indicatorName);
		App.indicatorRepository.remove(indicator);
		setCreateMode();
	}
	
	private void addIndicatorName(String name) {
		List<String> names = new ArrayList<>(indicatorNames);
		names.add(name);
		Collections.sort(names);
		indicatorNames = names;
		indicatorName = name;
	}
	
	private void addIndicator(Indicator indicator) {
		if(indicatorNames.contains(indicator.getName())) {
			replaceIndicator(indicator);
			return;
		}
		addIndicatorName(indicator.getName());
		App.indicatorRepository.add(indicator);
	}
	
	private void replaceIndicator(Indicator indicator) {
		Indicator prev = App.indicatorRepository.find(indicatorName);
		if(!indicatorName.equals(indicator.getName())) {
			addIndicatorName(indicator.getName());
			indicatorNames = indicatorNames.stream().filter(n -> !n.equals(prev.getName())).collect(Collectors.toList());
		}
		App.indicatorRepository.replace(prev, indicator);
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
		
		IndicatorBuilder builder = new IndicatorBuilder();
		builder.setName(name);
		builder.setDescription(description);
		builder.setFormula(formula);
		
		try {
			Indicator indicator = builder.build();
			
			if(isEditing) {
				replaceIndicator(indicator);
			} else {
				addIndicator(indicator);
			}
		} catch (InvalidFormulaException e) {
			this.error = String.format("Error: %s.", e.getMessage());
			return "";
		}
		
		this.error = "";
		String operation = isEditing ? "actualizado" : "creado";
		this.isEditing = true;
		return String.format("%s %s", name, operation);
	}
	
}
