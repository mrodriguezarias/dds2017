package tp1.view;

import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.model.Metric;
import tp1.viewModel.MetricViewModel;

@SuppressWarnings("serial")
public class MetricView extends SimpleWindow<MetricViewModel> {

	public MetricView(WindowOwner parent, Metric metric) {
		super(parent, new MetricViewModel(metric));
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
