package tp1.view;

import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import tp1.Util;
import tp1.model.Company;
import tp1.model.Indicator;
import tp1.model.Period;
import tp1.viewModel.IndicatorViewModel;

@SuppressWarnings("serial")
public class IndicatorView extends SimpleWindow<IndicatorViewModel> {

	public IndicatorView(WindowOwner parent, Company company, Period period) {
		super(parent, new IndicatorViewModel(company, period));
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
		createSelector(mainPanel);
		new Label(mainPanel).bindValueToProperty("description");
		createFormulaSection(mainPanel);
		createValueLabel(mainPanel);
		createErrorLabel(mainPanel);
	}
	
	private void createSelector(Panel panel) {
		Selector<Indicator> indicators = new Selector<>(panel);
		indicators.bindItemsToProperty("indicators");
		indicators.bindValueToProperty("indicator");
	}
	
	private void createFormulaSection(Panel container) {
		new Label(container).setFontSize(20).bindValueToProperty("formula");
	}
	
	private void createValueLabel(Panel container) {
		Label value = new Label(container);
		value.setForeground(Util.BLUE_COLOUR);
		value.setFontSize(30).setWidth(300).bindValueToProperty("value");
	}
	
	private void createErrorLabel(Panel container) {
		Label label = new Label(container);
		label.setForeground(Util.RED_COLOUR);
		label.bindValueToProperty("error");
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
	}
}
