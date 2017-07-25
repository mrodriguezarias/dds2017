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

import tp1.modelo.Cuenta;
import tp1.modelo.Empresa;
import tp1.modeloDeVista.MainViewModel;

@SuppressWarnings("serial")
public class MainView extends SimpleWindow<MainViewModel> {

	public MainView(WindowOwner parent) {
		super(parent, new MainViewModel());
	}

	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Analizador de cuentas");
		createFilters(mainPanel);
		createTable(mainPanel);
	}

	private void createFilters(Panel mainPanel) {
		Panel filterPanel = new Panel(mainPanel);
		filterPanel.setLayout(new HorizontalLayout());

		Selector<Empresa> companyFilter = new Selector<>(filterPanel);
		companyFilter.setWidth(120);
		companyFilter.bindItemsToProperty("companyNames");
		companyFilter.bindValueToProperty("selectedCompanyName");
		
		new Label(filterPanel).setWidth(15);
		Selector<Short> periodFilter = new Selector<>(filterPanel);
		periodFilter.setWidth(120);
		periodFilter.bindItemsToProperty("periods");
		periodFilter.bindValueToProperty("selectedPeriod");
		
		new Label(filterPanel).setWidth(15);
		crearBotónVerCuenta(filterPanel);
	}

	private void createTable(Panel mainPanel) {
		Table<Cuenta> table = new Table<>(mainPanel, Cuenta.class);
		table.setNumberVisibleRows(16);
		table.bindItemsToProperty("measures");
		table.bindSelectionToProperty("selectedMeasure");
		
		createColumn("Nombre", "name", 120, table);
		createColumn("Tipo", "type", 80, table);
		createColumn("Valor", "value", 160, table);
	}
	
	private <T> void createColumn(String title, String property, int length, Table<T> table) {
		Column<T> column = new Column<>(table);
		column.setTitle(title);
		column.setFixedSize(length);
		column.bindContentsToProperty(property);
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		actionsPanel.setLayout(new HorizontalLayout());
		crearBotonesParaIndicadores(actionsPanel);
		crearBotonesParaMetodologías(actionsPanel);
	}
	
	private void crearBotónVerCuenta(Panel panel) {
		Button button = new Button(panel);
		button.setCaption("Ver cuenta");
		button.bindEnabledToProperty("viewMetricEnabled");
		button.setAsDefault();
		button.onClick(() -> {
			new MeasureView(this, getModelObject().getSelectedMeasure()).open();
		});
	}
	
	private void crearBotonesParaIndicadores(Panel contenedor) {
		Panel panel = new Panel(contenedor);
		new Label(panel).setText("Indicadores").setFontSize(14);
		Panel botones = new Panel(panel);
		botones.setLayout(new HorizontalLayout());
		
		new Button(botones).setCaption("Aplicar").onClick(() -> {
			new IndicatorView(this, getModelObject().getSelectedCompanyName(),
					getModelObject().getSelectedPeriod()).open();
		});
		new Button(botones).setCaption("Administrar").onClick(() -> {
			new IndicatorAdminView(this).open();
		});
	}
	
	private void crearBotonesParaMetodologías(Panel contenedor) {
		Panel panel = new Panel(contenedor);
		new Label(panel).setText("Metodologías").setFontSize(14);
		Panel botones = new Panel(panel);
		botones.setLayout(new HorizontalLayout());
		
		new Button(botones).setCaption("Aplicar").onClick(()->{
			new MetodologiaView(this).open();
		} );
		new Button(botones).setCaption("Administrar");
	}

}