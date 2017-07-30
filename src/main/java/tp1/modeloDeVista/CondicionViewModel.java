package tp1.modeloDeVista;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import tp1.modelo.metodología.*;
import tp1.modelo.repositorios.Repositorios;


@Observable
public class CondicionViewModel {
	private String nombre;
	private Integer periodo;
	private List<String> nombreIndicadores;
	private String nombreIndicador;
	private List<Orden> ordenes;
	private Orden orden;
	private Double valorDeReferencia;
	private Evaluación evaluacion;
	private List<Evaluación> evaluaciones;
	private boolean taxativa;
	private boolean comparativa;
	private List<Prioridad> prioridades;
	private Prioridad prioridad;
	private ConstructorDeMetodología builderMetodologia;
	private boolean estaEditando;
	private String nombreAnterior;
	private String error;

	public CondicionViewModel(ConstructorDeMetodología builder, Condición condicion) {
		this.builderMetodologia = builder;
		inicializarListas();
		if(condicion != null) {
			this.nombreAnterior = condicion.getNombre();
			setEditMode(condicion);
		} else {
			setCreateMode();
		}
	}
	
	private void inicializarListas() {
		this.ordenes = Arrays.asList(Orden.values());
		this.evaluaciones = Arrays.asList(Evaluación.values());
		this.prioridades = Arrays.asList(Prioridad.values());
		this.nombreIndicadores = Repositorios.obtenerRepositorioDeIndicadores().todos().stream().map(i -> i.getName())
				.sorted().collect(Collectors.toList());
	}

	private void setEditMode(Condición condicion) {
		this.estaEditando = true;
		this.nombre = condicion.getNombre();
		this.periodo = condicion.obtenerNúmeroDePeríodos();
		this.orden = condicion.obtenerOrden();
		this.evaluacion = condicion.obtenerEvaluación();
		this.nombreIndicador = condicion.obtenerIndicador().getName();

		if(condicion.getTipo() == "Taxocomparativa")	{
			this.taxativa = true;
			this.comparativa = true;
			CondiciónTaxocomparativa unaCondicion = (CondiciónTaxocomparativa) condicion;
			this.valorDeReferencia = unaCondicion.obtenerValorDeReferencia();
			this.prioridad = unaCondicion.obtenerPrioridad();
		}
		else if(condicion.getTipo() == "Taxativa")	{
			this.taxativa = true;
			CondiciónTaxativa unaCondicion = (CondiciónTaxativa) condicion;
			this.valorDeReferencia = unaCondicion.obtenerValorDeReferencia();
		}
		else if(condicion.getTipo() == "Comparativa")	{
			this.comparativa = true;
			CondiciónComparativa unaCondicion = (CondiciónComparativa) condicion;
			this.prioridad = unaCondicion.obtenerPrioridad();
		}
	}

	private void setCreateMode()	{
		this.nombre = "";
		this.nombreIndicador = "";
		this.error = "";
		this.estaEditando = false;
	}

	public String guardarCambios()	{
		if(!estaTodoCompleto())	return "";
		
		if(nombreAnterior != null) {
			builderMetodologia.eliminarCondicion(nombreAnterior);
		}
		
		if(this.taxativa && this.comparativa)	{
			ConstructorDeCondiciónTaxocomparativa constructor = new ConstructorDeCondiciónTaxocomparativa(this.nombre);
			setBuilderTaxocomparativa(constructor);
			builderMetodologia.agregarCondición(constructor.construir());
		}
		else if(this.taxativa) {
			ConstructorDeCondiciónTaxativa constructor = new ConstructorDeCondiciónTaxativa(this.nombre)
					.conValorDeReferencia(this.valorDeReferencia);
			setBuilderTaxativa(constructor);
			builderMetodologia.agregarCondición(constructor.construir());
		}
		else {
			ConstructorDeCondiciónComparativa constructor = new ConstructorDeCondiciónComparativa(this.nombre);
			setBuilderComparativa(constructor);
			builderMetodologia.agregarCondición(constructor.construir());
		}

		return this.nombre + (this.estaEditando? " modificada" : " creada");
	}

	private void setBuilder(ConstructorDeCondición<?> builder)	{
		builder.conNúmeroDePeríodos(this.periodo)
		.conIndicador(this.nombreIndicador)
		.conOrden(this.orden)
		.conEvaluación(this.evaluacion);
	}

	private void setBuilderTaxativa(ConstructorDeCondiciónTaxativa builder) {
		builder.conValorDeReferencia(this.valorDeReferencia);
		setBuilder(builder);
	}

	private void setBuilderComparativa(ConstructorDeCondiciónComparativa builder) {
		builder.conPrioridad(this.prioridad);
		setBuilder(builder);
	}

	private void setBuilderTaxocomparativa(ConstructorDeCondiciónTaxocomparativa builder) {
		builder.conPrioridad(this.prioridad);
		builder.conValorDeReferencia(this.valorDeReferencia);
		setBuilder(builder);
	}

	private boolean estaTodoCompleto() {
		this.error = "";
		if(nombre.isEmpty())
			this.error = "Especifique un nombre de condición.";
		else if(!taxativa && !comparativa)
			this.error = "Seleccione un tipo de condición.";
		else if(periodo == null || periodo <= 0)
			this.error = "Especifique un período mayor a cero.";
		else if(orden == null)
			this.error = "Seleccione un tipo de orden.";
		else if(taxativa && valorDeReferencia == null)
			this.error = "Especifique un valor límite.";
		else if(nombreIndicador.isEmpty())
			this.error = "Seleccione un indicador.";
		else if(evaluacion == null)
			this.error = "Seleccione un tipo de evaluación.";
		else if(comparativa && prioridad == null)
			this.error = "Seleccione un tipo de prioridad";
		return this.error.isEmpty();
	}

	public List<Orden> getOrdenes()	{
		return this.ordenes;
	}

	public void setOrdenes(List<Orden> ordenes)	{
		this.ordenes = ordenes;
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

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public Double getValorDeReferencia() {
		return valorDeReferencia;
	}

	public void setValorDeReferencia(Double valorDeReferencia) {
		this.valorDeReferencia = valorDeReferencia;
	}

	public Evaluación getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluación evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
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

	public boolean getEstaEditando() {
		return estaEditando;
	}

	public List<Evaluación> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<Evaluación> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public List<Prioridad> getPrioridades() {
		return prioridades;
	}

	public void setPrioridades(List<Prioridad> prioridades) {
		this.prioridades = prioridades;
	}

	public Integer getPeriodo() {
		return periodo;
	}


	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}


	public Orden getOrden() {
		return orden;
	}


	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public boolean estaEditando() {
		return this.estaEditando;
	}

	public String getError()	{
		return this.error;
	}
}
