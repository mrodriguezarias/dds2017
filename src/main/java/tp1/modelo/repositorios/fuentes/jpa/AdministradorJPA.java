package tp1.modelo.repositorios.fuentes.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import tp1.modelo.Entidad;

public class AdministradorJPA<T> implements WithGlobalEntityManager {
	
	private Class<T> tipo;
	
	public AdministradorJPA(Class<T> tipo) {
		this.tipo = tipo;
	}
	
	public T obtenerPorId(int id) {
		return entityManager().find(tipo, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos() {
		String tabla = tipo.getAnnotation(Entity.class).name();
		String hql = String.format("SELECT e FROM %s e", tabla);
		Query consulta = entityManager().createQuery(hql);
		List<Entidad> entidades = consulta.getResultList();
		List<T> lista = new ArrayList<>();
		entidades.forEach(entidad -> {
			lista.add(entityManager().find(tipo, entidad.getId()));
		});
		return lista;
	}
	
	public EntityTransaction iniciarTransacción() {
		EntityTransaction transacción = entityManager().getTransaction();
		transacción.begin();
		return transacción;
	}
	
	public void terminarTransacción(EntityTransaction transacción) {
		transacción.commit();
	}
	
	public void persistir(T objeto) {
		entityManager().persist(objeto);
	}
	
	public void remover(T objeto) {
		entityManager().remove(objeto);
	}
}
