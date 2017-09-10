package tp1.modelo.indicador;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.modelo.Empresa;
import tp1.modelo.Medida;
import tp1.modelo.indicador.AnalizadorSintáctico.ParseFailedException;

@Entity(name="Indicadores")
public class Indicador implements Medida,Calculable {

	@Id @GeneratedValue
	private Long id;
	
	@JsonProperty
	private String name;

	@JsonProperty
	private String description;

	@JsonProperty
	private String formula;
	
	@Transient
	private Calculable calculable;

	@SuppressWarnings("serial")
	public class InvalidFormulaException extends Exception {
		private String cause;

		public InvalidFormulaException(String cause) {
			this.cause = cause;
		}

		@Override
		public String getMessage() { 
			return String.format("Fórmula inválida: %s.", cause);
		}
	}
	@SuppressWarnings("serial")
	public class InvalidForContextException extends Exception {}
	
	
	@JsonCreator
	public Indicador(
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("formula") String formula) {
		this.name = name;
		this.description = description;
		this.formula = formula;
		try {
			this.calculable = new AnalizadorSintáctico().obtenerCalculable(formula);
		} catch (ParseFailedException e) {}		
	}

	public String obtenerFórmula() {
		return formula;
	}

	public String getName() {
		return name;		
	}

	public String obtenerDescripción() {
		return description;
	}

	public double obtenerValor(Empresa company, short period) {
		return this.calcular(company, period);
	}

	@Override
	public double calcular(Empresa company, short period) {
		return calculable.calcular(company, period); //fixme reemplazar alguno de los 2 metodos :s
	}

	@JsonIgnore
	@Override
	public Set<String> getCuentas() {
		return calculable.getCuentas(); //fixme: quedarse con uno
	}
	
	public boolean esVálidoParaContexto(Empresa company, short period) {
		return company.obtenerCuentas(period).stream()
				.map(m -> m.getName()).collect(Collectors.toSet())
				.containsAll(calculable.getCuentas());
	}
	
}
