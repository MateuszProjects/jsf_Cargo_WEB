package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Handlingevent;

@Stateless
public class HandingeventDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Handlingevent find(Integer id) {
		return em.find(Handlingevent.class, id);
	}

	public void createHandingevent(Handlingevent handlingevent) {
		em.persist(handlingevent);
	}

	public void remove(Handlingevent handlingevent) {
		em.remove(em.merge(handlingevent));
	}
}
