package tp1.modeloDeLaVista;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.modelo.Empresa;
import tp1.modelo.indicador.Indicador;
import tp1.modelo.repositorios.Repositorios;

@Observable
public class ModeloDeVistaDeIndicador {

	private String nombreDeLaEmpresa;
	private short período;
	
	private List<String> nombresDeLosIndicadores;
	private String nombreDelIndicador;

	public ModeloDeVistaDeIndicador(String nombreDeLaEmpresa, short período) {
		this.nombreDeLaEmpresa = nombreDeLaEmpresa;
		this.período = período;
		
		Empresa empresa = Repositorios.obtenerRepositorioDeEmpresas().encontrar(nombreDeLaEmpresa);
		this.nombresDeLosIndicadores = Repositorios.obtenerRepositorioDeIndicadores().todos().stream()
				.filter(i -> i.obtenerFórmula().esVálidaParaContexto(empresa, período))
				.map(i -> i.obtenerNombre()).collect(Collectors.toList());
	}
	
	public String title() {
		return String.format("Aplicar indicador a $%s en %s", nombreDeLaEmpresa, período);
	}

	public String getNombreDelIndicador() {
		return nombreDelIndicador;
	}

	public void setNombreDelIndicador(String nombreDelIndicador) {
		this.nombreDelIndicador = nombreDelIndicador;
	}

	public List<String> getNombresDeLosIndicadores() {
		return nombresDeLosIndicadores;
	}
	
	@Dependencies("nombreDelIndicador")
	public String getDescripción() {
		Indicador indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreDelIndicador);
		return indicador.obtenerDescripción();
	}
	
	@Dependencies("nombreDelIndicador")
	public String getValor() {
		Indicador indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreDelIndicador);
		Empresa empresa = Repositorios.obtenerRepositorioDeEmpresas().encontrar(nombreDeLaEmpresa);
		ComponenteDeMedida componente = new ComponenteDeMedida(indicador, empresa, período);
		return componente.getValorCompleto();
	}
	
	@Dependencies("nombreDelIndicador")
	public String getFórmula() {
		Indicador indicador = Repositorios.obtenerRepositorioDeIndicadores().encontrar(nombreDelIndicador);
		return indicador.obtenerFórmula().comoCadenaDeCaracteres();
	}

}
