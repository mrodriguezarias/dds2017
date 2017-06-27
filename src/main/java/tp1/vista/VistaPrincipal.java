package tp1.vista;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modelo.Empresa;
import tp1.modeloDeLaVista.ComponenteDeMedida;
import tp1.modeloDeLaVista.ModeloDeVistaPrincipal;

@SuppressWarnings("serial")
public class VistaPrincipal extends SimpleWindow<ModeloDeVistaPrincipal> {

	public VistaPrincipal(WindowOwner pariente) {
		super(pariente, new ModeloDeVistaPrincipal());
	}

	@Override
	public void createContents(Panel panelPrincipal) {
		configureLayout(panelPrincipal);
		createFormPanel(panelPrincipal);
		createActionsPanel(panelPrincipal);
	}

	@Override
	protected void createFormPanel(Panel panelPrincipal) {
		setTitle("Analizador de cuentas");
		crearFiltros(panelPrincipal);
		createTable(panelPrincipal);
	}

	private void crearFiltros(Panel panelPrincipal) {
		Panel panelDeFiltros = new Panel(panelPrincipal);
		panelDeFiltros.setLayout(new HorizontalLayout());

		Selector<Empresa> filtroDeEmpresa = new Selector<>(panelDeFiltros);
		filtroDeEmpresa.setWidth(120);
		filtroDeEmpresa.bindItemsToProperty("nombresDeLasEmpresas");
		filtroDeEmpresa.bindValueToProperty("nombreDeLaEmpresaSeleccionada");
		
		new Label(panelDeFiltros).setWidth(15);
		Selector<Short> filtroDePeríodo = new Selector<>(panelDeFiltros);
		filtroDePeríodo.setWidth(120);
		filtroDePeríodo.bindItemsToProperty("períodos");
		filtroDePeríodo.bindValueToProperty("períodoSeleccionado");
		
		new Label(panelDeFiltros).setWidth(15);
		crearBotónDeVerMedida(panelDeFiltros);
	}

	private void createTable(Panel panelPrincipal) {
		Table<ComponenteDeMedida> tabla = new Table<>(panelPrincipal, ComponenteDeMedida.class);
		tabla.setNumberVisibleRows(16);
		tabla.bindItemsToProperty("medidas");
		tabla.bindSelectionToProperty("medidaSeleccionada");

		crearColumna("Empresa", "nombreDeLaEmpresa", 60, tabla);
		crearColumna("Período", "período", 60, tabla);
		crearColumna("Nombre", "nombre", 60, tabla);
		crearColumna("Tipo", "tipo", 70, tabla);
		crearColumna("Valor", "valor", 100, tabla);
	}
	
	private <T> void crearColumna(String título, String propiedad, int longitud, Table<T> tabla) {
		Column<T> columna = new Column<>(tabla);
		columna.setTitle(título);
		columna.setFixedSize(longitud);
		columna.bindContentsToProperty(propiedad);
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		createBotónDeIndicador(actionsPanel);
		createBotónDeAdministración(actionsPanel);
	}
	
	private void crearBotónDeVerMedida(Panel panel) {
		Button button = new Button(panel);
		button.setCaption("Ver cuenta");
		button.bindEnabledToProperty("verMedidaHabilitado");
		button.setAsDefault();
		button.onClick(() -> {
			new VistaDeMedida(this, getModelObject().getMedidaSeleccionada()).open();
		});
	}
	
	private void createBotónDeIndicador(Panel panel) {
		Button botón = new Button(panel);
		botón.setCaption("Aplicar indicador…");
		botón.onClick(() -> {
			new VistaDeIndicador(this, getModelObject().getNombreDeLaEmpresaSeleccionada(),
					getModelObject().getPeríodoSeleccionado()).open();
		});
	}
	
	private void createBotónDeAdministración(Panel panel) {
		new Label(panel).setWidth(55);
		Button botón = new Button(panel);
		botón.setCaption("Administrar indicadores");
		botón.onClick(() -> {
			new VistaDeAdministrador(this).open();
		});
	}

}