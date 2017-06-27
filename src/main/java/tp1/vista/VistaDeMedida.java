package tp1.vista;

import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modeloDeLaVista.ComponenteDeMedida;
import tp1.modeloDeLaVista.ModeloDeVistaDeMedida;

@SuppressWarnings("serial")
public class VistaDeMedida extends SimpleWindow<ModeloDeVistaDeMedida> {

	public VistaDeMedida(WindowOwner pariente, ComponenteDeMedida medida) {
		super(pariente, new ModeloDeVistaDeMedida(medida));
	}
	
	@Override
	public void createContents(Panel panelPrincipal) {
		configureLayout(panelPrincipal);
		createFormPanel(panelPrincipal);
		createActionsPanel(panelPrincipal);
	}
	
	@Override
	protected void createFormPanel(Panel panelPrincipal) {
		setTitle(getModelObject().título());
		new Label(panelPrincipal).bindValueToProperty("descripción");
		new Label(panelPrincipal).setFontSize(20).bindValueToProperty("encabezado");
		createValueLabel(panelPrincipal);
	}
	
	private void createValueLabel(Panel panel) {
		Label valor = new Label(panel);
		valor.setFontSize(30).setWidth(300).bindValueToProperty("valor");
		valor.bindForegroundToProperty("color");
	}

	@Override
	protected void addActions(Panel panelDeAcciones) {
	}
}
