package tp1;

import static org.junit.Assert.*;

import org.junit.Test;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.indicador.ConstructorDelIndicador;
import tp1.modelo.indicador.ConstructorDelIndicador.ExcepciónDeFórmulaInválida;

public class IndicatorTest {

	@Test
	public void testValidIndicator() throws Exception {
		String name = "VI";
		String description = "The golden ratio";
		String formula = "1.61803398875";

		ConstructorDelIndicador indicatorBuilder = new ConstructorDelIndicador();
		indicatorBuilder.establecerNombre(name);
		indicatorBuilder.establecerDescripción(description);
		indicatorBuilder.establecerFórmula(formula);

		Indicador indicator = indicatorBuilder.construir();

		assertEquals(name, indicator.obtenerNombre());
		assertEquals(description, indicator.obtenerDescripción());
		assertEquals(formula, indicator.obtenerFórmula().comoCadenaDeCaracteres());
	}
	
	@Test(expected = ExcepciónDeFórmulaInválida.class)
	public void testInvalidIndicator() throws Exception {
		String name = "II";
		String formula = "1:61803398875";

		ConstructorDelIndicador indicatorBuilder = new ConstructorDelIndicador();
		indicatorBuilder.establecerNombre(name);
		indicatorBuilder.establecerFórmula(formula);
		indicatorBuilder.construir();
	}
	
	@Test
	public void testIndicatorGetValue() throws Exception {
		String name = "name";
		String description = "";
		String formula = "1+2";
		
		Empresa company = new Empresa("CompanyName");

		ConstructorDelIndicador indicatorBuilder = new ConstructorDelIndicador();
		indicatorBuilder.establecerNombre(name);
		indicatorBuilder.establecerDescripción(description);
		indicatorBuilder.establecerFórmula(formula);

		Indicador indicator = indicatorBuilder.construir();
		
		double result = indicator.obtenerValor(company, (short) 1929);

		assertEquals(3.0, result, 0.0);
	}
	
	
}