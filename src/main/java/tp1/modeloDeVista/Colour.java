package tp1.modeloDeVista;

import java.awt.Color;

public enum Colour {
	
	RED(0.0f, 1.0f, 0.8f),
	GREEN(0.3f, 1.0f, 0.5f),
	BLUE(0.6f, 1.0f, 0.9f);
	
	private Color value;
	
	private Colour(float h, float s, float b) {
		this.value = Color.getHSBColor(h, s, b);
	}
	
	public Color getValue() {
		return value;
	}
}
