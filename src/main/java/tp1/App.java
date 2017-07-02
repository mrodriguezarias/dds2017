package tp1;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import tp1.model.repositories.CompanyRepository;
import tp1.model.repositories.IndicatorRepository;
import tp1.model.repositories.sources.JsonCompanySource;
import tp1.model.repositories.sources.JsonIndicatorSource;
import tp1.view.MainView;

public class App extends Application {
	
	static public CompanyRepository companyRepository;
	static public IndicatorRepository indicatorRepository;
	
	@Override
	protected Window<?> createMainWindow() {
		return new MainView(this);
	}
	
	public static void main(String[] args) {
		
		final String METRICS_FILE = "metrics.json";
		final String INDICATORS_FILE = "indicators.json";
		
		JsonIndicatorSource indicatorSource = new JsonIndicatorSource(INDICATORS_FILE);
		JsonCompanySource companySource = new JsonCompanySource(METRICS_FILE);
		
		companyRepository = new CompanyRepository(companySource);
		indicatorRepository = new IndicatorRepository(indicatorSource);
		indicatorRepository.createIndicators();
		
		new App().start();
	}
}
