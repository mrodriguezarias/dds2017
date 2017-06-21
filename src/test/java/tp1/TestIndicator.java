package tp1;

import static org.junit.Assert.*;

import org.junit.Test;

import tp1.model.Company;
import tp1.model.Indicator;
import tp1.model.IndicatorBuilder;
import tp1.model.IndicatorBuilder.InvalidFormulaException;

public class TestIndicator {

	@Test
	public void testValidIndicator() throws InvalidFormulaException {
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
	public void testInvalidIndicator() throws InvalidFormulaException {
		String name = "II";
		String formula = "1:61803398875";

		IndicatorBuilder indicatorBuilder = new IndicatorBuilder();
		indicatorBuilder.setName(name);
		indicatorBuilder.setFormula(formula);
		indicatorBuilder.build();
	}
	
	@Test
	public void testIndicatorGetValue() throws InvalidFormulaException {
		String name = "name";
		String description = "";
		String formula = "1+2";
		
		Company company = new Company("CompanyName");

		IndicatorBuilder indicatorBuilder = new IndicatorBuilder();
		indicatorBuilder.setName(name);
		indicatorBuilder.setDescription(description);
		indicatorBuilder.setFormula(formula);

		Indicator indicator = indicatorBuilder.build();
		
		double result = indicator.getValue(company, (short) 1929);

		assertEquals(3.0, result, 0.0);
	}
	
	
}