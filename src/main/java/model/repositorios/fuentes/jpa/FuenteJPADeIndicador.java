package model.repositorios.fuentes.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityTransaction;

import model.indicador.Indicador;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.fuentes.FuenteDeIndicador;

public class FuenteJPADeIndicador implements FuenteDeIndicador {
	
	AdministradorJPA<Indicador> jpa = new AdministradorJPA<>(Indicador.class);
	List<Indicador> indicadores;

	@Override
	public List<Indicador> cargar() {
		indicadores = jpa.obtenerTodos();
		indicadores.forEach(indicador -> {
			indicador.actualizar(indicador.getName(), indicador.obtenerDescripción(), indicador.obtenerFórmula());
		});
		return indicadores;
	}

	@Override
	public List<String> obtenerNombres() {
		return jpa.obtenerTodos().stream().map(i -> i.getName()).collect(Collectors.toList());
	}

	@Override
	public void guardar(RepositorioDeIndicadores repositorio,List<Indicador> indicators) {
		EntityTransaction transacción = jpa.iniciarTransacción();
		indicators.forEach(indicador -> {
			if(encontrarOriginal(indicador) == null) {
				jpa.persistir(indicador);
				indicadores.add(indicador);
			}
		});
		transacción.commit();
		repositorio.setIndicators(this.indicadores);
	}

	@Override
	public void remover(Indicador indicador) {
		Indicador original = encontrarOriginal(indicador);
		indicadores.remove(original);
		if(original == null) return;
		EntityTransaction transacción = jpa.iniciarTransacción();
		jpa.remover(original);
		transacción.commit();
	}
	
	private Indicador encontrarOriginal(Indicador indicador) {
		return indicadores.stream().filter(i -> i.getName().equals(indicador.getName())).findFirst().orElse(null);
	}
	
	@Override
	public void actualizar(Indicador viejo, Indicador nuevo) {
		Indicador original = encontrarOriginal(viejo);
		EntityTransaction transacción = jpa.iniciarTransacción();
		original.actualizar(nuevo.getName(), nuevo.obtenerDescripción(), nuevo.obtenerFórmula());
		transacción.commit();
	}
	
}
