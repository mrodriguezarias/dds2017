package tp1.modelo.repositorios.fuentes.jpa;

import java.util.List;

import javax.persistence.EntityTransaction;

import tp1.modelo.indicador.Indicador;
import tp1.modelo.metodología.Metodología;
import tp1.modelo.repositorios.fuentes.FuenteDeMetodologia;

public class FuenteJPADeMetodologia implements FuenteDeMetodologia {

	AdministradorJPA<Metodología> jpa = new AdministradorJPA<>(Metodología.class);
	List<Metodología> metodologias;
	
	@Override
	public List<Metodología> cargar() {
		this.metodologias = jpa.obtenerTodos();
		return metodologias;
	}

	@Override
	public void guardar(List<Metodología> metodologias) {
		 EntityTransaction transacción = jpa.iniciarTransacción();
		 metodologias.forEach(m-> jpa.persistir(m));
		 transacción.commit();		 
	}
	
	public void remover(Metodología metodologia) {
		Metodología original = encontrarOriginal(metodologia);
		if(original == null) return;
		EntityTransaction transacción = jpa.iniciarTransacción();
		jpa.remover(original);
		transacción.commit();
	}
	
	private Metodología encontrarOriginal(Metodología metodologia) {
		return metodologias.stream().filter(m -> m.obtenerNombre().equals(metodologia.obtenerNombre())).findFirst().orElse(null);
	}
	
	@Override
	public void actualizar(Metodología viejo, Metodología nuevo) {
		Metodología original = encontrarOriginal(viejo);
		EntityTransaction transacción = jpa.iniciarTransacción();
		//TODO ???
		transacción.commit();
	
}
