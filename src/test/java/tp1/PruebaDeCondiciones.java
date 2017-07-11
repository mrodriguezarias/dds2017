package tp1;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import tp1.modelo.Empresa;
import tp1.modelo.metodología.Condición;
import tp1.modelo.metodología.ConstructorDeCondiciónComparativa;
import tp1.modelo.metodología.ConstructorDeCondiciónTaxativa;
import tp1.modelo.metodología.ConstructorDeCondiciónTaxocomparativa;
import tp1.modelo.metodología.Evaluación;
import tp1.modelo.metodología.Orden;
import tp1.modelo.repositorios.RepositorioDeEmpresas;
import tp1.modelo.repositorios.RepositorioDeIndicadores;
import tp1.modelo.repositorios.Repositorios;
import static tp1.FuenteDeEmpresaDePrueba.*;

public class PruebaDeCondiciones {
	
	@Before
	public void inicializar() {
		Repositorios.establecerRepositorioDeEmpresas(new RepositorioDeEmpresas(new FuenteDeEmpresaDePrueba()));
		Repositorios.establecerRepositorioDeIndicadores(new RepositorioDeIndicadores(new FuenteDeIndicadorDePrueba()));
		FuenteDeIndicadorDePrueba.crearIndicadoresDePrueba();
	}
	
	@Test
	public void probarCondiciónTaxativaNoAplicable() throws Exception {
		Condición condición = new ConstructorDeCondiciónTaxativa("I1").construir();
		List<Empresa> empresas = empresas("E1", "E2");
		assertFalse(condición.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaAplicable() throws Exception {
		Condición condición = new ConstructorDeCondiciónTaxativa("I1").construir();
		List<Empresa> empresas = empresas("E1");
		assertTrue(condición.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaMenorAValorDeReferencia() throws Exception {
		Condición condición = new ConstructorDeCondiciónTaxativa("I1")
				.conValorDeReferencia(1000).conOrden(Orden.MENOR).construir();
		
		List<Empresa> empresas = empresas("E1", "E2");
		List<Empresa> filtradas = empresas("E2");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondiciónTaxativaTendenciaCreciente() throws Exception {
		Condición condición = new ConstructorDeCondiciónTaxativa("I4").conNúmeroDePeríodos(5).construir();
		
		List<Empresa> empresas = empresas("E2", "E1", "E3");
		List<Empresa> filtradas = empresas("E2", "E1");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondiciónTaxativaMedianaMayorAUnValor() throws Exception {
		Condición condición = new ConstructorDeCondiciónTaxativa("I4")
				.conNúmeroDePeríodos(5).conEvaluación(Evaluación.MEDIANA).conValorDeReferencia(120).construir();
		
		List<Empresa> empresas = empresas("E3");
		List<Empresa> filtradas = empresas();
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, filtradas));
	}
	
	@Test
	public void probarCondiciónComparativaPromedioCreciente() throws Exception {
		Condición condición = new ConstructorDeCondiciónComparativa("I5")
				.conNúmeroDePeríodos(3).construir();
		
		List<Empresa> empresas  = empresas("E1", "E3", "E2", "E4");
		List<Empresa> ordenadas = empresas("E3", "E4", "E1", "E2");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondiciónComparativaSumatoriaDecreciente() throws Exception {
		Condición condición = new ConstructorDeCondiciónComparativa("I5")
				.conNúmeroDePeríodos(3).conEvaluación(Evaluación.SUMATORIA).conOrden(Orden.MENOR).construir();
		
		List<Empresa> empresas  = empresas("E4", "E2", "E1", "E3");
		List<Empresa> ordenadas = empresas("E2", "E1", "E4", "E3");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondiciónTaxocomparativa() throws Exception {
		Condición condición = new ConstructorDeCondiciónTaxocomparativa("I5")
				.conOrden(Orden.MENOR).conValorDeReferencia(5000).construir();
		
		List<Empresa> empresas  = empresas("E1", "E3", "E4", "E2");
		List<Empresa> ordenadas = empresas("E4", "E3");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
}
