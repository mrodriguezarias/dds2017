package tp1.modelo;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.RepositorioDelIndicador;
import tp1.modelo.metodologia.comparadores.Comparador;
import java.util.Calendar;

public class Condicion {
	Indicador indicador;
	short periodo;
	Comparador comparador;
	
	public Condicion(String nombreIndicador, short periodo) {
		this.periodo = periodo;
		this.indicador = new RepositorioDelIndicador("indicadores.json").encontrar(nombreIndicador); // esto de hardcodear el indicadores.json no me gustó nada... habría que buscarle alguna vuelta
	}
	
	public boolean esMejor(Empresa mejorEmpresa, Empresa peorEmpresa)	{
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=1; i <= this.periodo; i++) {
			short anioEvaluado = (short) (anioActual - i); 	// lo encerré entre paréntesis para poder convertirlo a short
															// si no lo hacía, sólo me convertía el anioActual
			double mejorIndicador = this.indicador.obtenerValor(mejorEmpresa, anioEvaluado);
			double peorIndicador = this.indicador.obtenerValor(peorEmpresa, anioEvaluado);
			if(!comparador.comparar(mejorIndicador, peorIndicador)) return false;
		}
		return true;
	}
	
	public boolean seCumple(Empresa unaEmpresa)	{
		return false; // TODO
	}
}
