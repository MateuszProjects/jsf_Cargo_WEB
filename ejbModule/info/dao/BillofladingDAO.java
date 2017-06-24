package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Billoflading;

@Stateless
public class BillofladingDAO {
	
	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Billoflading find(Integer id) {
		return em.find(Billoflading.class, id);
	}

	public void createBilloflading(Billoflading billoflading ) {
		em.persist(billoflading);
	}

	public void remove(Billoflading billoflading) {
		em.remove(em.merge(billoflading));
	}
}
