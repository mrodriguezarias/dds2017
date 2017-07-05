package tp1;

import static org.junit.Assert.*;

import org.junit.Test;

import tp1.model.Empresa;
import tp1.model.indicator.Indicator;
import tp1.model.indicator.IndicatorBuilder;
import tp1.model.indicator.IndicatorBuilder.InvalidFormulaException;

public class IndicatorTest {

	@Test
	public void testValidIndicator() throws Exception {
		String name = "VI";
		String description = "The golden ratio";
		String formula = "1.61803398875";

		IndicatorBuilder indicatorBuilder = new IndicatorBuilder();
		indicatorBuilder.setName(name);
		indicatorBuilder.setDescription(description);
		indicatorBuilder.setFormula(formula);

		Indicator indicator = indicatorBuilder.build();

		assertEquals(name, indicator.getName());
		assertEquals(description, indicator.getDescription());
		assertEquals(formula, indicator.getFormula());
	}
	
	@Test(expected = InvalidFormulaException.class)
	public void testInvalidIndicator() throws Exception {
		String name = "II";
		String formula = "1:61803398875";

		IndicatorBuilder indicatorBuilder = new IndicatorBuilder();
		indicatorBuilder.setName(name);
		indicatorBuilder.setFormula(formula);
		indicatorBuilder.build();
	}
	
	@Test
	public void testIndicatorGetValue() throws Exception {
		String name = "name";
		String description = "";
		String formula = "1+2";
		
		Empresa company = new Empresa("CompanyName");

		IndicatorBuilder indicatorBuilder = new IndicatorBuilder();
		indicatorBuilder.setName(name);
		indicatorBuilder.setDescription(description);
		indicatorBuilder.setFormula(formula);

		Indicator indicator = indicatorBuilder.build();
		
		double result = indicator.getValue(company, (short) 1929);

		assertEquals(3.0, result, 0.0);
	}
	
	
}