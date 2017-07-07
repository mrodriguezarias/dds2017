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
		Condición condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E1", "E2");
		assertFalse(condición.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaAplicable() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I1");
		Condición condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E1");
		assertTrue(condición.esAplicable(empresas));
	}
	
	@Test
	public void probarCondiciónTaxativaMenorAValorDeReferencia() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I1");
		constructor.establecerValorDeReferencia(1000);
		constructor.establecerOrden(Orden.MENOR);
		Condición condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E1", "E2");
		empresas = condición.aplicar(empresas);
		assertEquals(1, empresas.size());
	}
	
	@Test
	public void probarCondiciónTaxativaTendenciaCreciente() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I4");
		constructor.establecerNúmeroDePeríodos(5);
		Condición condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E2", "E1", "E3");
		empresas = condición.aplicar(empresas);
		assertEquals(2, empresas.size());
	}
	
	@Test
	public void probarCondiciónTaxativaMedianaMayorAUnValor() throws Exception {
		ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa("I4");
		constructor.establecerNúmeroDePeríodos(5);
		constructor.establecerEvaluación(Evaluación.MEDIANA);
		constructor.establecerValorDeReferencia(120);
		Condición condición = constructor.construir();
		
		List<Empresa> empresas = FuenteDeEmpresaDePrueba.empresas("E3");
		empresas = condición.aplicar(empresas);
		assertEquals(0, empresas.size());
	}
	
	private boolean empresasSonIguales(List<Empresa> empresas1, List<Empresa> empresas2) {
		int tamaño = empresas1.size();
		if(tamaño != empresas2.size()) {
			return false;
		}
		for(int i = 0; i < tamaño; i++) {
			Empresa empresa1 = empresas1.get(i);
			Empresa empresa2 = empresas2.get(i);
			if(!empresa1.obtenerNombre().equals(empresa2.obtenerNombre())) {
				return false;
			}
		}
		return true;
	}
	
	@Test
	public void probarCondiciónComparativaPromedioCreciente() throws Exception {
		ConstructorDeCondiciónComparativa constructor = new ConstructorDeCondiciónComparativa("I5");
		constructor.establecerNúmeroDePeríodos(3);
		Condición condición = constructor.construir();
		
		List<Empresa> empresas  = FuenteDeEmpresaDePrueba.empresas("E1", "E3", "E2", "E4");
		List<Empresa> ordenadas = FuenteDeEmpresaDePrueba.empresas("E3", "E4", "E1", "E2");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondiciónComparativaSumatoriaDecreciente() throws Exception {
		ConstructorDeCondiciónComparativa constructor = new ConstructorDeCondiciónComparativa("I5");
		constructor.establecerNúmeroDePeríodos(3);
		constructor.establecerEvaluación(Evaluación.SUMATORIA);
		constructor.establecerOrden(Orden.MENOR);
		Condición condición = constructor.construir();
		
		List<Empresa> empresas  = FuenteDeEmpresaDePrueba.empresas("E4", "E2", "E1", "E3");
		List<Empresa> ordenadas = FuenteDeEmpresaDePrueba.empresas("E2", "E1", "E4", "E3");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
	
	@Test
	public void probarCondiciónTaxocomparativa() throws Exception {
		ConstructorDeCondiciónTaxocomparativa constructor = new ConstructorDeCondiciónTaxocomparativa("I5");
		constructor.establecerOrden(Orden.MENOR);
		constructor.establecerValorDeReferencia(5000);
		Condición condición = constructor.construir();
		
		List<Empresa> empresas  = FuenteDeEmpresaDePrueba.empresas("E1", "E3", "E4", "E2");
		List<Empresa> ordenadas = FuenteDeEmpresaDePrueba.empresas("E4", "E3");
		
		empresas = condición.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, ordenadas));
	}
}
