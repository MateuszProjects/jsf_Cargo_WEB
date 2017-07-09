package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	public Role merge(Role role) {
		return em.merge(role);
	}

	public List<Role> getSearchList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Role> list = null;

		String select = "select c ";
		String from = "from Role c ";
		String where = "";
		String orderby = "";
		String join = "";

		/*
		 * if (idAddress != null) { if (where.isEmpty()) { where = "where "; }
		 * else { where += " or "; } if (join.isEmpty()) { join =
		 * " join p.idaddress p  "; } where +=
		 * " c.idCustomer like :idCustomer "; }
		 */

		Query querycount = em.createQuery("SELECT COUNT(c.idrole) " + from);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
