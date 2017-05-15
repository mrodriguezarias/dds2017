package tp1.model;
import java.util.ArrayList;
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

	private List<Metric> metrics;
	private List<Indicator> indicators;

	public Parser() {
		this.metrics = new ArrayList<>();
		this.indicators = new ArrayList<>();
	}

	public Parser(List<Metric> metrics, List<Indicator> indicators) {
		this.metrics = metrics;
		this.indicators = indicators;
	}

	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}

	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}
	
	public double parse(final String string) throws ParseFailedException {
		return eval(replaceVariables(string));
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
		Pattern pattern = Pattern.compile("[A-Z]+");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer(str.length());
		int start = 0;
		int end = 0;
		while(matcher.find()) {
			end = matcher.start();
			sb.append(str.substring(start, end));
			start = matcher.end();
			String var = matcher.group();
			end += var.length();
			sb.append("(");
			Optional<Metric> metric = getMetric(var);
			if(metric.isPresent()) {
				sb.append(metric.get().getValue());
			} else {
				Optional<Indicator> indicator = getIndicator(var);
				if(!indicator.isPresent()) {
					throw new ParseFailedException(String.format("Variable '%s' no reconocida", var));
				}
				try {
					sb.append(replaceVariables(indicator.get().getFormula()));
				} catch(StackOverflowError e) {
					throw new ParseFailedException("Límite de recursión alcanzado");					
				}
			}
			sb.append(")");
		}
		sb.append(str.substring(end));
		return sb.toString();
	}

	// Source: http://stackoverflow.com/a/26227947
	private double eval(final String text) throws ParseFailedException {
		return new Object() {
			int pos = -1, ch;
			String str;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ') nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() throws ParseFailedException {
				str = text.replaceAll("([0-9)])\\s+([0-9(])", "$1 * $2");
				str = str.replaceAll("([0-9)])\\(", "$1 * (");
				str = str.replaceAll("\\)([0-9(])", ") * $1");
				nextChar();
				double x = parseExpression();
				if (pos < str.length()) {
					throw new ParseFailedException(
							String.format("Carácter '%c' inesperado", ch));
				}
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			//        | number | functionName factor | factor `^` factor

			double parseExpression() throws ParseFailedException {
				double x = parseTerm();
				for (;;) {
					if      (eat('+')) x += parseTerm(); // addition
					else if (eat('-')) x -= parseTerm(); // subtraction
					else return x;
				}
			}

			double parseTerm() throws ParseFailedException {
				double x = parseFactor();
				for (;;) {
					if      (eat('*')) x *= parseFactor(); // multiplication
					else if (eat('/')) x /= parseFactor(); // division
					else return x;
				}
			}

			double parseFactor() throws ParseFailedException {
				if (eat('+')) return parseFactor(); // unary plus
				if (eat('-')) return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z') nextChar();
					String func = str.substring(startPos, this.pos);
					x = parseFactor();
					if (func.equals("sqrt")) x = Math.sqrt(x);
					else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
					else throw new RuntimeException("Unknown function: " + func);
				} else {
					throw new ParseFailedException(
							String.format("Carácter '%c' inesperado", ch));
				}

				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}

}
