package tp1.model;
import java.util.List;
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

	private String indicatorName;
	private String formula;
	private List<Metric> metrics;
	private List<Indicator> indicators;

	public Parser(Indicator indicator) {
		//		Company company = indicator.getCompany();
		//		Period period = indicator.getPeriod();
		//		this.indicatorName = indicator.getName();
		//		this.formula = indicator.getFormula();
		//		this.metrics = Database.getInstance().getMetrics(company, period);
		//		this.indicators = Database.getInstance().getIndicators(company, period);
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
		addTimesOperator();
		checkMeasure();
		nextChar();
		Expression x = parseExpression();
		if (pos < str.length()) {
			throw new ParseFailedException(
					String.format("carácter '%c' inesperado", ch));
		}
		return x;
	}
	
	private void addTimesOperator() {
		// Hace explícito el operador de multiplicación (*) en las expresiones donde estaba implícito.
		// Por ejemplo: "3 (A + B)" lo convierte en "3 * (A + B)"
		str = formula.replaceAll("([0-9)])\\s+([0-9(])", "$1 * $2");
		str = str.replaceAll("([0-9)])\\(", "$1 * (");
		str = str.replaceAll("\\)([0-9(])", ") * $1");
	}

	private void checkMeasure() throws ParseFailedException {
		Pattern pattern = Pattern.compile("[A-Za-z]+");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()) {
			String variable = matcher.group();
			boolean isValid = App.companyRepository.all().stream().anyMatch(c -> c.hasMetric(variable));
			if(!isValid) {
				isValid = App.indicatorRepository.all().stream().anyMatch(i -> i.getName().equals(variable));
			}
			if(!isValid) {
				throw new ParseFailedException(String.format("variable '%s' es inválida", variable));
			}
		}
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
			Expression factor = parseFactor();
			return ((Company company, short period) -> -1 * factor.eval(company, period)); // unary minus
		}

		Expression x;
		int startPos = this.pos;
		if (eat('(')) { // parentheses
			x = parseExpression();
			eat(')');
		} else if(Character.isDigit(ch)) {
			while(Character.isDigit(ch)) {
				nextChar();
			}
			double number = Double.parseDouble(str.substring(startPos, this.pos));
			x = ((Company company, short period) -> number);

		} else if(Character.isLetter(ch)) {
			while(Character.isLetter(ch)) {
				nextChar();
			}
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
			String err = ch == -1 ? "final inesperado"
					: String.format("carácter '%c' inesperado", ch);
			throw new ParseFailedException(err);
		}

//		if (eat('^')) x = Math.pow(x.eval(company, period), parseFactor()); // exponentiation
		return x;
	}
}
