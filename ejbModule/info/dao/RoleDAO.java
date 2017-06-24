package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Role;

@Stateless
public class RoleDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Role find(Integer id) {
		return em.find(Role.class, id);
	}

	public void createRole(Role role) {
		em.persist(role);
	}

	public void remove(Role role) {
		em.remove(em.merge(role));
	}
}
