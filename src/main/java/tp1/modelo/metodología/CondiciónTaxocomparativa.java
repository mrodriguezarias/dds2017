package tp1.modelo.metodología;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;

public final class CondiciónTaxocomparativa extends Condición {
	
	@JsonProperty
	private CondiciónTaxativa condiciónTaxativa;
	@JsonProperty
	private CondiciónComparativa condiciónComparativa;
	
	CondiciónTaxocomparativa(
			@JsonProperty("nombre") String nombre, 
			@JsonProperty("indicador") Indicador indicador,
			@JsonProperty("númeroDePeríodos") int númeroDePeríodos,
			@JsonProperty("evaluacion") Evaluación evaluación,
			@JsonProperty("orden") Orden orden,
			@JsonProperty("valorDeReferencia") double valor,
			@JsonProperty("prioridad") Prioridad prioridad) {
		super(nombre, indicador, númeroDePeríodos, evaluación, orden);
		condiciónTaxativa = new CondiciónTaxativa(nombre, indicador, númeroDePeríodos, evaluación, orden, valor);
		condiciónComparativa = new CondiciónComparativa(nombre, indicador, númeroDePeríodos, evaluación, orden, prioridad);
	}
	
	public double obtenerValorDeReferencia() {
		return condiciónTaxativa.obtenerValorDeReferencia();
	}
	
	public Prioridad obtenerPrioridad() {
		return condiciónComparativa.obtenerPrioridad();
	}

	@Override
	public List<Empresa> aplicar(List<Empresa> empresas) {
		return condiciónComparativa.aplicar(condiciónTaxativa.aplicar(empresas));
	}
	
	CondiciónTaxativa obtenerCondiciónTaxativa() {
		return condiciónTaxativa;
	}
	
	CondiciónComparativa obtenerCondiciónComparativa() {
		return condiciónComparativa;
	}
	
	public String getTipo()	{
		return "Taxocomparativa";
	}
}