package tp1.model.indicator;
import java.util.HashSet;
import java.util.Set;
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
	
	private int pos = -1, ch;
	private String str;
	
	public Formula parse(String formula) throws ParseFailedException {
		Expression expression = getExpressionFromFormula(formula);
		Set<String> metrics = getMetricsUsedByFormula(formula);
		return new Formula(formula, expression, metrics);
	}
	
	Expression getExpressionFromFormula(String formula) throws ParseFailedException {
		str = addTimesOperatorWhenImplicit(checkAndBracketVariables(formula));
		nextChar();
		Expression expression = parseExpression();
		if (pos < str.length()) {
			throw new ParseFailedException(
					String.format("carácter '%c' inesperado", ch));
		}
		return expression;
	}
	
	private boolean isMetric(String variable) {
		return App.companyRepository.all().stream().anyMatch(c -> c.hasMetric(variable));
	}
	
	private boolean isIndicator(String variable) {
		return App.indicatorRepository.all().stream().anyMatch(i -> i.getName().equals(variable));
	}
	
	private Set<String> getMetricsUsedByFormula(String formula) {
		Set<String> metrics = new HashSet<>();
		Matcher matcher = Pattern.compile(VAR_REGEX).matcher(formula);

		while(matcher.find()) {
			String variable = matcher.group();
			if(isMetric(variable)) {
				metrics.add(variable);
			} else if(isIndicator(variable)) {
				metrics.addAll(getMetricsUsedByFormula(App.indicatorRepository.find(variable).getFormula().asString()));
			}
		}
		
		return metrics;
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

		formula = formula.replaceAll(VAR_REGEX, "($0)");
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

	private void nextChar() {
		ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	}

	private boolean eat(int charToEat) {
		while (ch == ' ') nextChar();
		if (ch == charToEat) {
			nextChar();
			return true;
		}
		return false;
	}

	// Grammar:
	// expression = term | expression `+` term | expression `-` term
	// term = factor | term `*` factor | term `/` factor
	// factor = `+` factor | `-` factor | `(` expression `)`
	//        | number | functionName factor | factor `^` factor

	private Expression parseExpression() throws ParseFailedException {
		Expression x = parseTerm();
		while(true) {
			if(eat('+')) {
				Expression a = x, b = parseTerm();
				x = ((Company company, short period) -> a.eval(company, period) + b.eval(company, period));
			} else if(eat('-')) {
				Expression a = x, b = parseTerm();
				x = ((Company company, short period) -> a.eval(company, period) - b.eval(company, period));
			} else {
				return x;
			}
		}
	}

	private Expression parseTerm() throws ParseFailedException {
		Expression x = parseFactor();
		while(true) {
			if(eat('*')) {
				Expression a = x, b = parseFactor();
				x = ((Company company, short period) -> a.eval(company, period) * b.eval(company, period));
			} else if(eat('/')) {
				Expression a = x, b = parseFactor();
				x = ((Company company, short period) -> a.eval(company, period) / b.eval(company, period));
			} else {
				return x;
			}
		}
	}

	private Expression parseFactor() throws ParseFailedException {
		if (eat('+')) return parseFactor(); // unary plus
		if (eat('-')) {
			Expression a = parseFactor();
			return ((Company company, short period) -> -1 * a.eval(company, period)); // unary minus
		}

		Expression x;
		int startPos = this.pos;
		if (eat('(')) { // parentheses
			x = parseExpression();
			eat(')');
		} else if(Character.isDigit(ch) || ch == '.') {
			while(Character.isDigit(ch) || ch == '.') nextChar();
			double number;
			try {
				number = Double.parseDouble(str.substring(startPos, this.pos));				
			} catch(NumberFormatException e) {
				throw new ParseFailedException("formato numérico inválido");
			 }
			x = ((Company company, short period) -> number);

		} else if(Character.isLetter(ch)) {
			nextChar();
			while(Character.isLetter(ch) || Character.isDigit(ch)) nextChar();
			String name = str.substring(startPos, this.pos);

			x = ((Company company, short period) -> {
				Metric metric = company.getMetric(name, period);
				if(metric != null) {
					return metric.getValue();
				}

				Indicator indicator = App.indicatorRepository.find(name);				
				return indicator.getValue(company, period);
			});
		} else {
			String err = ch == -1 ? "final inesperado" : String.format("carácter '%c' inesperado", ch);
			throw new ParseFailedException(err);
		}

		if (eat('^')) { // exponentiation
			Expression a = x;
			Expression b = parseFactor();
			x = ((Company company, short period) -> Math.pow(a.eval(company, period), b.eval(company, period)));
		}

		return x;
	}
}
