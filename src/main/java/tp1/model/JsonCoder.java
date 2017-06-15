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
	Class type;
	ObjectMapper mapper;
	
	public JsonCoder (String fileName, Class type){
		this.fileName = fileName;
		this.type = type;
		
	} 
	
	public void init (String filename, Class type){}
	
	public <type> List<type> read(){
		
		try {
			File file = Util.getResource(fileName);
			type[] array = (type[]) mapper.readValue(file, Util.arrayType(type));
			return Arrays.asList(array);
		} catch(IOException e) {
			String msg = "Error al intentar leer datos del archivo " + fileName;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
		}
		
	} 

	public void write (Object list){
		
		try {
			File file = Util.getResource(fileName);
			mapper.writeValue(file, list);
		} catch(IOException e) {
			String msg = "Error al intentar escribir datos en el archivo " + fileName;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
		}
	}
	
}
