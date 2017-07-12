package tp1.fuentes;

import java.util.ArrayList;
import java.util.List;

import tp1.modelo.indicador.ConstructorDeIndicador;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.indicador.ConstructorDeIndicador.ExcepciónDeFórmulaInválida;
import tp1.modelo.repositorios.Repositorios;
import tp1.modelo.repositorios.fuentes.FuenteDeIndicador;

public class FuenteDeIndicadorDePrueba implements FuenteDeIndicador {
	
	@Override
	public List<Indicador> cargar() {
		return new ArrayList<>();
	}
	
	@Override
	public List<String> obtenerNombres() {
		return null;
	}

	@Override
	public void guardar(List<Indicador> indicadores) {
		
	}
	
	public static void crearIndicador(String nombre, String fórmula) {
		ConstructorDeIndicador constructor = new ConstructorDeIndicador();
		constructor.establecerNombre(nombre);
		constructor.establecerFórmula(fórmula);
		
		try {
			Indicador indicador = constructor.construir();
			Repositorios.obtenerRepositorioDeIndicadores().agregar(indicador);
		} catch (ExcepciónDeFórmulaInválida e) {
			e.printStackTrace();
		}
	}

	public static void crearIndicadoresDePrueba() {
		crearIndicador("I1", "13 * 724 + C2");
		crearIndicador("I2", "I1 - 3.14");
		crearIndicador("I3", "I1 / I2");
		crearIndicador("I4", "(235 / 3.4) C1");
		crearIndicador("I5", "2 C3");
	}
}