package tp1.vista;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modelo.indicador.Indicador;
import tp1.modeloDeLaVista.ModeloDeVistaDeAdministrador;
import tp1.modeloDeLaVista.PaletaDeColores;

@SuppressWarnings("serial")
public class VistaDeAdministrador extends SimpleWindow<ModeloDeVistaDeAdministrador> {
	
	public VistaDeAdministrador(WindowOwner pariente) {
		super(pariente, new ModeloDeVistaDeAdministrador());
	}
	
	@Override
	public void createContents(Panel panelPrincipal) {
		configureLayout(panelPrincipal);
		createFormPanel(panelPrincipal);
		createActionsPanel(panelPrincipal);
	}
	
	@Override
	protected void createFormPanel(Panel panelPrincipal) {
		setTitle("Administración de indicadores");
		createTopPanel(panelPrincipal);
		crearFilaDeFormulario("       Nombre", "nombre", panelPrincipal);
		crearFilaDeFormulario("Descripción", "descripción", panelPrincipal);
		crearFilaDeFormulario("       Fórmula", "fórmula", panelPrincipal);
		crearEtiquetaDeError(panelPrincipal);
	}
	
	private void createTopPanel(Panel contenedor) {
		Panel panel = new Panel(contenedor);
		panel.setLayout(new HorizontalLayout());
		crearSelector(panel);
		crearBotónDeEliminar(panel);
	}
	
	private void crearSelector(Panel contenedor) {
		Selector<Indicador> indicadores = new Selector<>(contenedor);
		indicadores.setWidth(225);
		indicadores.allowNull(true);
		indicadores.bindItemsToProperty("nombresDeLosIndicadores");
		indicadores.bindValueToProperty("nombreDelIndicador");
	}
	
	private void crearBotónDeEliminar(Panel contenedor) {
		Button botónDeEliminar = new Button(contenedor);
		botónDeEliminar.setCaption("╳");
		botónDeEliminar.bindEnabledToProperty("estáEditando");
		botónDeEliminar.onClick(getModelObject()::eliminarIndicador);
	}
	
	private void crearFilaDeFormulario(String etiqueta, String propiedad, Panel contenedor) {
		Panel panel = new Panel(contenedor);
		panel.setLayout(new HorizontalLayout());
		new Label(panel).setText(etiqueta + ":").setWidth(80);
		new TextBox(panel).setWidth(180).bindValueToProperty(propiedad);
	}
	
	private void crearEtiquetaDeError(Panel contenedor) {
		Label etiqueta = new Label(contenedor);
		etiqueta.setForeground(PaletaDeColores.ROJO.obtenerValor());
		etiqueta.bindValueToProperty("error");
	}
	
	@Override
	protected void addActions(Panel panelDeAcciones) {
		new Button(panelDeAcciones).setCaption("Nuevo")
			.onClick(getModelObject()::setCreateMode);
		new Label(panelDeAcciones).setWidth(50);
		new Button(panelDeAcciones).setCaption("Guardar cambios").setAsDefault()
			.onClick(() -> {
				String operación = getModelObject().guardarCambios();
				if(!operación.isEmpty()) {
					showInfo(String.format("Indicador %s con éxito.", operación));
				}
			});
	}
	
	@Override
	public void close() {
		VistaPrincipal vistaPariente = (VistaPrincipal) getOwner();
		vistaPariente.getModelObject().actualizarMedidas();
		super.close();
	}
}
