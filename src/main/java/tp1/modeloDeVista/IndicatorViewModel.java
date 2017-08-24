package tp1.modeloDeVista;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.model.annotations.Dependencies;
import org.uqbar.commons.model.annotations.Observable;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.Repositorios;

@Observable
public class IndicatorViewModel {

	private String companyName;
	private short period;
	
	private List<String> indicatorNames;
	private String indicatorName;

	public IndicatorViewModel(String companyName, short period) {
		this.companyName = companyName;
		this.period = period;
		
		Empresa company = Repositorios.obtenerRepositorioDeEmpresas().encontrar(companyName);
		this.indicatorNames = Repositorios.obtenerRepositorioDeIndicadores().todos().stream()
				.filter(i -> i.esVálidoParaContexto(company, period))
				.map(i -> i.getName()).collect(Collectors.toList());
	}
	
	public String title() {
		return String.format("Aplicar indicador a $%s en %s", companyName, period);
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public List<String> getIndicatorNames() {
		return indicatorNames;
	}
	
	@Dependencies("indicatorName")
	public String getDescription() {
		Indicador indicator = Repositorios.obtenerRepositorioDeIndicadores().encontrar(indicatorName);
		return indicator.obtenerDescripción();
	}
	
	@Dependencies("indicatorName")
	public String getValue() {
		Indicador indicator = Repositorios.obtenerRepositorioDeIndicadores().encontrar(indicatorName);
		Empresa company = Repositorios.obtenerRepositorioDeEmpresas().encontrar(companyName);
		MeasureComponent component = new MeasureComponent(indicator, company, period);
		return component.getFullValue();
	}
	
	@Dependencies("indicatorName")
	public String getFormula() {
		Indicador indicator = Repositorios.obtenerRepositorioDeIndicadores().encontrar(indicatorName);
		return indicator.obtenerFórmula();
	}

}
