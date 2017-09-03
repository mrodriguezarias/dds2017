package tp1.modelo.repositorios.fuentes.jpa;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class AdministradorJPA<T> implements WithGlobalEntityManager {
	
	private Class<T> tipo;
	
	public AdministradorJPA(Class<T> tipo) {
		this.tipo = tipo;
	}
	
	public T obtenerPorId(int id) {
		return entityManager().find(tipo, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos(String condición) {
		String nombre = tipo.getSimpleName();
		String sql = String.format("SELECT e FROM %s e WHERE %s", nombre, condición);
		Query consulta = entityManager().createQuery(sql);
		return (List<T>) consulta.getResultList();
	}
	
	public List<T> obtenerTodos() {
		return obtenerTodos("1=1");
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
}
