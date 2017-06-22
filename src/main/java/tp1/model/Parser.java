package tp1.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tp1.App;

public class Parser {

	@SuppressWarnings("serial")
	public class ParseFailedException extends Exception {
		public ParseFailedException(String message) {
			super(message);
		}
	}

	private int pos = -1, ch;
	private String str;

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

	public Expression parse(String formula) throws ParseFailedException {
		str = addTimesOperatorWhenImplicit(checkAndBracketVariables(formula));
		nextChar();
		Expression x = parseExpression();
		if (pos < str.length()) {
			throw new ParseFailedException(
					String.format("carácter '%c' inesperado", ch));
		}
		return x;
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

	/**
	 * Verifica que existan las variables y las encierra entre paréntesis.
	 */
	private String checkAndBracketVariables(String formula) throws ParseFailedException {
		String regex = "\\p{Alpha}\\w*";
		Matcher matcher = Pattern.compile(regex).matcher(formula);

		while(matcher.find()) {
			String variable = matcher.group();
			boolean isValid = App.companyRepository.all().stream().anyMatch(c -> c.hasMetric(variable));
			if(!isValid) {
				isValid = App.indicatorRepository.all().stream().anyMatch(i -> i.getName().equals(variable));
			}
			if(!isValid) {
				throw new ParseFailedException(String.format("variable '%s' inválida", variable));
			}
		}

		return formula.replaceAll(regex, "($0)");
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
			double number = Double.parseDouble(str.substring(startPos, this.pos));
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
