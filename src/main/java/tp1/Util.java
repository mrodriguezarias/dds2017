package tp1;

import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.widgets.tables.Column;

public class Util {
	
	public static File getResource(String name) {
		ClassLoader classLoader = App.class.getClassLoader();
		File file;

		try {
			file = new File(classLoader.getResource(name).getFile());
		} catch(NullPointerException e) {
			String message = String.format("Error al intentar leer el recurso \"%s\"", name);
			throw new RuntimeException(message);
		}
		
		return file;
	}
	
	public static <T, R> List<R> mapList(List<T> list, Function<T, R> function) {
		return list.stream().map(function).collect(Collectors.toList());
	}
	
	public static <T> List<T> filterList(List<T> list, Predicate<? super T> predicate) {
		return list.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public static <T> void createColumn(String title, String property, int length, Table<T> table) {
		Column<T> column = new Column<>(table);
		column.setTitle(title);
		column.setFixedSize(length);
		column.bindContentsToProperty(property);
	}
	
	
	
	
}
