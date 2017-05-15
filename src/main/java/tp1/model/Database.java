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
	private static Database instance = new Database();
	private List<Metric> metrics;
	private List<Company> companies;
	private List<Period> periods;
	private List<Indicator> indicators;
	
	public static Database getInstance() {
		return instance;
	}
	
	private Database() {
		metrics = readFile("metrics.json", Metric.class);
		indicators = readFile("indicators.json", Indicator.class);

		createCompanies();
		createPeriods();
	}
	
	private void createCompanies() {
		companies = metrics.stream().map(metric -> metric.getCompany()).distinct()
				.sorted().map(symbol -> new Company(symbol)).collect(Collectors.toList());
		companies.add(0, Company.EMPTY);
	}
	
	private void createPeriods() {
		periods = metrics.stream().map(metric -> metric.getPeriod()).distinct()
				.sorted(Collections.reverseOrder()).map(year -> new Period(year)).collect(Collectors.toList());
		periods.add(0, Period.EMPTY);
	}
	
	public List<Metric> getMetrics() {
		return metrics;
	}
	
	public List<Metric> getMetrics(Company company, Period period) {
		return Util.filterList(metrics, (metric) -> 
			(company == Company.EMPTY || metric.getCompany().equals(company.getSymbol())) &&
			(period == Period.EMPTY || metric.getPeriod() == period.getYear())
		);
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
	
	public List<Period> getPeriods() {
		return periods;
	}
	
	public List<Indicator> getIndicators() {
		return indicators;
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> readFile(String filename, Class<T> type) {
		try {
			File file = Util.getResource(filename);
			Class<?> arrayType = Class.forName("[L" + type.getName() + ";");
			T[] array = (T[]) new ObjectMapper().readValue(file, arrayType);
			return Arrays.asList(array);
		} catch(IOException | ClassNotFoundException e) {
			String msg = "Error al intentar crear la base de datos a partir del archivo " + filename;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
		}
	}
}
