package tp1.modelo.indicador;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.AnalizadorSintáctico.ExcepciónDeAnálisisFallido;

public class Fórmula {
	
	@SuppressWarnings("serial")
	public class ExcepciónIndicadorInválidoParaElContexto extends Exception {}
	
	@JsonProperty
	private String representación;
	private Expresión expresión;
	
	@JsonProperty
	private Set<String> cuentas;
	
	/*
	 * Constructor privado que usa Jackson para deserializar la fórmula.
	 * Es necesario para que pueda crear el árbol de sintaxis (Expression) a partir de la fórmula,
	 * ya que como Expression es una interfaz funcional, no se puede guardar directamente en el archivo JSON.
	 */
	@JsonCreator
	private Fórmula(
			@JsonProperty("representación") String representación,
			@JsonProperty("cuentas") Set<String> cuentas) {
		this.representación = representación;
		try {
			this.expresión = new AnalizadorSintáctico().obtenerExpresiónAPartirDeFórmula(representación);
		} catch (ExcepciónDeAnálisisFallido e) {}
		this.cuentas = cuentas;
	}
	
	public Fórmula(String representación, Expresión expresión, Set<String> cuentas) {
		this.representación = representación;
		this.expresión = expresión;
		this.cuentas = cuentas;
	}
	
	public String comoCadenaDeCaracteres() {
		return representación;
	}
	
	public Expresión comoExpresión() {
		return expresión;
	}
	
	public boolean esVálidaParaContexto(Empresa empresa, short período) {
		return empresa.obtenerCuentas(período).stream().map(m -> m.obtenerNombre()).collect(Collectors.toSet()).containsAll(cuentas);
	}
	
	public double eval(Empresa empresa, short período) throws ExcepciónIndicadorInválidoParaElContexto {
		if(!esVálidaParaContexto(empresa, período)) {
			throw new ExcepciónIndicadorInválidoParaElContexto();
		}
		return expresión.eval(empresa, período); 
	}
}
