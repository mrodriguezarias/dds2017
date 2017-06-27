package tp1.modeloDeLaVista;

import java.awt.Color;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.modelo.indicador.Indicador;

@Observable
public class ModeloDeVistaDeMedida {
	
	private ComponenteDeMedida medida;

	public ModeloDeVistaDeMedida(ComponenteDeMedida medida) {
		this.medida = medida;
	}
	
	public String título() {
		return String.format("%s %s de $%s en %s", medida.getTipo(),
				medida.getNombre(), medida.getNombreDeLaEmpresa(), medida.getPeríodo()); 
	}
	
	private boolean esIndicador() {
		return medida.getTipo().equals("Indicador");
	}
	
	@Dependencies("medida")
	public String getEncabezado() {
		if(esIndicador()) {
			Indicador indicador = ((Indicador) medida.getMedida());
			return indicador.obtenerFórmula().comoCadenaDeCaracteres();
		}
		return String.format("%s(%s, %s)",
				medida.getNombre(), medida.getNombreDeLaEmpresa(), medida.getPeríodo());
	}

	@Dependencies("medida")
	public String getValor() {
		return medida.getValorCompleto();
	}

	@Dependencies("medida")
	public String getDescripción() {
		return medida.getDescripción();
	}
	
	@Dependencies("medida")
	public Color getColor() {
		if(esIndicador()) return PaletaDeColores.AZUL.obtenerValor();
		return medida.getValorDoble() < 0 ? PaletaDeColores.ROJO.obtenerValor() : PaletaDeColores.VERDE.obtenerValor(); 
	}
	
}
