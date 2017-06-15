package tp1;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import tp1.model.CompanyRepository;
import tp1.model.IndicatorRepository;
import tp1.model.JsonCompanySource;
import tp1.model.JsonIndicatorSource;
import tp1.view.MainView;

public class App extends Application {
	
	static public CompanyRepository companies;
	static public IndicatorRepository indicators;
	
	@Override
	protected Window<?> createMainWindow() {
		return new MainView(this);
	}
	
	public static void main(String[] args) {
		
		final String METRICS_FILE = "metrics.json";
		final String INDICATORS_FILE = "indicators.json";
		
		JsonIndicatorSource indicatorSource = new JsonIndicatorSource(INDICATORS_FILE);
		JsonCompanySource companySource = new JsonCompanySource(METRICS_FILE);
		
		companies = new CompanyRepository(companySource);
		indicators = new IndicatorRepository(indicatorSource);
		
		
		new App().start();
	}
}
