package tp1.vista;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

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
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {

	
	}


}
