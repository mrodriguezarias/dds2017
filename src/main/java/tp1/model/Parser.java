package tp1.model;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private Optional<Metric> getMetric(String name) {
		return metrics.stream()
				.filter(metric -> metric.getName().equals(name)).findFirst();
	}

	private Optional<Indicator> getIndicator(String name) {
		return indicators.stream()
				.filter(indicator -> indicator.getName().equals(name)).findFirst();
	}

	private String replaceVariables(final String str) throws ParseFailedException {
		//		Pattern pattern = Pattern.compile("[A-Z]+");
		//		Matcher matcher = pattern.matcher(str);
		//		StringBuffer sb = new StringBuffer(str.length());
		//		int start = 0;
		//		int end = 0;
		//		while(matcher.find()) {
		//			end = matcher.start();
		//			sb.append(str.substring(start, end));
		//			start = matcher.end();
		//			String var = matcher.group();
		//			end += var.length();
		//			sb.append("(");
		//			Optional<Metric> metric = getMetric(var);
		//			if(metric.isPresent()) {
		//				sb.append(String.format("%f", metric.get().getValue()));
		//			} else {
		//				Optional<Indicator> indicator = getIndicator(var);
		//				if(!indicator.isPresent()) {
		//					throw new ParseFailedException(String.format("variable '%s' no reconocida", var));
		//				}
		//				if(indicator.get().getName().equals(indicatorName)) {
		//					throw new ParseFailedException("definición recursiva");
		//				}
		// 				sb.append(replaceVariables(indicator.get().getFormula()));
		//			}
		//			sb.append(")");
		//		}
		//		sb.append(str.substring(end));
		//		return sb.toString();
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
		str = formula.replaceAll("([0-9)])\\s+([0-9(])", "$1 * $2");
		str = str.replaceAll("([0-9)])\\(", "$1 * (");
		str = str.replaceAll("\\)([0-9(])", ") * $1");
		nextChar();
		Expression x = parseExpression();
		if (pos < str.length()) {
			throw new ParseFailedException(
					String.format("carácter '%c' inesperado", ch));
		}
		return x;
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
		if (eat('-')) return ((Company company, short period) -> -1 * parseFactor().eval(company, period)); // unary minus

		Expression x;
		int startPos = this.pos;
		if (eat('(')) { // parentheses
		x = parseExpression();
		eat(')');
		
		if(Character.isDigit(ch)) {
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

				Indicator indicator = IndicatorRepository.getInstance().getIndicator(name);
				if(indicator != null) {
					return indicator.getValue(company, period);
				}

				throw new RuntimeException("Variable no reconocida");
			});
		} 
		return x;
	}
	//				double x;
	//				int startPos = this.pos;
	//				if (eat('(')) { // parentheses
	//					x = parseExpression();
	//					eat(')');
	//				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	//					while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	//					x = Double.parseDouble(str.substring(startPos, this.pos));
	//				} else if (ch >= 'a' && ch <= 'z') { // functions
	//					while (ch >= 'a' && ch <= 'z') nextChar();
	//					String func = str.substring(startPos, this.pos);
	//					x = parseFactor();
	//					if (func.equals("sqrt")) x = Math.sqrt(x);
	//					else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
	//					else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
	//					else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
	//					else throw new ParseFailedException(
	//							String.format("función '%s' desconocida", func));
	//				} else {
	//					String err = ch == -1 ? "final inesperado"
	//							: String.format("carácter '%c' inesperado", ch);
	//					throw new ParseFailedException(err);
	//				}
	//
	//				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
	//
	//				return x;

}

}
