package tp1;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import tp1.model.Metric;

public class App extends Application {

	@Override
	protected Window<?> createMainWindow() {
		return null;
	}
	
	public static void main(String[] args) {
		Metric.loadMetrics();
		Metric.getMetrics().stream().forEach(metric -> System.out.println(metric));
		
//		new App().start();
	}
}
