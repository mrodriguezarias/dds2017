package tp1;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.model.Indicator;
import tp1.model.IndicatorBuilder;

public class TestIndicator{

	//private static List<Indicator> indicators;

	@BeforeClass
	public static void setUp() {
		//indicators = App.indicatorRepository.all();
	}

	@Test
	public void testValues() {

		String name = "nombre";
		String description = "descripcion del indicador";
		String formula = "1";

		IndicatorBuilder indicatorBuilder = new IndicatorBuilder();
		indicatorBuilder.setName(name);
		indicatorBuilder.setDescription(description);
		indicatorBuilder.setFormula(formula);

		Indicator indicator = indicatorBuilder.build();

		assertEquals(name, indicator.getName());
		assertEquals(description, indicator.getDescription());
		assertEquals(formula, indicator.getFormula());

	}
}