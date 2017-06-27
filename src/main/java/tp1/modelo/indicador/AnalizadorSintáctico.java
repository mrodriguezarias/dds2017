package tp1.modelo.indicador;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tp1.Aplicación;
import tp1.modelo.Empresa;
import tp1.modelo.Cuenta;

public class AnalizadorSintáctico {

	@SuppressWarnings("serial")
	public class ExcepciónDeAnálisisFallido extends Exception {
		public ExcepciónDeAnálisisFallido(String mensaje) {
			super(mensaje);
		}
	}

	private final String REGEX_VAR = "\\p{Alpha}\\w*";
	
	private int posición = -1, carácter;
	private String cadena;
	
	public Fórmula analizar(String fórmula) throws ExcepciónDeAnálisisFallido {
		Expresión expresión = obtenerExpresiónAPartirDeFórmula(fórmula);
		Set<String> cuentas = obtenerCuentasUsadasPorFórmula(fórmula);
		return new Fórmula(fórmula, expresión, cuentas);
	}
	
	Expresión obtenerExpresiónAPartirDeFórmula(String fórmula) throws ExcepciónDeAnálisisFallido {
		cadena = agregarOperadorDeMultiplicaciónCuandoEstéImplícito(verificarYEncerrarVariablesEntreParéntesis(fórmula));
		siguienteCarácter();
		Expresión expression = analizarExpresión();
		if (posición < cadena.length()) {
			throw new ExcepciónDeAnálisisFallido(
					String.format("carácter '%c' inesperado", carácter));
		}
		return expression;
	}
	
	private boolean esCuenta(String variable) {
		return Aplicación.repositorioDeEmpresas.todos().stream().anyMatch(c -> c.tieneCuenta(variable));
	}
	
	private boolean esIndicador(String variable) {
		return Aplicación.repositorioDeIndicadores.todos().stream().anyMatch(i -> i.obtenerNombre().equals(variable));
	}
	
	private Set<String> obtenerCuentasUsadasPorFórmula(String formula) {
		Set<String> cuentas = new HashSet<>();
		Matcher comparador = Pattern.compile(REGEX_VAR).matcher(formula);

		while(comparador.find()) {
			String variable = comparador.group();
			if(esCuenta(variable)) {
				cuentas.add(variable);
			} else if(esIndicador(variable)) {
				cuentas.addAll(obtenerCuentasUsadasPorFórmula(Aplicación.repositorioDeIndicadores.encontrar(variable).obtenerFórmula().comoCadenaDeCaracteres()));
			}
		}
		
		return cuentas;
	}

	/**
	 * Verifica que existan las variables y las encierra entre paréntesis.
	 */
	private String verificarYEncerrarVariablesEntreParéntesis(String fórmula) throws ExcepciónDeAnálisisFallido {
		Matcher comparador = Pattern.compile(REGEX_VAR).matcher(fórmula);

		while(comparador.find()) {
			String variable = comparador.group();
			if(!esCuenta(variable) && !esIndicador(variable)) {
				throw new ExcepciónDeAnálisisFallido(String.format("variable '%s' inválida", variable));
			}
		}

		fórmula = fórmula.replaceAll(REGEX_VAR, "($0)");
		return fórmula;
	}

	/** 
	 * Hace explícito el operador de multiplicación (*)
	 * en las expresiones donde estaba implícito.
	 * Por ejemplo: "3 (A + B)" lo convierte en "3 * (A + B)"
	 */
	private String agregarOperadorDeMultiplicaciónCuandoEstéImplícito(String fórmula) {
		fórmula = fórmula.replaceAll("([0-9)])\\s+([0-9(])", "$1 * $2");
		fórmula = fórmula.replaceAll("([0-9)])\\(", "$1 * (");
		fórmula = fórmula.replaceAll("\\)([0-9(])", ") * $1");
		fórmula = fórmula.replaceAll(",", ".");
		return fórmula;
	}

	private void siguienteCarácter() {
		carácter = (++posición < cadena.length()) ? cadena.charAt(posición) : -1;
	}

	private boolean comer(int carácterAComer) {
		while (carácter == ' ') siguienteCarácter();
		if (carácter == carácterAComer) {
			siguienteCarácter();
			return true;
		}
		return false;
	}

	// Grammar:
	// expression = term | expression `+` term | expression `-` term
	// term = factor | term `*` factor | term `/` factor
	// factor = `+` factor | `-` factor | `(` expression `)`
	//        | number | functionName factor | factor `^` factor

	private Expresión analizarExpresión() throws ExcepciónDeAnálisisFallido {
		Expresión x = analizarTérmino();
		while(true) {
			if(comer('+')) {
				Expresión a = x, b = analizarTérmino();
				x = ((Empresa empresa, short período) -> a.eval(empresa, período) + b.eval(empresa, período));
			} else if(comer('-')) {
				Expresión a = x, b = analizarTérmino();
				x = ((Empresa empresa, short período) -> a.eval(empresa, período) - b.eval(empresa, período));
			} else {
				return x;
			}
		}
	}

	private Expresión analizarTérmino() throws ExcepciónDeAnálisisFallido {
		Expresión x = analizarFactor();
		while(true) {
			if(comer('*')) {
				Expresión a = x, b = analizarFactor();
				x = ((Empresa empresa, short período) -> a.eval(empresa, período) * b.eval(empresa, período));
			} else if(comer('/')) {
				Expresión a = x, b = analizarFactor();
				x = ((Empresa empresa, short período) -> a.eval(empresa, período) / b.eval(empresa, período));
			} else {
				return x;
			}
		}
	}

	private Expresión analizarFactor() throws ExcepciónDeAnálisisFallido {
		if (comer('+')) return analizarFactor(); // unary plus
		if (comer('-')) {
			Expresión a = analizarFactor();
			return ((Empresa empresa, short período) -> -1 * a.eval(empresa, período)); // unary minus
		}

		Expresión x;
		int posiciónInicial = this.posición;
		if (comer('(')) { // paréntesis
			x = analizarExpresión();
			comer(')');
		} else if(Character.isDigit(carácter) || carácter == '.') {
			while(Character.isDigit(carácter) || carácter == '.') siguienteCarácter();
			double número;
			try {
				número = Double.parseDouble(cadena.substring(posiciónInicial, this.posición));				
			} catch(NumberFormatException e) {
				throw new ExcepciónDeAnálisisFallido("formato numérico inválido");
			 }
			x = ((Empresa empresa, short período) -> número);

		} else if(Character.isLetter(carácter)) {
			siguienteCarácter();
			while(Character.isLetter(carácter) || Character.isDigit(carácter)) siguienteCarácter();
			String nombre = cadena.substring(posiciónInicial, this.posición);

			x = ((Empresa empresa, short período) -> {
				Cuenta metric = empresa.obtenerCuenta(nombre, período);
				if(metric != null) {
					return metric.obtenerValor();
				}

				Indicador indicator = Aplicación.repositorioDeIndicadores.encontrar(nombre);				
				return indicator.obtenerValor(empresa, período);
			});
		} else {
			String err = carácter == -1 ? "final inesperado" : String.format("carácter '%c' inesperado", carácter);
			throw new ExcepciónDeAnálisisFallido(err);
		}

		if (comer('^')) { // exponentiation
			Expresión a = x;
			Expresión b = analizarFactor();
			x = ((Empresa empresa, short período) -> Math.pow(a.eval(empresa, período), b.eval(empresa, período)));
		}

		return x;
	}
}
