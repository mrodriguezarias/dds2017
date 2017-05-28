package tp1.view;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.Util;
import tp1.model.Company;
import tp1.model.Metric;
import tp1.model.Period;
import tp1.viewModel.MainViewModel;

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

		Selector<Company> companyFilter = new Selector<>(filterPanel);
		companyFilter.bindItemsToProperty("companies");
		companyFilter.bindValueToProperty("selectedCompany");
		
		new Label(filterPanel).setWidth(15);
		Selector<Period> periodFilter = new Selector<>(filterPanel);
		periodFilter.bindItemsToProperty("periods");
		periodFilter.bindValueToProperty("selectedPeriod");
		
		new Label(filterPanel).setWidth(15);
		createViewMetricButton(filterPanel);
	}

	private void createTable(Panel mainPanel) {
		Table<Metric> table = new Table<>(mainPanel, Metric.class);
		table.setNumberVisibleRows(16);
		table.bindItemsToProperty("metrics");
		table.bindSelectionToProperty("selectedMetric");

		Util.createColumn("Empresa", "company", 60, table);
		Util.createColumn("Período", "period", 60, table);
		Util.createColumn("Nombre", "name", 60, table);
		Util.createColumn("Tipo", "typeString", 70, table);
		Util.createColumn("Valor", "valueString", 100, table);
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		createIndicatorButton(actionsPanel);
		createAdminButton(actionsPanel);
	}
	
	private void createViewMetricButton(Panel panel) {
		Button button = new Button(panel);
		button.setCaption("Ver cuenta");
		button.bindEnabledToProperty("viewMetricEnabled");
		button.setAsDefault();
		button.onClick(() -> {
			new MetricView(this, getModelObject().getSelectedMetric()).open();
		});
	}
	
	private void createIndicatorButton(Panel panel) {
		Button button = new Button(panel);
		button.setCaption("Aplicar indicador…");
		button.bindEnabledToProperty("applyIndicatorEnabled");
		button.onClick(() -> {
			new IndicatorView(this, getModelObject().getSelectedCompany(),
					getModelObject().getSelectedPeriod()).open();
		});
	}
	
	private void createAdminButton(Panel panel) {
		new Label(panel).setWidth(55);
		Button button = new Button(panel);
		button.setCaption("Administrar indicadores");
		button.onClick(() -> {
			new AdminView(this).open();
		});
	}

}