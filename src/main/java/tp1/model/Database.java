package tp1.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import tp1.Util;

public class Database {
	
	private final static String FILENAME = "metrics.json";
	
	private static Database instance = new Database();
	private List<Metric> metrics;
	private List<Company> companies;
	private List<Period> periods;
	
	public static Database getInstance() {
		return instance;
	}
	
	private Database() {
		metrics = readFile();
		
		companies = metrics.stream().map(metric -> metric.getCompany()).distinct()
				.sorted().map(symbol -> new Company(symbol)).collect(Collectors.toList());
		companies.add(0, new Company(""));
		
		periods = metrics.stream().map(metric -> metric.getPeriod()).distinct()
				.sorted(Collections.reverseOrder()).map(year -> new Period(year)).collect(Collectors.toList());
		periods.add(0, new Period(0));
	}
	
	public List<Metric> getMetrics() {
		return metrics;
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
	
	public List<Period> getPeriods() {
		return periods;
	}
	
	private List<Metric> readFile() {
		File file = Util.getResource(FILENAME);
		
		try {
			return Arrays.asList(new ObjectMapper().readValue(file, Metric[].class));
		} catch(IOException e) {
			String msg = "Error al intentar crear la base de datos a partir del archivo " + FILENAME;
			throw new RuntimeException(msg);
		}
	}
}
