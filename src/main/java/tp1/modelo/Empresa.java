package tp1.modelo;

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
	
	public String obtenerNombre() {
		return name;
	}
	
	public void agregarCuenta(Cuenta metric) {
		metrics.add(metric);
	}
	
	public boolean tieneCuenta(String name) {
		return metrics.stream().anyMatch(x -> x.obtenerNombre().equals(name));
	}

	public List<Cuenta> obtenerCuentas() {
		return metrics;
	}
	
	public List<Cuenta> obtenerCuentas(short period) {
		return metrics.stream().filter(m -> m.getPeriod() == period).collect(Collectors.toList());
	}

	public Cuenta obtenerCuenta(String name, short period) {
		Cuenta metric = metrics.stream()
		            .filter(x -> x.obtenerNombre().equals(name) && x.getPeriod() == period)
		            .findFirst().orElse(null);
		return metric;
	}
}
