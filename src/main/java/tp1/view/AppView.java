package tp1.view;
import java.awt.Color;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;

import tp1.viewModel.AppViewModel;

@SuppressWarnings("serial")
public class AppView extends MainWindow<AppViewModel> {

	public AppView() {
		super(new AppViewModel());
	}

	@Override
	public void createContents(Panel mainPanel) {
		setTitle("Aplicacion para consultar estados de cuentas de empresas");
	    mainPanel.setLayout(new VerticalLayout());

	    new Label(mainPanel).setText("Para cargar archivo de cuentas haga click en cargar");

//	    new TextBox(mainPanel).bindValueToProperty("gradosCelsius");

	    new Button(mainPanel) //
	    .setCaption("Cargar"); //
//	    .onClick(() -> getModelObject().cargarArchivo());

//	    new Label(mainPanel) //
//	    .setBackground(Color.ORANGE) //
//	    .bindValueToProperty("gradosFarenheit");

	    new Label(mainPanel).setText(" ddsa - grupo 25 - 2017");
	  }
	
	  public static void main(String[] args) {
		    new AppView().startApplication();
		  }

}