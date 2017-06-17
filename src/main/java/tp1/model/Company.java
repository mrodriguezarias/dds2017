package tp1.model;

import java.util.ArrayList;
import java.util.List;

public class Company implements Comparable<Company> {
	
	public static final Company EMPTY = new Company("");
	
	private String name;
	private String symbol;
	private List<Metric> metrics;
	
	public Company(String name) {
		this.name = name;
		this.metrics = new ArrayList<>();
	}
	
	public void addMetric(Metric metric) {
		metrics.add(metric);
	}
	
	public String getName() {
		return name;
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
	
	
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || this.getClass() == obj.getClass()
				&& this.symbol.equals(((Company)obj).getName());
	}

	@Override
	public int compareTo(Company other) {
		return this.getName().compareTo(other.getName());
	}
}
