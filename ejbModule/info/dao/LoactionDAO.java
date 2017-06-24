package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Location;

@Stateless
public class LoactionDAO {
	
	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public Location find(Integer id) {
		return em.find(Location.class, id);
	}

	public void createLocation(Location location) {
		em.persist(location);
	}

	public void remove(Location location) {
		em.remove(em.merge(location));
	}
}
