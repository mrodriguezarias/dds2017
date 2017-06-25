package tp1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp1.model.Company;
import tp1.model.repositories.CompanyRepository;
import tp1.model.repositories.IndicatorRepository;
import tp1.model.Metric;
import tp1.model.indicator.Expression;
import tp1.model.indicator.Indicator;
import tp1.model.indicator.IndicatorBuilder;
import tp1.model.indicator.Parser;
import tp1.model.indicator.IndicatorBuilder.InvalidFormulaException;

public class ParserTest {
	
	private static final double DELTA = 1e-10;
	private Parser parser = new Parser();
	private Company company;
	
	private void createMetric(String name, short period, double value) {
		Metric metric = new Metric(name, "", company.getName(), period, value);
		company.addMetric(metric);
	}
	
	private void createIndicator(String name, String formula) throws InvalidFormulaException {
		IndicatorBuilder builder = new IndicatorBuilder();
		builder.setName(name);
		builder.setFormula(formula);
		
		Indicator indicator = builder.build();
		App.indicatorRepository.add(indicator);
	}
	
	private void createMetrics() {
		createMetric("M1", (short) 2009, 13);
		createMetric("M1", (short) 2010, 14);
		createMetric("M2", (short) 2010, 3598732);
		createMetric("M3", (short) 2010, 2347806);
	}
	
	private void createIndicators() throws InvalidFormulaException {
		createIndicator("I1", "13 * 724 + M2");
		createIndicator("I2", "I1 - 3.14");
		createIndicator("I3", "I1 / I2");
	}
	
	@Before
	public void setUp() throws Exception {
		App.companyRepository = new CompanyRepository(new TestCompanySource());
		company = App.companyRepository.all().get(0); 
		createMetrics();
		App.indicatorRepository = new IndicatorRepository(new TestIndicatorSource());
		createIndicators();
	}
	
	@Test
	public void testFormulaWithBasicArithmetic() throws Exception {
		double result = parser.parse("75 + 3,1 (0,2 - 12) / 4").asExpression().eval(null, (short) 0);
		assertEquals(65.855, result, DELTA);
	}
	
	@Test
	public void testFormulaWithMetrics() throws Exception {
		double result = parser.parse("2 M2 / (M3 + 7.3)").asExpression().eval(company, (short) 2010);
		assertEquals(3.0656032147, result, DELTA);
	}
	
	@Test
	public void testFormulaWithIndicators() throws Exception {
		Expression expression = parser.parse("5 I3 - 3 I1").asExpression();
		double result = expression.eval(company, (short) 2010);
		assertEquals(-10824426.9999956487, result, DELTA);
	}
	
	@Test
	public void testFormulaWithMetricsAndIndicators() throws Exception {
		double result = parser.parse("M1 I2 / (20 M2)").asExpression().eval(company, (short) 2010);
		assertEquals(0.7018301452, result, DELTA);
	}
	
	@Test(timeout = 500)
	public void testFormulaEvaluation() throws Exception {
		Expression expression = parser.parse("7 M1").asExpression();
		for(int i = 0; i < 1000000; i++) {
			short period = (short) (2009 + i % 2);
			double result = expression.eval(company, period);
			assertEquals(7 * company.getMetric("M1", period).getValue(), result, DELTA);
		}
	}

}
