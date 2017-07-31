package tp1.vista;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modelo.Empresa;
import tp1.modelo.metodología.Metodología;
import tp1.modeloDeVista.MetodologiaViewModel;

@SuppressWarnings("serial")
public class MetodologiaView extends SimpleWindow<MetodologiaViewModel> {

	public MetodologiaView(WindowOwner parent) {
		super(parent, new MetodologiaViewModel());
	}

	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Aplicar metodología");
		createSelector(mainPanel);
		createTable(mainPanel);
	}
	
	private void createTable(Panel mainPanel) {
		Table<Empresa> table = new Table<>(mainPanel, Empresa.class);
		table.setNumberVisibleRows(10);
		table.bindItemsToProperty("empresas");
		
		Column<Empresa> columna = new Column<>(table);
		columna.setTitle("Empresas");
		columna.bindContentsToProperty("nombre");
		columna.setFixedSize(250);
	}

	private void createSelector(Panel panel) {
		Selector<Metodología> metodologias = new Selector<>(panel);
		metodologias.setWidth(200);
		metodologias.bindItemsToProperty("nombreMetodologias");
		metodologias.bindValueToProperty("nombreMetodologia"); 
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		// TODO Auto-generated method stub
		
	}
}
