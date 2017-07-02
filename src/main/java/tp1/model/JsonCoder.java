package tp1.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import tp1.App;

public class JsonCoder {

	String filename;
	Class<?> type;
	ObjectMapper mapper;

	public JsonCoder (String filename, Class<?> type) {
		this.filename = filename;
		this.type = type;
		this.mapper = new ObjectMapper();
	}
	
	public List<String> obtenerCampo(String campo){
		List<String> nombres = new ArrayList<String>();
	
		try{
			File file = getResource(filename);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootArray = mapper.readTree(file);
	
			for(JsonNode root : rootArray){
				nombres.add(root.path(campo).asText());
//				System.out.println(root.path(campo).asText()); //fixme borrar
			}
		} catch (JsonProcessingException e) {
		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nombres;
	}

	public List<?> read() {
		try {
			File file = getResource(filename);
			Object[] array = (Object[]) mapper.readValue(file, arrayType(type));
			return Arrays.asList(array);
		} catch(IOException e) {
			throw new RuntimeException(String.format("Error al intentar leer datos del archivo '%s' (%s)",
					filename, e.getMessage()));
		}
	} 

	public void write(List<?> list) {
		try {
			File file = getResource(filename);
			mapper.writeValue(file, list);
		} catch(IOException e) {
			throw new RuntimeException(String.format("Error al intentar escribir datos en el archivo '%s' (%s)",
					filename, e.getMessage()));
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

	private File getResource(String name) {
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
}
