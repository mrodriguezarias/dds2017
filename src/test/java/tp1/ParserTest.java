package tp1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp1.modelo.Empresa;
import tp1.modelo.Cuenta;
import tp1.modelo.indicador.Expresión;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.indicador.ConstructorDelIndicador;
import tp1.modelo.indicador.AnalizadorSintáctico;
import tp1.modelo.indicador.ConstructorDelIndicador.ExcepciónDeFórmulaInválida;
import tp1.modelo.repositorios.RepositorioDeLaEmpresa;
import tp1.modelo.repositorios.RepositorioDelIndicador;

public class ParserTest {
	
	private static final double DELTA = 1e-10;
	private AnalizadorSintáctico parser = new AnalizadorSintáctico();
	private Empresa company;
	
	private void createMetric(String name, short period, double value) {
		Cuenta metric = new Cuenta(name, "", company.obtenerNombre(), period, value);
		company.agregarCuenta(metric);
	}
	
	private void createIndicator(String name, String formula) throws ExcepciónDeFórmulaInválida {
		ConstructorDelIndicador builder = new ConstructorDelIndicador();
		builder.establecerNombre(name);
		builder.establecerFórmula(formula);
		
		Indicador indicator = builder.construir();
		Aplicación.repositorioDeIndicadores.agregar(indicator);
	}
	
	private void createMetrics() {
		createMetric("M1", (short) 2009, 13);
		createMetric("M1", (short) 2010, 14);
		createMetric("M2", (short) 2010, 3598732);
		createMetric("M3", (short) 2010, 2347806);
	}
	
	private void createIndicators() throws ExcepciónDeFórmulaInválida {
		createIndicator("I1", "13 * 724 + M2");
		createIndicator("I2", "I1 - 3.14");
		createIndicator("I3", "I1 / I2");
	}
	
	@Before
	public void setUp() throws Exception {
		Aplicación.repositorioDeEmpresas = new RepositorioDeLaEmpresa(new TestCompanySource());
		company = Aplicación.repositorioDeEmpresas.todos().get(0); 
		createMetrics();
		Aplicación.repositorioDeIndicadores = new RepositorioDelIndicador(new TestIndicatorSource());
		createIndicators();
	}
	
	@Test
	public void testFormulaWithBasicArithmetic() throws Exception {
		double result = parser.analizar("75 + 3,1 (0,2 - 12) / 4").comoExpresión().eval(null, (short) 0);
		assertEquals(65.855, result, DELTA);
	}
	
	@Test
	public void testFormulaWithMetrics() throws Exception {
		double result = parser.analizar("2 M2 / (M3 + 7.3)").comoExpresión().eval(company, (short) 2010);
		assertEquals(3.0656032147, result, DELTA);
	}
	
	@Test
	public void testFormulaWithIndicators() throws Exception {
		Expresión expression = parser.analizar("5 I3 - 3 I1").comoExpresión();
		double result = expression.eval(company, (short) 2010);
		assertEquals(-10824426.9999956487, result, DELTA);
	}
	
	@Test
	public void testFormulaWithMetricsAndIndicators() throws Exception {
		double result = parser.analizar("M1 I2 / (20 M2)").comoExpresión().eval(company, (short) 2010);
		assertEquals(0.7018301452, result, DELTA);
	}
	
	@Test(timeout = 500)
	public void testFormulaEvaluation() throws Exception {
		Expresión expression = parser.analizar("7 M1").comoExpresión();
		for(int i = 0; i < 1000000; i++) {
			short period = (short) (2009 + i % 2);
			double result = expression.eval(company, period);
			assertEquals(7 * company.obtenerCuenta("M1", period).obtenerValor(), result, DELTA);
		}
	}

}
