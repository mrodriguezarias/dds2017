package tp1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Empresa {
	
	private String name;
	private List<Cuenta> metrics;
	
	public Empresa(String name) {
		this.name = name;
		this.metrics = new ArrayList<>();
	}
	
	public Empresa(String name, List<Cuenta> metrics) {
		this.name = name;
		this.metrics = metrics;
	}
	
	public String getName() {
		return name;
	}
	
	public void addMetric(Cuenta metric) {
		metrics.add(metric);
	}
	
	public boolean hasMetric(String name) {
		return metrics.stream().anyMatch(x -> x.getName().equals(name));
	}

	public List<Cuenta> getMetrics() {
		return metrics;
	}
	
	public List<Cuenta> getMetrics(short period) {
		return metrics.stream().filter(m -> m.getPeriod() == period).collect(Collectors.toList());
	}

	public Cuenta getMetric(String name, short period) {
		Cuenta metric = metrics.stream()
		            .filter(x -> x.getName().equals(name) && x.getPeriod() == period)
		            .findFirst().orElse(null);
		return metric;
	}
}
