package tp1.modelo.metodología;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Prioridad {
	MÍNIMA(1), BAJA(2), MEDIA(3), ALTA(4), MÁXIMA(5);
	
	private int valor;
	
	private Prioridad(int valor) {
		this.valor = valor;
	}
	public int obtenerValor() {
		return valor;
	}
}
