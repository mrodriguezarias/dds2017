package tp1.vista;

import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modelo.indicador.Indicador;
import tp1.modeloDeLaVista.ModeloDeVistaDeIndicador;
import tp1.modeloDeLaVista.PaletaDeColores;

@SuppressWarnings("serial")
public class VistaDeIndicador extends SimpleWindow<ModeloDeVistaDeIndicador> {

	public VistaDeIndicador(WindowOwner pariente, String nombreDeLaEmpresa, short período) {
		super(pariente, new ModeloDeVistaDeIndicador(nombreDeLaEmpresa, período));
	}
	
	@Override
	public void createContents(Panel panelPrincipal) {
		configureLayout(panelPrincipal);
		createFormPanel(panelPrincipal);
		createActionsPanel(panelPrincipal);
	}
	
	@Override
	protected void createFormPanel(Panel panelPrincipal) {
		setTitle(getModelObject().title());
		crearSelector(panelPrincipal);
		new Label(panelPrincipal).bindValueToProperty("descripción");
		crearSecciónDeFórmula(panelPrincipal);
		createEtiquetaParaElValor(panelPrincipal);
	}
	
	private void crearSelector(Panel panel) {
		Selector<Indicador> indicadores = new Selector<>(panel);
		indicadores.bindItemsToProperty("nombresDeLosIndicadores");
		indicadores.bindValueToProperty("nombreDelIndicador");
	}
	
	private void crearSecciónDeFórmula(Panel contenedor) {
		new Label(contenedor).setFontSize(20).bindValueToProperty("fórmula");
	}
	
	private void createEtiquetaParaElValor(Panel contenedor) {
		Label valor = new Label(contenedor);
		valor.setForeground(PaletaDeColores.AZUL.obtenerValor());
		valor.setFontSize(30).setWidth(300).bindValueToProperty("valor");
	}
	
	@Override
	protected void addActions(Panel panelDeAcciones) {
	}
}
