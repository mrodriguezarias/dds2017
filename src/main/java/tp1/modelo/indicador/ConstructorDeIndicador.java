package tp1.modelo.indicador;

import tp1.modelo.indicador.AnalizadorSintáctico.ExcepciónDeAnálisisFallido;

public class ConstructorDeIndicador {
	@SuppressWarnings("serial")
	public class ExcepciónDeFórmulaInválida extends Exception {
		public ExcepciónDeFórmulaInválida(String mensaje) {
			super(mensaje);
		}
	}
	
	String nombre;
	String descripción;
	String fórmulaComoCadena;
	
	public void establecerNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void establecerDescripción(String descripción) {
		this.descripción = descripción;
	}
	
	public void establecerFórmula(String fórmula) {
		this.fórmulaComoCadena = fórmula;
	}


	public Indicador construir() throws ExcepciónDeFórmulaInválida {
		AnalizadorSintáctico analizador = new AnalizadorSintáctico();
		Fórmula fórmula = null;
		
		try {
			fórmula = analizador.analizar(fórmulaComoCadena);
		} catch (ExcepciónDeAnálisisFallido e) {
			throw new ExcepciónDeFórmulaInválida(e.getMessage());
		}
		
		Indicador indicador = new Indicador(nombre, descripción, fórmula);
		return indicador;
	}
}
