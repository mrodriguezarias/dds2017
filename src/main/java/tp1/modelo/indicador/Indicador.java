package tp1.modelo.indicador;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.modelo.Empresa;
import tp1.modelo.Medida;
import tp1.modelo.indicador.Fórmula.ExcepciónIndicadorInválidoParaElContexto;

public class Indicador implements Medida {

	@JsonProperty
	private String nombre;

	@JsonProperty
	private String descripción;

	@JsonProperty
	private Fórmula fórmula;

	@SuppressWarnings("serial")
	public class FórmulaDeExcepciónInválida extends Exception {
		private String causa;

		public FórmulaDeExcepciónInválida(String causa) {
			this.causa = causa;
		}

		@Override
		public String getMessage() { 
			return String.format("Fórmula inválida: %s.", causa);
		}
	}

	@JsonCreator
	public Indicador(
			@JsonProperty("name") String nombre,
			@JsonProperty("description") String descripción,
			@JsonProperty("formula") Fórmula fórmula) {
		this.nombre = nombre;
		this.descripción = descripción;
		this.fórmula = fórmula;
	}

	public Fórmula obtenerFórmula() {
		return fórmula;
	}

	public String obtenerNombre(){
		return nombre;		
	}

	public String obtenerDescripción(){
		return descripción;
	}

	public double obtenerValor(Empresa empresa, short período) {
		try {
			return fórmula.eval(empresa, período);
		} catch (ExcepciónIndicadorInválidoParaElContexto e) {
			/*
			 * Esta excepción puede ignorarse en este contexto porque no debería pasar nunca.
			 */
			return 0;
		}
	}
}
