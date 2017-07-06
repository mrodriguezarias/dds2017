package tp1;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import tp1.vista.VistaPrincipal;

public class Aplicación extends Application {
	
	@Override
	protected Window<?> createMainWindow() {
		return new VistaPrincipal(this);
	}
	
	public static void main(String[] args) {
		new Aplicación().start();
	}
}
