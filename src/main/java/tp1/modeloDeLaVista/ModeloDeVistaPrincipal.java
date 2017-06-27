package tp1.modeloDeLaVista;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import tp1.Aplicación;
import tp1.modelo.Empresa;

@Observable
public class ModeloDeVistaPrincipal {
	
	private List<ComponenteDeMedida> medidas;
	private ComponenteDeMedida medidaSeleccionada;
	
	private List<String> nombresDeLasEmpresas;
	private String nombreDeLaEmpresaSeleccionada;
	
	private List<Short> períodos;
	private short períodoSeleccionado;
	
	public ModeloDeVistaPrincipal() {
		List<Empresa> empresas = Aplicación.repositorioDeEmpresas.todos(); 
		
		nombresDeLasEmpresas = empresas.stream().map(c -> c.obtenerNombre())
				.sorted().collect(Collectors.toList());
		nombreDeLaEmpresaSeleccionada = nombresDeLasEmpresas.get(0);
		
		períodos = empresas.stream().map(c -> c.obtenerCuentas())
			.flatMap(l -> l.stream()).map(m -> m.obtenerPeríodo()).distinct()
			.sorted(Collections.reverseOrder()).collect(Collectors.toList());
		períodoSeleccionado = períodos.get(0);
		
		actualizarMedidas();
	}
	
	public List<ComponenteDeMedida> getMedidas() {
		return medidas;
	}
	
	public ComponenteDeMedida getMedidaSeleccionada() {
		return medidaSeleccionada;
	}
	
	public void setMedidaSeleccionada(ComponenteDeMedida medidaSeleccionada) {
		this.medidaSeleccionada = medidaSeleccionada;
	}
	
	public List<String> getNombresDeLasEmpresas() {
		return nombresDeLasEmpresas;
	}
	
	public String getNombreDeLaEmpresaSeleccionada() {
		return nombreDeLaEmpresaSeleccionada;
	}
	
	public void setNombreDeLaEmpresaSeleccionada(String nombreDeLaEmpresaSeleccionada) {
		this.nombreDeLaEmpresaSeleccionada = nombreDeLaEmpresaSeleccionada;
		actualizarMedidas();
	}
	
	public List<Short> getPeríodos() {
		return períodos;
	}
	
	public short getPeríodoSeleccionado() {
		return períodoSeleccionado;
	}
	
	public void setPeríodoSeleccionado(short períodoSeleccionado) {
		this.períodoSeleccionado = períodoSeleccionado;
		actualizarMedidas();
	}
	
	public void actualizarMedidas() {
		this.medidas = null;
		Empresa empresaSeleccionada = Aplicación.repositorioDeEmpresas.encontrar(nombreDeLaEmpresaSeleccionada);
		
		List<ComponenteDeMedida> medidas = empresaSeleccionada.obtenerCuentas().stream()
				.filter(cuenta -> cuenta.obtenerPeríodo() == períodoSeleccionado)
				.map(cuenta -> new ComponenteDeMedida(cuenta, empresaSeleccionada, períodoSeleccionado))
				.collect(Collectors.toList());
		
		List<ComponenteDeMedida> indicadores = Aplicación.repositorioDeIndicadores.todos().stream()
				.filter(indicador -> indicador.obtenerFórmula().esVálidaParaContexto(empresaSeleccionada, períodoSeleccionado))
				.map(indicador -> new ComponenteDeMedida(indicador, empresaSeleccionada, períodoSeleccionado))
				.collect(Collectors.toList());
		medidas.addAll(indicadores);
		
		this.medidas = medidas;
	}
	
	@Dependencies("medidaSeleccionada")
	public boolean getVerMedidaHabilitado() {
		return medidaSeleccionada != null;
	}
}
