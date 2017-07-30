package tp1.modelo.metodología;

import com.fasterxml.jackson.annotation.JsonFormat;

//@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Orden {
	
	MAYOR, MENOR;
	
	public boolean comparar(double valor1, double valor2) {
		return this == MAYOR ? valor1 >= valor2 : valor1 <= valor2;
	}
}