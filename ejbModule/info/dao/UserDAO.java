package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.User;

@Stateless
public class UserDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public User find(Integer id) {
		return em.find(User.class, id);
	}

	public void createUser(User user) {
		em.persist(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public List<User> getEmployeeList(Map<String, Object> searchParams, PaginationInfo info) {
		List<User> list = null;
		// add searchParams

		String select = "select p ";
		String from = "from User p ";
		String where = "";
		String group_by = "";
		String having = "";
		String join = "JOIN  p.addresses a";

		/*
		 * if (idAddress != null) { if (where.isEmpty()) { where = "where "; }
		 * else { where += " or "; } if (join.isEmpty()) { join =
		 * " join p.idaddress p  "; } where +=
		 * " c.idCustomer like :idCustomer "; }
		 */

		Query querycount = em.createQuery("SELECT COUNT(p.idusers) " + from + join + where);
		
		
		
		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// add join...
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

	public List<User> getCustomerList(Map<String, Object> searchParams, PaginationInfo info) {
		List<User> list = null;
		// add searchParams

		String select = "SELECT u  ";
		String from = "from User u ";
		String where = "";
		String join = "";

		Query querycount = em.createQuery("SELECT COUNT(u.idusers) " + from + join + where);

		
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
