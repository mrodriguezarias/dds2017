package tp1;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import tp1.view.MainView;

public class App extends Application {

	@Override
	protected Window<?> createMainWindow() {
		return new MainView(this);
	}
	
	public static void main(String[] args) {
		new App().start();
	}
}
