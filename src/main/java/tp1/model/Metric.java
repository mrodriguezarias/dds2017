package tp1.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import tp1.Util;

public class Metric {
	
	private static List<Metric> metrics;
	
	public String name;
	public long value;
	public String company;
	public short period;
	
	@Override
	public String toString() {
		return String.format("%s for %s in %d: %,d", name, company, period, value);
	}
	
	public static void loadMetrics() {
		File file = Util.getResource("metrics.json");
		
		try {
			metrics = Arrays.asList(new ObjectMapper().readValue(file, Metric[].class));
		} catch (IOException e) {
			throw new RuntimeException("Error al intentar crear la lista de cuentas");
		}
	}
	
	public static List<Metric> getMetrics() {
		return metrics;
	}
}
