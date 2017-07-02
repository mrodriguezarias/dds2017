package tp1.model.indicator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tp1.App;
import tp1.model.Company;
import tp1.model.Metric;

public class Parser {

	@SuppressWarnings("serial")
	public class ParseFailedException extends Exception {
		public ParseFailedException(String message) {
			super(message);
		}
	}

	private final String VAR_REGEX = "\\p{Alpha}\\w*";
	
	public Calculable obtenerCalculable(String formula) throws ParseFailedException {
		/*-- Este metodo evaula una formula sin errores, es decir que se debe chequear antes por errores --*/
		int i;
		Calculable calculable = null;
		String listaOperadores = "+-*/"; // <-- agregar aca los oepradores a parsear en orden de precedencia
		
		formula = addTimesOperatorWhenImplicit(checkAndBracketVariables(formula));
		formula = formula.replace(" ","" ); //eliminar espacios
		
		if(formula.equals(""))return new CalculableNumerico(0.0); //para operadores unarios El: -"7" => "0-7"
		
		for (i=0; i<listaOperadores.length(); i++ ){
			calculable = evaluarTerminosSegunOperador(formula, listaOperadores.charAt(i));
			if (calculable != null) return calculable;			
		}
		
		calculable = evaluarFormulaEnterParentesis(formula); // A este punto solo llega una entre ()
		if (calculable != null) return calculable;
		
		return crearCalculableSimple(formula);

	}
	
	private Calculable crearCalculableSimple(String formula) {
		
		if(esCuenta(formula)) return new CalculableCuenta(formula);
		if(esIndicador(formula)) return new CalcuableIndicador(formula); 
		return new CalculableNumerico(Double.parseDouble(formula));
	}

	private boolean esCuenta(String variable) {
		return App.companyRepository.all().stream().anyMatch(c -> c.hasMetric(variable));
	}
	
	private boolean esIndicador(String variable) {
		return App.indicatorRepository.existeIndicador(variable);
	}

	public Calculable evaluarTerminosSegunOperador(String formula, char operador) throws ParseFailedException{
		int i;
		String termino = "";
		String subtermino = ""; //un termino dentro de un parentesis
		
		for (i=0; i< formula.length(); i++){ //recorro el string para separar segun operador
			char caracter = formula.charAt(i);
			if (caracter == '('){
				subtermino = saltearParentesis(formula.substring(i));
				termino += subtermino;
				i+= subtermino.length() -1;
				continue;
			}
			if(caracter == operador){
				switch (operador) {
				case ' ': break; //ignora espacios
				case '+': 
				return new CalculableOperador(obtenerCalculable(termino),
											  obtenerCalculable(formula.substring(i+1)), '+');
				case '-': 
				return new CalculableOperador(obtenerCalculable(termino),
											  obtenerCalculable(formula.substring(i+1)), '-');
				case '/':
				return new CalculableOperador(obtenerCalculable(termino),
											  obtenerCalculable(formula.substring(i+1)), '/');
				case '*':
				return new CalculableOperador(obtenerCalculable(termino),
											  obtenerCalculable(formula.substring(i+1)), '*');
				}
			}
			termino += caracter;
	      }
		return null; //si no encontro el operador en la formula
	}
	
	public String saltearParentesis(String subformula){
		//-- Dado un string retorna la cadena hasta donde cierra ')'
		//-- Ej: (7*3-(11-1)/2)+44 --> (7*3-(11-1)/2)  
		int i =1;
		String termino = "(";
		String subtermino;
		for (i=1; i< subformula.length(); i++){
			char caracter = subformula.charAt(i);
			switch (caracter) {
			case '(':
				subtermino = saltearParentesis(subformula.substring(i));
				termino += subtermino;
				i+= subtermino.length()-1;
				break;
			case ')':
				termino += caracter;
//				System.out.println(termino);
				return termino;
			default: 
				termino += caracter; 
				break;
			}
		}
		return "";
	}
	
	public Calculable evaluarFormulaEnterParentesis (String formula) throws ParseFailedException{
		// entrada: "(8+3*4)" evaluo: "8+3*4"
		if(formula.charAt(0) == '('){
			return obtenerCalculable(formula.substring(1, formula.length()-1));  
		}
		return null; // la formula no estaba entre parentesis
	}
	
	
	
	/*Lo que estaba antes*/
	private boolean isMetric(String variable) {
		return App.companyRepository.all().stream().anyMatch(c -> c.hasMetric(variable));
	}
	
	private boolean isIndicator(String variable) {
		return App.indicatorRepository.all().stream().anyMatch(i -> i.getName().equals(variable));
	}
	

	/**
	 * Verifica que existan las variables y las encierra entre paréntesis.
	 */
	private String checkAndBracketVariables(String formula) throws ParseFailedException {
		Matcher matcher = Pattern.compile(VAR_REGEX).matcher(formula);

		while(matcher.find()) {
			String variable = matcher.group();
			if(!isMetric(variable) && !isIndicator(variable)) {
				throw new ParseFailedException(String.format("variable '%s' inválida", variable));
			}
		}

		return formula;
	}

	/** 
	 * Hace explícito el operador de multiplicación (*)
	 * en las expresiones donde estaba implícito.
	 * Por ejemplo: "3 (A + B)" lo convierte en "3 * (A + B)"
	 */
	private String addTimesOperatorWhenImplicit(String formula) {
		formula = formula.replaceAll("([0-9)])\\s+([0-9(])", "$1 * $2");
		formula = formula.replaceAll("([0-9)])\\(", "$1 * (");
		formula = formula.replaceAll("\\)([0-9(])", ") * $1");
		formula = formula.replaceAll(",", ".");
		return formula;
	}
}
