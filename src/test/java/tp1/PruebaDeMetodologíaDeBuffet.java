package tp1;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import tp1.fuentes.FuenteDeEmpresaDePrueba;
import tp1.fuentes.FuenteDeIndicadorDePrueba;
import tp1.modelo.Empresa;
import tp1.modelo.metodología.CondiciónComparativa;
import tp1.modelo.metodología.CondiciónTaxativa;
import tp1.modelo.metodología.CondiciónTaxocomparativa;
import tp1.modelo.metodología.ConstructorDeCondiciónComparativa;
import tp1.modelo.metodología.ConstructorDeCondiciónTaxativa;
import tp1.modelo.metodología.ConstructorDeCondiciónTaxocomparativa;
import tp1.modelo.metodología.ConstructorDeMetodología;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.metodología.Orden;
import tp1.modelo.repositorios.RepositorioDeEmpresas;
import tp1.modelo.repositorios.RepositorioDeIndicadores;
import tp1.modelo.repositorios.Repositorios;
import static tp1.fuentes.FuenteDeEmpresaDePrueba.*;

public class PruebaDeMetodologíaDeBuffet {

	private class FuenteDeEmpresaDePruebaParaLaMetodologíaDeBuffet extends FuenteDeEmpresaDePrueba {
		@Override
		protected void crearEmpresas() {
			crearEmpresa("E1", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2014, 200);
				crearCuenta("UN", empresa, 2015, 300);
				crearCuenta("UN", empresa, 2016, 400);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2014, 1300);
				crearCuenta("PN", empresa, 2015, 1500);
				crearCuenta("PN", empresa, 2016, 1700);
				// Pasivo total
				crearCuenta("PT", empresa, 2014, 400);
				crearCuenta("PT", empresa, 2015, 500);
				crearCuenta("PT", empresa, 2016, 500);
				// Ventas
				crearCuenta("V", empresa, 2014, 900);
				crearCuenta("V", empresa, 2015, 1100);
				crearCuenta("V", empresa, 2016, 1200);
				// Longevidad
				crearCuenta("L", empresa, 2016, 15);
			});

			crearEmpresa("E2", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2015, 400);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2015, 1300);
				// Pasivo total
				crearCuenta("PT", empresa, 2015, 700);
				// Ventas
				crearCuenta("V", empresa, 2015, 900);
				// Longevidad
				crearCuenta("L", empresa, 2015, 12);
			});

			crearEmpresa("E3", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2013, 500);
				crearCuenta("UN", empresa, 2014, 400);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2013, 1900);
				crearCuenta("PN", empresa, 2014, 1700);
				// Pasivo total
				crearCuenta("PT", empresa, 2013, 800);
				crearCuenta("PT", empresa, 2014, 600);
				// Ventas
				crearCuenta("V", empresa, 2013, 500);
				crearCuenta("V", empresa, 2014, 300);
				// Longevidad
				crearCuenta("L", empresa, 2014, 7);
			});

			crearEmpresa("E4", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2015, 700);
				crearCuenta("UN", empresa, 2016, 200);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2015, 1500);
				crearCuenta("PN", empresa, 2016, 1100);
				// Pasivo total
				crearCuenta("PT", empresa, 2015, 1000);
				crearCuenta("PT", empresa, 2016, 1000);
				// Ventas
				crearCuenta("V", empresa, 2015, 800);
				crearCuenta("V", empresa, 2016, 900);
				// Longevidad
				crearCuenta("L", empresa, 2016, 14);
			});

			crearEmpresa("E5", empresa -> {
				// Utilidad neta
				crearCuenta("UN", empresa, 2016, 900);
				// Patrimonio neto
				crearCuenta("PN", empresa, 2016, 1300);
				// Pasivo total
				crearCuenta("PT", empresa, 2016, 400);
				// Ventas
				crearCuenta("V", empresa, 2016, 600);
				// Longevidad
				crearCuenta("L", empresa, 2016, 11);
			});
		}
	}

	@Before
	public void inicializar() {
		Repositorios.establecerRepositorioDeEmpresas(new RepositorioDeEmpresas(new FuenteDeEmpresaDePruebaParaLaMetodologíaDeBuffet()));
		Repositorios.establecerRepositorioDeIndicadores(new RepositorioDeIndicadores(new FuenteDeIndicadorDePrueba()));
		Repositorios.obtenerRepositorioDeIndicadores().crearIndicadores();
		FuenteDeIndicadorDePrueba.crearIndicador("ROE", "UN / PN");
		FuenteDeIndicadorDePrueba.crearIndicador("NivelDeDeuda", "PT / PN");
		FuenteDeIndicadorDePrueba.crearIndicador("Márgenes", "UN / V");
		FuenteDeIndicadorDePrueba.crearIndicador("Longevidad", "L");
	}

	/**
	 * Maximizar ROE: una empresa es mejor que otra si durante los últimos
	 * 10 años, su ROE fue consistentemente mejor que el de la otra.
	 */
	private CondiciónComparativa crearCondiciónParaMaximizarROE() {
		return new ConstructorDeCondiciónComparativa("ROE").conNúmeroDePeríodos(10).construir();
	}

	/**
	 * Minimizar el nivel de deuda: una empresa es mejor que otra si su
	 * proporción de deuda es menor.
	 */
	private CondiciónComparativa crearCondiciónParaMinimizarElNivelDeDeuda() {
		return new ConstructorDeCondiciónComparativa("NivelDeDeuda").conOrden(Orden.MENOR).construir();
	}

	/**
	 * Márgenes consistentemente crecientes: vale la pena invertir en una
	 * empresa en la que su margen durante los últimos 10 años fue siempre
	 * creciente.
	 */
	private CondiciónTaxativa crearCondiciónDeMárgenesCrecientes() {
		return new ConstructorDeCondiciónTaxativa("Márgenes").conNúmeroDePeríodos(10).construir();
	}

	/**
	 * Longevidad: sólo vale la pena invertir en empresas con más de 10 años.
	 * Además, una empresa es mejor que otra si es más antigua.
	 */
	private CondiciónTaxocomparativa crearCondiciónDeLongevidad() {
		return new ConstructorDeCondiciónTaxocomparativa("Longevidad").conValorDeReferencia(10).construir();
	}

	@Test
	public void probarMetodología() throws Exception {
		ConstructorDeMetodología constructor = new ConstructorDeMetodología("Buffet");
		constructor.agregarCondición(crearCondiciónParaMaximizarROE());
		constructor.agregarCondición(crearCondiciónParaMinimizarElNivelDeDeuda());
		constructor.agregarCondición(crearCondiciónDeMárgenesCrecientes());
		constructor.agregarCondición(crearCondiciónDeLongevidad());
		Metodología metodología = constructor.construir();

		List<Empresa> empresas = Repositorios.obtenerRepositorioDeEmpresas().todos();
		List<Empresa> resultadoEsperado = empresas("E5", "E1", "E2");

		empresas = metodología.aplicar(empresas);
		assertTrue(empresasSonIguales(empresas, resultadoEsperado));
	}
}