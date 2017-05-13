package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Role;

@Stateless
public class RoleDAO  {

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

	public List<Role> getSearchList(Map<String, Object> searchParams) {
		List<Role> list = null;

		String select = "select c ";
		String from = "from Customer c ";
		String where = "";
		String orderby = "";
		String join = "";

		return list;

	}
	
}
