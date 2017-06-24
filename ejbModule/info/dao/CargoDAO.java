package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Cargo;

@Stateless
public class CargoDAO {
	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public Cargo find(Integer id) {
		return em.find(Cargo.class, id);
	}

	public void createCargo(Cargo cargo) {
		em.persist(cargo);
	}

	public void remove(Cargo cargo ) {
		em.remove(em.merge(cargo));
	}

}
