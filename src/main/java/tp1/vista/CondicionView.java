package tp1.vista;

import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.NumericField;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.RadioSelector;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import tp1.modelo.indicador.*;
import tp1.modelo.metodología.*;
import tp1.modeloDeVista.*;

@SuppressWarnings("serial")
public class CondicionView extends SimpleWindow<CondicionViewModel> {
	public CondicionView(WindowOwner padre, ConstructorDeMetodología builder, Condición condicionParaEditar) {
		super(padre, new CondicionViewModel(builder, condicionParaEditar));
		
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel)	{
		setTitle((getModelObject().estaEditando()? "Modificar" : "Crear") + " condición");
		Panel contenedor = new Panel(mainPanel);
		crearTextBox("Nombre:", "nombre", contenedor, false);
		crearCheckBoxTipo(contenedor);
		crearTextBox("Períodos:", "periodo", contenedor, true);
		crearContenedorOrden(contenedor);
		TextBox referencia = crearTextBox("Valor límite:", "valorDeReferencia", contenedor, true);
		referencia.bindEnabledToProperty("taxativa");
		crearSelectorIndicador(contenedor);
		crearRadiosEvaluacion(contenedor);
		crearSelectorPrioridad(contenedor);
	}
	
	private Panel crearFila(String label, int ancho, Panel contenedor) {
		Panel panel = new Panel(contenedor);
		panel.setLayout(new HorizontalLayout());
		new Label(panel).setText(label).setWidth(ancho);
		return panel;
	}
	
	private TextBox crearTextBox(String label, String propiedad, Panel contenedor, boolean numérico) {
		Panel contenedorTextBox = crearFila(label, 100, contenedor);
		TextBox campo = numérico ? new NumericField(contenedorTextBox): new TextBox(contenedorTextBox);
		campo.setWidth(250);
		campo.bindValueToProperty(propiedad);
		return campo;
	}
	
	private void crearCheckBoxTipo(Panel contenedor)	{
		Panel panelTipo = crearFila("Tipo:", 100, contenedor);
		CheckBox taxativa = new CheckBox(panelTipo);
		taxativa.bindValueToProperty("taxativa");
		new Label(panelTipo).setText("Taxativa");
		new Label(panelTipo).setWidth(5);
		CheckBox comparativa = new CheckBox(panelTipo);
		comparativa.bindValueToProperty("comparativa");
		new Label(panelTipo).setText("Comparativa");
		new Label(panelTipo).setText("      "); // por alguna extraña razón, si no pongo estas
		new Label(panelTipo).setText("      "); // etiquetas, la de Comparativa no aparece
	}
	
	private void crearContenedorOrden(Panel contenedor)	{
		Panel contenedorOrden = crearFila("Orden:", 100, contenedor);
		RadioSelector<Orden> ordenes = new RadioSelector<Orden>(contenedorOrden, "ordenes");
		ordenes.bindValueToProperty("orden");
	}
	
	private void crearRadiosEvaluacion(Panel contenedor) {
		Panel contenedorEvaluacion = crearFila("Evaluación:", 100, contenedor);
		RadioSelector<Evaluación> evaluaciones = new RadioSelector<Evaluación>(contenedorEvaluacion, "evaluaciones");
		evaluaciones.bindValueToProperty("evaluacion");
	}
	
	private void crearSelectorIndicador(Panel contenedor) {
		Panel contenedorSelector = crearFila("Indicador:", 100, contenedor);
		Selector<Indicador> indicadores = new Selector<>(contenedorSelector);
		indicadores.setWidth(250);
		indicadores.allowNull(true);
		indicadores.bindItemsToProperty("nombreIndicadores");
		indicadores.bindValueToProperty("nombreIndicador");
	}
	
	private void crearSelectorPrioridad(Panel contenedor) {
		Panel contenedorSelector = crearFila("Prioridad:", 100, contenedor);
		Selector<Indicador> prioridades = new Selector<>(contenedorSelector);
		prioridades.allowNull(true);
		prioridades.bindItemsToProperty("prioridades");
		prioridades.bindValueToProperty("prioridad");
		prioridades.bindEnabledToProperty("comparativa");
		prioridades.setWidth(250);
	}
	
	@Override
	protected void addActions(Panel actionsPanel)	{
		new Button(actionsPanel).setCaption("Cancelar").onClick(this::close);
		new Label(actionsPanel).setWidth(130);
		new Button(actionsPanel).setCaption("Guardar cambios").setAsDefault()
		.onClick(() -> {
			String resultado = getModelObject().guardarCambios();
			if(!resultado.isEmpty()) {
				showInfo(String.format("Condición %s con éxito.", resultado));
				this.close();
			}
			else showInfo(getModelObject().getError());
		});
	}
	
}
