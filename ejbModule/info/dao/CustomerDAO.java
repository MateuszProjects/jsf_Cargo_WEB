package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.User;

@Stateless
public class CustomerDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public User find(Integer id) {
		return em.find(User.class, id);
	}

	public void createUserCustomer(User user) {
		em.persist(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}
}
