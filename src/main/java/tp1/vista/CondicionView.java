package tp1.vista;

import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.RadioSelector;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import tp1.modelo.indicador.*;
import tp1.modelo.metodología.*;
import tp1.modeloDeVista.*;

@SuppressWarnings("serial")
public class CondicionView extends SimpleWindow<CondicionViewModel> {
	public CondicionView(WindowOwner padre) {
		super(padre, new CondicionViewModel());
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel)	{
		setTitle("Crear condición");
		Panel contenedor = new Panel(mainPanel);
		crearTextBox("Nombre de la condición:", "nombre", contenedor);
		crearCheckBoxTipo(contenedor);
		crearTextBox("Período:", "periodo", contenedor);
		crearContenedorOrden(contenedor);
		TextBox referencia = crearTextBox("Valor de referencia:", "valorDeReferencia", contenedor);
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
	
	private TextBox crearTextBox(String label, String propiedad, Panel contenedor) {
		Panel contenedorTextBox = crearFila(label, 250, contenedor);
		TextBox campo = new TextBox(contenedorTextBox);
		campo.setWidth(250);
		campo.bindValueToProperty(propiedad);
		return campo;
	}
	
	private void crearCheckBoxTipo(Panel contenedor)	{
		Panel panelTipo = crearFila("Tipo:", 250, contenedor).setWidth(500);
		CheckBox taxativa = new CheckBox(panelTipo);
		taxativa.setWidth(20);
		taxativa.bindValueToProperty("taxativa");
		new Label(panelTipo).setText("Taxativa").setWidth(105);
		CheckBox comparativa = new CheckBox(panelTipo);
		comparativa.setWidth(20);
		comparativa.bindValueToProperty("comparativa");
		new Label(panelTipo).setText("Comparativa").setWidth(115);
	}
	
	private void crearContenedorOrden(Panel contenedor)	{
		Panel contenedorOrden = crearFila("Tipo:", 250, contenedor);
		RadioSelector<Orden> ordenes = new RadioSelector<Orden>(contenedorOrden, "ordenes");
		ordenes.bindValueToProperty("ordenElegido");
	}
	
	private void crearRadiosEvaluacion(Panel contenedor) {
		Panel contenedorEvaluacion = crearFila("Evaluación:", 200, contenedor);
		RadioSelector<Evaluación> evaluaciones = new RadioSelector<Evaluación>(contenedorEvaluacion, "evaluaciones");
		evaluaciones.bindValueToProperty("evaluacion");
	}
	
	private void crearSelectorIndicador(Panel contenedor) {
		Panel contenedorSelector = crearFila("Indicador:", 250, contenedor);
		Selector<Indicador> indicadores = new Selector<>(contenedorSelector);
		indicadores.setWidth(250);
		indicadores.allowNull(true);
		indicadores.bindItemsToProperty("nombreIndicadores");
		indicadores.bindValueToProperty("nombreIndicadorElegido");
	}
	
	private void crearSelectorPrioridad(Panel contenedor) {
		Panel contenedorSelector = crearFila("Prioridad:", 250, contenedor);
		Selector<Indicador> prioridades = new Selector<>(contenedorSelector);
		prioridades.setWidth(250);
		prioridades.allowNull(true);
		prioridades.bindItemsToProperty("prioridades");
		prioridades.bindValueToProperty("prioridadElegida");
	}
	
	@Override
	protected void addActions(Panel actionsPanel)	{
		new Button(actionsPanel).setCaption("Guardar cambios").setAsDefault()
		.onClick(() -> {
			String operation = getModelObject().guardarCambios();
			if(!operation.isEmpty()) {
				showInfo(String.format("Condición %s con éxito.", operation));
			}
			else showInfo("Hubo un problema al guardar la condición. Asegúrese de que todos los campos están completos y su tipo es el correcto.");
		});
	}
}
