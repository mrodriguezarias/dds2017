package tp1.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import tp1.Util;

public class Database {
	private static Database instance = new Database();
	
	private final String METRICS_FILE = "metrics.json";
	private final String INDICATORS_FILE = "indicators.json";
	
	private ObjectMapper objectMapper;
	private List<Metric> metrics;
	private List<Company> companies;
	private List<Period> periods;
	private List<Indicator> indicators;
	
	public static Database getInstance() {
		return instance;
	}
	
	private Database() {
		createObjectMapper();
		metrics = readListFromFile(METRICS_FILE, Metric.class);
		indicators = readListFromFile(INDICATORS_FILE, Indicator.class);

		createCompanies();
		createPeriods();
	}
	
	private void createObjectMapper() {
		this.objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setVisibility(
				objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
				.withFieldVisibility(JsonAutoDetect.Visibility.NONE)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
		);
	}
	
	private void createCompanies() {
		companies = metrics.stream().map(metric -> metric.getCompany()).distinct()
				.sorted().collect(Collectors.toList());
		companies.add(0, Company.EMPTY);
	}
	
	private void createPeriods() {
		periods = metrics.stream().map(metric -> metric.getPeriod()).distinct()
				.sorted(Collections.reverseOrder()).collect(Collectors.toList());
		periods.add(0, Period.EMPTY);
	}
	
	public List<Metric> getMetrics() {
		return metrics;
	}
	
	public List<Metric> getMetrics(Company company, Period period) {
		return Util.filterList(metrics, (metric) -> 
			(company == Company.EMPTY || metric.getCompany().equals(company)) &&
			(period == Period.EMPTY || metric.getPeriod().equals(period))
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
	
	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
		writeListToFile(INDICATORS_FILE, indicators);
	}
	
	public List<Indicator> getIndicators(Company company, Period period) {
		return Util.mapList(indicators, indicator -> {
			indicator.setCompany(company);
			indicator.setPeriod(period);
			return indicator;
		});
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> readListFromFile(String filename, Class<T> type) {
		try {
			File file = Util.getResource(filename);
			T[] array = (T[]) objectMapper.readValue(file, Util.arrayType(type));
			return Arrays.asList(array);
		} catch(IOException e) {
			String msg = "Error al intentar leer datos del archivo " + filename;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
		}
	}
	
	private void writeListToFile(String filename, Object value) {
		try {
			File file = Util.getResource(filename);
			objectMapper.writeValue(file, value);
		} catch(IOException e) {
			String msg = "Error al intentar escribir datos en el archivo " + filename;
			System.out.println(e.getMessage());
			throw new RuntimeException(msg);
		}
	}
}
