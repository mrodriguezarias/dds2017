package tp1.view;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.model.Colour;
import tp1.model.Indicator;
import tp1.viewModel.AdminViewModel;

@SuppressWarnings("serial")
public class AdminView extends SimpleWindow<AdminViewModel> {
	
	public AdminView(WindowOwner parent) {
		super(parent, new AdminViewModel());
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		configureLayout(mainPanel);
		createFormPanel(mainPanel);
		createActionsPanel(mainPanel);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Administración de indicadores");
		createTopPanel(mainPanel);
		createFormRow("       Nombre", "name", mainPanel);
		createFormRow("Descripción", "description", mainPanel);
		createFormRow("       Fórmula", "formula", mainPanel);
		createErrorLabel(mainPanel);
	}
	
	private void createTopPanel(Panel container) {
		Panel panel = new Panel(container);
		panel.setLayout(new HorizontalLayout());
		createSelector(panel);
		createDeleteButton(panel);
	}
	
	private void createSelector(Panel container) {
		Selector<Indicator> indicators = new Selector<>(container);
		indicators.setWidth(225);
		indicators.bindItemsToProperty("indicators");
		indicators.bindValueToProperty("indicator");
	}
	
	private void createDeleteButton(Panel container) {
		Button deleteButton = new Button(container);
		deleteButton.setCaption("╳");
		deleteButton.bindEnabledToProperty("isEditing");
		deleteButton.onClick(getModelObject()::deleteIndicator);
	}
	
	private void createFormRow(String label, String property, Panel container) {
		Panel panel = new Panel(container);
		panel.setLayout(new HorizontalLayout());
		new Label(panel).setText(label + ":").setWidth(80);
		new TextBox(panel).setWidth(180).bindValueToProperty(property);
	}
	
	private void createErrorLabel(Panel container) {
		Label label = new Label(container);
		label.setForeground(Colour.RED.getValue());
		label.bindValueToProperty("error");
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel).setCaption("Nuevo")
			.onClick(getModelObject()::setCreateMode);
		new Label(actionsPanel).setWidth(50);
		new Button(actionsPanel).setCaption("Guardar cambios").setAsDefault()
			.onClick(() -> {
				String operation = getModelObject().saveChanges();
				if(!operation.isEmpty()) {
					showInfo(String.format("Indicador %s con éxito.", operation));
				}
			});
	}
	
	@Override
	public void close() {
		MainView parentView = (MainView) getOwner();
		parentView.getModelObject().updateMetrics();
		super.close();
	}
}
