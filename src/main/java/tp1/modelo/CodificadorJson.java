package tp1.modelo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import tp1.Aplicación;

public class CodificadorJson {

	String nombreDeArchivo;
	Class<?> tipo;
	ObjectMapper mapeador;

	public CodificadorJson (String nombreDeArchivo, Class<?> tipo) {
		this.nombreDeArchivo = nombreDeArchivo;
		this.tipo = tipo;
		this.mapeador = new ObjectMapper();
	}

	public List<?> leer() {
		try {
			File archivo = obtenerRecurso(nombreDeArchivo);
			Object[] arreglo = (Object[]) mapeador.readValue(archivo, arrayType(tipo));
			return Arrays.asList(arreglo);
		} catch(IOException e) {
			throw new RuntimeException(String.format("Error al intentar leer datos del archivo '%s' (%s)",
					nombreDeArchivo, e.getMessage()));
		}
	} 

	public void escribir(List<?> lista) {
		try {
			File archivo = obtenerRecurso(nombreDeArchivo);
			mapeador.writeValue(archivo, lista);
		} catch(IOException e) {
			throw new RuntimeException(String.format("Error al intentar escribir datos en el archivo '%s' (%s)",
					nombreDeArchivo, e.getMessage()));
		}
	}

	private Class<?> arrayType(Class<?> type) {
		try {
			return Class.forName("[L" + type.getName() + ";");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private File obtenerRecurso(String nombre) {
		ClassLoader cargadorDeClase = Aplicación.class.getClassLoader();
		File archivo;

		try {
			archivo = new File(cargadorDeClase.getResource(nombre).getFile());
		} catch(NullPointerException e) {
			String mensaje = String.format("Error al intentar leer el recurso \"%s\"", nombre);
			throw new RuntimeException(mensaje);
		}

		return archivo;
	}
}
