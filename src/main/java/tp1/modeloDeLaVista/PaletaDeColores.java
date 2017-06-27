package tp1.modeloDeLaVista;

import java.awt.Color;

public enum PaletaDeColores {
	
	ROJO(0.0f, 1.0f, 0.8f),
	VERDE(0.3f, 1.0f, 0.5f),
	AZUL(0.6f, 1.0f, 0.9f);
	
	private Color valor;
	
	private PaletaDeColores(float h, float s, float b) {
		this.valor = Color.getHSBColor(h, s, b);
	}
	
	public Color obtenerValor() {
		return valor;
	}
}
