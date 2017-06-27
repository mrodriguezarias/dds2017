package tp1;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import tp1.modelo.repositorios.RepositorioDeLaEmpresa;
import tp1.modelo.repositorios.RepositorioDelIndicador;
import tp1.modelo.repositorios.fuentes.FuenteJsonDeLaEmpresa;
import tp1.modelo.repositorios.fuentes.FuenteJsonDelIndicador;
import tp1.vista.VistaPrincipal;

public class Aplicación extends Application {
	
	static public RepositorioDeLaEmpresa repositorioDeEmpresas;
	static public RepositorioDelIndicador repositorioDeIndicadores;
	
	@Override
	protected Window<?> createMainWindow() {
		return new VistaPrincipal(this);
	}
	
	public static void main(String[] args) {
		
		final String ARCHIVO_DE_CUENTAS = "cuentas.json";
		final String ARCHIVO_DE_INDICADORES = "indicadores.json";
		
		FuenteJsonDelIndicador fuenteDeIndicador = new FuenteJsonDelIndicador(ARCHIVO_DE_INDICADORES);
		FuenteJsonDeLaEmpresa fuenteDeEmpresa = new FuenteJsonDeLaEmpresa(ARCHIVO_DE_CUENTAS);
		
		repositorioDeEmpresas = new RepositorioDeLaEmpresa(fuenteDeEmpresa);
		repositorioDeIndicadores = new RepositorioDelIndicador(fuenteDeIndicador);
		
		new Aplicación().start();
	}
}
