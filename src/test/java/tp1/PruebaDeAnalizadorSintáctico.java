package tp1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.indicador.ConstructorDeIndicador;
import tp1.modelo.indicador.AnalizadorSintáctico;
import tp1.modelo.indicador.ConstructorDeIndicador.ExcepciónDeFórmulaInválida;
import tp1.modelo.indicador.Expresión;
import tp1.modelo.repositorios.RepositorioDeEmpresas;
import tp1.modelo.repositorios.RepositorioDeIndicadores;
import tp1.modelo.repositorios.Repositorios;

public class PruebaDeAnalizadorSintáctico {
	
	private static final double DELTA = 1e-10;
	private AnalizadorSintáctico analizador = new AnalizadorSintáctico();
	private Empresa empresa;
	
	private void crearIndicador(String nombre, String fórmula) throws ExcepciónDeFórmulaInválida {
		ConstructorDeIndicador constructor = new ConstructorDeIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerFórmula(fórmula);
		
		Indicador indicador = constructor.construir();
		Repositorios.obtenerRepositorioDeIndicadores().agregar(indicador);
	}
	
	private void crearIndicadores() throws ExcepciónDeFórmulaInválida {
		crearIndicador("I1", "13 * 724 + C2");
		crearIndicador("I2", "I1 - 3.14");
		crearIndicador("I3", "I1 / I2");
	}
	
	@Before
	public void inicializar() throws Exception {
		Repositorios.establecerRepositorioDeEmpresas(new RepositorioDeEmpresas(new FuenteDeEmpresaDePrueba()));
		Repositorios.establecerRepositorioDeIndicadores(new RepositorioDeIndicadores(new FuenteDeIndicadorDePrueba()));
		FuenteDeIndicadorDePrueba.crearIndicadoresDePrueba();
		
		empresa = Repositorios.obtenerRepositorioDeEmpresas().encontrar("E1");
		crearIndicadores();
	}
	
	@Test
	public void probarFórmulaConAritméticaBásica() throws Exception {
		double resultado = analizador.analizar("75 + 3,1 (0,2 - 12) / 4").comoExpresión().eval(null, (short) 0);
		assertEquals(65.855, resultado, DELTA);
	}
	
	@Test
	public void probarFórmulaConCuentas() throws Exception {
		double resultado = analizador.analizar("2 C2 / (C3 + 7.3)").comoExpresión().eval(empresa, (short) 2010);
		assertEquals(3.0656032147, resultado, DELTA);
	}
	
	@Test
	public void probarFórmulaConIndicadores() throws Exception {
		Expresión expresión = analizador.analizar("5 I3 - 3 I1").comoExpresión();
		double resultado = expresión.eval(empresa, (short) 2010);
		assertEquals(-10824426.9999956487, resultado, DELTA);
	}
	
	@Test
	public void probarFórmulaConCuentasEIndicadores() throws Exception {
		double resultado = analizador.analizar("C1 I2 / (20 C2)").comoExpresión().eval(empresa, (short) 2010);
		assertEquals(0.7018301452, resultado, DELTA);
	}
	
	@Test(timeout = 500)
	public void probarEvaluaciónDeFórmula() throws Exception {
		Expresión expresión = analizador.analizar("7 C1").comoExpresión();
		for(int i = 0; i < 1000000; i++) {
			short período = (short) (2009 + i % 2);
			double resultado = expresión.eval(empresa, período);
			assertEquals(7 * empresa.obtenerCuenta("C1", período).obtenerValor(), resultado, DELTA);
		}
	}

}
