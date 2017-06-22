package tp1.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
	
	private String name;
	private List<Metric> metrics;
	
	public Company(String name) {
		this.name = name;
		this.metrics = new ArrayList<>();
	}
	
	public Company(String name, List<Metric> metrics) {
		this.name = name;
		this.metrics = metrics;
	}
	
	public String getName() {
		return name;
	}
	
	public void addMetric(Metric metric) {
		metrics.add(metric);
	}
	
	public boolean hasMetric(String name) {
		return metrics.stream().anyMatch(x -> x.getName().equals(name));
	}

	public List<Metric> getMetrics() {
		return metrics;
	}

	public Metric getMetric(String name, short period){

		Metric metric = metrics.stream()
		            .filter(x -> (x.getName().equals(name)) && (x.getPeriod() == (period)) )
		            .findFirst()
		            .orElse(null);
		return metric;
	
	}
}
