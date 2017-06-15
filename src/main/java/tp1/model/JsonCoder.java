package tp1.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import tp1.Util;

public class JsonCoder {
	
	String fileName;
	Class<?> type;
	ObjectMapper mapper;
	
	public JsonCoder (String fileName, Class<?> type) {
		this.fileName = fileName;
		this.type = type;
		
	} 
	
	public List<?> read() {
		
		try {
			File file = Util.getResource(fileName);
			Object[] array = (Object[]) mapper.readValue(file, arrayType(type));
			return Arrays.asList(array);
		} catch(IOException e) {
			String msg = "Error al intentar leer datos del archivo " + fileName;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
		}
		
	} 

	public void write (List<?> list) {
		
		try {
			File file = Util.getResource(fileName);
			mapper.writeValue(file, list);
		} catch(IOException e) {
			String msg = "Error al intentar escribir datos en el archivo " + fileName;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
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
	
}
