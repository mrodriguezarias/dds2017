package tp1.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Company implements Comparable<Company> {
	
	public static final Company EMPTY = new Company("");
	
	private String name;
	private String symbol;
	public List<Metric> metrics;
	
	public Company(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Optional<Metric> getMetric(String name, short period){
//		Metric metricValid = null;
//		for (Metric metric : metrics){
//			if(metric.getName().equals(name) && (metric.getPeriod() == period)){
//				metricValid = metric;
//			}
//		}
//		return metricValid;
	Optional<Metric> metrica = metrics
			.stream()
			.filter(metric -> (metric.getName().equals(name) && (metric.getPeriod() == period)))
			.findFirst();
	return metrica;
	
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
