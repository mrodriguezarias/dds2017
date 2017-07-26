package tp1.vista;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modelo.metodología.Condición;
import tp1.modelo.metodología.Metodología;
import tp1.modeloDeVista.MetodologiaAdminViewModel;

@SuppressWarnings("serial")
public class MetodologiaAdminView  extends SimpleWindow<MetodologiaAdminViewModel>{

	public MetodologiaAdminView(WindowOwner parent) {
		super(parent, new MetodologiaAdminViewModel());
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Administrador de metodologías");
		createSelector(mainPanel);
		createFormRow("Nombre", "nombre", mainPanel);
		createTable(mainPanel);
	}
	
	private void createSelector(Panel panel) {
		Selector<Metodología> metodologias = new Selector<>(panel);
		metodologias.bindItemsToProperty("nombreMetodologias");
		metodologias.bindValueToProperty("nombreMetodologia"); 
	}
	
	private void createFormRow(String label, String property, Panel container) {
		Panel panel = new Panel(container);
		panel.setLayout(new HorizontalLayout());
		new Label(panel).setText(label + ":").setWidth(80);
		new TextBox(panel).setWidth(180).bindValueToProperty(property);
	}
	
	private void createTable(Panel mainPanel) {
		Table<Condición> table = new Table<>(mainPanel, Condición.class);
		table.setNumberVisibleRows(10);
		table.bindItemsToProperty("condiciones");
		table.bindSelectionToProperty("condicionSeleccionada");	
		createColumn("Condiciones", "nombre", 150, table);
	}
	
	private <T> void createColumn(String title, String property, int length, Table<T> table) {
		Column<T> column = new Column<>(table);
		column.setTitle(title);
		column.setFixedSize(length);
		column.bindContentsToProperty(property);
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {

	
	}


}
