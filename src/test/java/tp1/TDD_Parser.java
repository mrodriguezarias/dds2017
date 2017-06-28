package tp1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TDD_Parser {

	public double evaluar(String formula){
		/*-- Este metodo evaula una formula sin errores, es decir que se debe chequear antes por errores --*/
		int i;
		String termino = "";
		String subtermino = ""; //un termino dentro de un parentesis
		for (i=0; i< formula.length(); i++){ //recorro el string para separar terminos por + y -
			char caracter = formula.charAt(i);
			switch (caracter) {
			case ' ':
				break; //ignora espacios
			case '+': 
				return evaluar(termino) + evaluar(formula.substring(i+1));
			case '-':
				return evaluar(termino) - evaluar(formula.substring(i+1));
			case '(':
				while((caracter = formula.charAt(++i))!=')'){ subtermino += caracter;}
				termino += String.valueOf(evaluar(subtermino));
				break;
				
			default: 
				termino += caracter; 
				break;
			}
	      }
		
		/*-- A este punto llega una formula solo si no tiene sumas ni restas --*/	
		
		termino = ""; //reseteo el "termino" para recorrer el string nuevamente

		for (i=0; i< formula.length(); i++){
			char caracter = formula.charAt(i);
			switch (caracter) {
			case ' ':
				break;
			case '/': 
				return evaluar(termino) / evaluar(formula.substring(i+1));
			case '*':
				return evaluar(termino) * evaluar(formula.substring(i+1));
			case '(':
				while((caracter = formula.charAt(++i))!=')'){ subtermino += caracter;}
				termino += String.valueOf(evaluar(subtermino));
				break;
			default: 
				termino += caracter; 
				break;
			}
			 
		}
		
		return Double.parseDouble(termino); //para los casos donde solo hay un numero ej: formula = "7"
	}
	
	@Test
	public void sieteEsSiete() {
		String formula =  "7";
		Double resultado = evaluar(formula);
		assertEquals(7, resultado, 0.0);	
	}
	
	@Test
	public void dosYDosSonCuatroTest() {
		String formula =  " 2 + 2 ";
		Double resultado = evaluar(formula);
		assertEquals(4, resultado, 0.0);
		
	}
	
	@Test
	public void ochoMenosUnoSiete() {
		String formula =  "8-1";
		Double resultado = evaluar(formula);
		assertEquals(7, resultado, 0.0);
	}
	
	@Test
	public void tresMasTresMasTres() {
		String formula =  "3+3+3";
		Double resultado = evaluar(formula);
		assertEquals(9, resultado, 0.0);
	}
	
	@Test
	public void cuatroMasDosMenosOnce() {
		String formula =  "4+2-11";
		Double resultado = evaluar(formula);
		assertEquals(-5, resultado, 0.0);
	}
	
	@Test
	public void tresDividoTresIgualUno() {
		String formula =  "3/3";
		Double resultado = evaluar(formula);
		assertEquals(1, resultado, 0.0);
	}
	
	@Test
	public void dosMasTresPorCuatroIgualCatorce() {
		String formula =  "2+3*4";
		Double resultado = evaluar(formula);
		assertEquals(14, resultado, 0.0);
	}
	
	@Test
	public void dosMaTresPorCuatroSobreDosMenosCincoIgualNueve() {
		String formula =  "8+3*4/2-5";
		Double resultado = evaluar(formula);
		assertEquals(9, resultado, 0.0);
	}
	
	/* Test que deberia pasar */
	
	@Test
	public void formulaConParentestisSimple() {
		String formula =  "(8+3*4)/2-5";
		Double resultado = evaluar(formula);
		assertEquals(5, resultado, 0.0);
	}
	
	/*-- Test que deberia pasar --*/
//	@Test
//	public void formulaConParentestisDoble() {
//		String formula =  "((8+3)*4)/2-5";
//		Double resultado = evaluar(formula);
//		assertEquals(17, resultado, 0.0);
//	}
	
	
}
