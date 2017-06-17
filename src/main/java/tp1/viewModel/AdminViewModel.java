package tp1.viewModel;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import tp1.model.Indicator;

@Observable
public class AdminViewModel {
	
	private boolean isEditing;
	
	private String name;
	private String description;
	private String formula;
	private String error;

	private List<Indicator> indicators;
	private Indicator indicator;
	
	public AdminViewModel() {
//		indicators = new ArrayList<>(Database.getInstance().getIndicators());
//		indicators.add(0, Indicator.EMPTY);
		setCreateMode();
	}
	
	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
//		isEditing = indicator != Indicator.EMPTY;
		updateForm();
	}

	public List<Indicator> getIndicators() {
		return indicators;
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
		this.name = indicator.getName();
		this.description = indicator.getDescription();
		this.formula = indicator.getFormula();
		this.error = "";
	}
	
	public void setCreateMode() {
		clearForm();
		isEditing = false;
		indicator = indicators.get(0);
	}
	
//	private void updateIndicators() {
//		List<Indicator> newIndicators = indicators.subList(1, indicators.size());
//		Database.getInstance().setIndicators(newIndicators);
//	}
	
//	public void deleteIndicator() {
//		List<Indicator> newIndicators = new ArrayList<>(indicators);
//		newIndicators.remove(indicator);
//		setCreateMode();
//		indicators = newIndicators;
//		updateIndicators();
//	}
	
//	private void addIndicator(Indicator indicator) {
//		List<Indicator> newIndicators = new ArrayList<>(indicators); 
//		if(newIndicators.contains(indicator)) {
//			newIndicators.remove(indicator);
//		}
//		newIndicators.add(indicator);
//		Collections.sort(newIndicators);
//		indicators = newIndicators;
//		this.indicator = indicator;
//	}
	
	public String saveChanges() {
		return "";
//		this.name = name.toUpperCase();
//		Indicator newIndicator = new Indicator(name, description, formula);
//		
//		if(name.isEmpty() || formula.isEmpty()) {
//			String error = "Error: ";
//			error += name.isEmpty() ? "el nombre" : "la fórmula";
//			error += " no puede quedar vací";
//			error += name.isEmpty() ? "o." : "a.";
//			this.error = error;
//			return "";
//		}
//		
//		try {
//			newIndicator.tryGetValue();
//			
//			if(isEditing) {
//				indicator.update(name, description, formula);				
//			} else {
//				addIndicator(newIndicator);
//			}
//			
//			updateIndicators();
//			this.error = "";
//		} catch (InvalidFormulaException e) {
//			this.error = e.getMessage();
//			return "";
//		}
//		
//		String operation = isEditing ? "actualizado" : "creado";
//		this.isEditing = true;
//		return String.format("%s %s", name, operation);
	}
	
}
