package tp1;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import tp1.modelo.Empresa;
import tp1.modelo.metodología.CondiciónTaxativa;
import tp1.modelo.metodología.ConstructorDeCondiciónTaxativa;
import tp1.modelo.metodología.Evaluación;
import tp1.modelo.metodología.Orden;
import tp1.modelo.repositorios.RepositorioDeEmpresas;
import tp1.modelo.repositorios.RepositorioDeIndicadores;
import tp1.modelo.repositorios.Repositorios;

public class PruebaDeCondiciones {
	
	@Before
	public void inicializar() {
		Repositorios.establecerRepositorioDeEmpresas(new RepositorioDeEmpresas(new FuenteDeEmpresaDePrueba()));
		Repositorios.establecerRepositorioDeIndicadores(new RepositorioDeIndicadores(new FuenteDeIndicadorDePrueba()));
		FuenteDeIndicadorDePrueba.crearIndicadoresDePrueba();
	}
	
	@Test
	public void probarCondiciónTaxativaNoAplicable() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I1");
		CondiciónTaxativa condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E1", "E2");
		assertFalse(condición.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaAplicable() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I1");
		CondiciónTaxativa condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E1");
		assertTrue(condición.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaMenorAValorDeReferencia() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I1");
		constructor.establecerValorDeReferencia(1000);
		constructor.establecerOrden(Orden.MENOR);
		CondiciónTaxativa condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E1");
		empresas = condición.aplicar(empresas);
		assertEquals(0, empresas.size());
	}
	
	@Test
	public void probarCondiciónTaxativaTendenciaCreciente() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I4");
		constructor.establecerNúmeroDePeríodos(5);
		CondiciónTaxativa condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E3");
		empresas = condición.aplicar(empresas);
		assertEquals(0, empresas.size());
	}
	
	@Test
	public void probarCondiciónTaxativaMedianaMayorAUnValor() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I4");
		constructor.establecerNúmeroDePeríodos(5);
		constructor.establecerEvaluación(Evaluación.MEDIANA);
		constructor.establecerValorDeReferencia(120);
		CondiciónTaxativa condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E3");
		empresas = condición.aplicar(empresas);
		assertEquals(0, empresas.size());
	}
}
