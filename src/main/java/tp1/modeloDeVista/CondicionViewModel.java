package tp1.modeloDeVista;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import tp1.modelo.metodología.*;
import tp1.modelo.repositorios.Repositorios;


@Observable
public class CondicionViewModel {
	private String nombre;
	private int periodo;
	private List<String> nombreIndicadores;
	private String nombreIndicadorElegido;
	private List<String> ordenes;
	private String ordenElegido;
	private double valorDeReferencia;
	private String evaluacion;
	private List<String> evaluaciones;
	private boolean taxativa;
	private boolean comparativa;
	private List<String> prioridades;
	private String prioridadElegida;
	private ConstructorDeMetodología builderMetodologia;
	
	public CondicionViewModel(ConstructorDeMetodología builder, Condición condicion)	{
		this.builderMetodologia = builder;
		if(condicion != null) {
			this.nombre = condicion.getNombre();
			this.periodo = condicion.obtenerNúmeroDePeríodos();
			this.ordenElegido = condicion.obtenerOrden().toString();
			this.evaluacion = condicion.obtenerEvaluación().toString();
			if(condicion.getTipo() == "Taxocomparativa")	{
				
			}
			else if(condicion.getTipo() == "Taxativa")	{
				
			}
			else	{
				
			}
		}
		this.ordenes = new ArrayList<String>();
		this.evaluaciones = new ArrayList<String>();
		this.prioridades = new ArrayList<String>();
		for(Orden orden : EnumSet.allOf(Orden.class))	{
			this.ordenes.add(orden.toString());
		}
		for(Evaluación evaluacion : EnumSet.allOf(Evaluación.class))	{
			this.evaluaciones.add(evaluacion.toString());
		}
		for(Prioridad prioridad : EnumSet.allOf(Prioridad.class))	{
			this.prioridades.add(prioridad.toString());
		}
		this.nombreIndicadores = Repositorios.obtenerRepositorioDeIndicadores().todos().stream().map(i -> i.getName())
				.sorted().collect(Collectors.toList());
	}
	
	
	public String guardarCambios()	{
		if(estaTodoCompleto())	{
			if(this.taxativa && this.comparativa)	{
				ConstructorDeCondiciónTaxocomparativa constructor = new ConstructorDeCondiciónTaxocomparativa(this.nombre);
				setBuilderTaxocomparativa(constructor);
				builderMetodologia.agregarCondición(constructor.construir());
			}
			else if(this.taxativa && this.valorDeReferencia > 0) {
				ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa(this.nombre).conValorDeReferencia(this.valorDeReferencia);
				setBuilderTaxativa(constructor);
				builderMetodologia.agregarCondición(constructor.construir());
			}
			else {
				ConstructorDeCondiciónComparativa constructor = new ConstructorDeCondiciónComparativa(this.nombre);
				setBuilderComparativa(constructor);
				builderMetodologia.agregarCondición(constructor.construir());
			}
			return this.nombre;
			
		}
		else return "";
	}
	
	private void setBuilder(ConstructorDeCondición builder)	{
		builder	.conNúmeroDePeríodos(this.periodo)
				.conIndicador(this.nombreIndicadorElegido)
				.conOrden(Orden.valueOf(this.ordenElegido))
				.conEvaluación(Evaluación.valueOf(this.evaluacion));
	}
	
	private void setBuilderTaxativa(ConstructorDeCondiciónTaxativa builder) {
		builder	.conValorDeReferencia(this.valorDeReferencia);
		setBuilder(builder);
	}
	
	private void setBuilderComparativa(ConstructorDeCondiciónComparativa builder) {
		builder	.conPrioridad(Prioridad.valueOf(this.prioridadElegida));
		setBuilder(builder);
	}
	
	private void setBuilderTaxocomparativa(ConstructorDeCondiciónTaxocomparativa builder) {
		builder	.conPrioridad(Prioridad.valueOf(this.prioridadElegida));
		builder	.conValorDeReferencia(this.valorDeReferencia);
		setBuilder(builder);
	}
	
	private boolean estaTodoCompleto() {
		return !(this.nombre.isEmpty()
				|| (!this.taxativa && !this.comparativa)
				|| this.prioridadElegida.isEmpty()
				|| this.evaluacion.isEmpty()
				|| this.ordenElegido.isEmpty()
				|| this.periodo <= 0);
	}
	
	public List<String> getOrdenes()	{
		return this.ordenes;
	}
	
	public void setOrdenes(List<String> unosOrdenes)	{
		this.ordenes = unosOrdenes;
	}
	
	public List<String> getNombreIndicadores()	{
		return this.nombreIndicadores;
	}
	
	public void setNombreIndicadores(List<String> nombresIndicadores) {
		this.nombreIndicadores = nombresIndicadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreIndicadorElegido() {
		return nombreIndicadorElegido;
	}

	public void setNombreIndicadorElegido(String nombreIndicadorElegido) {
		this.nombreIndicadorElegido = nombreIndicadorElegido;
	}

	public double getValorDeReferencia() {
		return valorDeReferencia;
	}

	public void setValorDeReferencia(double valorDeReferencia) {
		this.valorDeReferencia = valorDeReferencia;
	}

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getPrioridadElegida() {
		return prioridadElegida;
	}

	public void setPrioridad(String prioridad) {
		this.prioridadElegida = prioridad;
	}

	public boolean getTaxativa() {
		return taxativa;
	}

	public void setTaxativa(boolean esTaxativa) {
		this.taxativa = esTaxativa;
	}

	public boolean getComparativa() {
		return comparativa;
	}

	public void setComparativa(boolean esComparativa) {
		this.comparativa = esComparativa;
	}

	public List<String> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<String> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public List<String> getPrioridades() {
		return prioridades;
	}

	public void setPrioridades(List<String> prioridades) {
		this.prioridades = prioridades;
	}

	public void setPrioridadElegida(String prioridadElegida) {
		this.prioridadElegida = prioridadElegida;
	}


	public int getPeriodo() {
		return periodo;
	}


	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}


	public String getOrdenElegido() {
		return ordenElegido;
	}


	public void setOrdenElegido(String ordenElegido) {
		this.ordenElegido = ordenElegido;
	}
}
