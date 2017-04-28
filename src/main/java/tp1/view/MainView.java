package tp1.view;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

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
	    mainPanel.setLayout(new VerticalLayout());

	    new Label(mainPanel).setText("Para cargar archivo de cuentas haga clic en cargar");
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel).setCaption("Cargar");
	}

}