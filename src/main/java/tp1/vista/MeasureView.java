package tp1.vista;

import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.modeloDeVista.MeasureComponent;
import tp1.modeloDeVista.MeasureViewModel;

@SuppressWarnings("serial")
public class MeasureView extends SimpleWindow<MeasureViewModel> {

	public MeasureView(WindowOwner parent, MeasureComponent measure) {
		super(parent, new MeasureViewModel(measure));
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle(getModelObject().title());
		new Label(mainPanel).bindValueToProperty("description");
		new Label(mainPanel).setFontSize(20).bindValueToProperty("heading");
		createValueLabel(mainPanel);
	}
	
	private void createValueLabel(Panel panel) {
		Label value = new Label(panel);
		value.setFontSize(30).setWidth(300).bindValueToProperty("value");
		value.bindForegroundToProperty("colour");
	}

	@Override
	protected void addActions(Panel actionsPanel) {
	}
}
