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
import tp1.model.Period;
import tp1.model.Metric;
import tp1.viewModel.MetricViewModel;

@SuppressWarnings("serial")
public class MetricView extends SimpleWindow<MetricViewModel> {

	public MetricView(WindowOwner parent) {
		super(parent, new MetricViewModel());
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
		new Label(filterPanel).setText("Filtros:");
		
		Selector<Company> companyFilter = new Selector<>(filterPanel);
		companyFilter.bindItemsToProperty("companies");
		companyFilter.bindValueToProperty("selectedCompany");
		
		Selector<Period> periodFilter = new Selector<>(filterPanel);
		periodFilter.bindItemsToProperty("periods");
		periodFilter.bindValueToProperty("selectedPeriod");
	}
	
	private void createTable(Panel mainPanel) {
		Table<Metric> table = new Table<>(mainPanel, Metric.class);
	    table.setNumberVisibleRows(16);
	    table.bindItemsToProperty("metrics");
	    
	    Util.createColumn("Empresa", "company", 60, table);
	    Util.createColumn("Período", "period", 60, table);
	    Util.createColumn("Tipo", "typeString", 80, table);
	    Util.createColumn("Nombre", "name", 80, table);
	    Util.createColumn("Valor", "valueString", 140, table);
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel).setCaption("Crear indicador").onClick(() -> showInfo("TODO"));
	}

}