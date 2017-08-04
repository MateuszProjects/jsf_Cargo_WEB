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

	/**
	 * Function for taking from Data Base object  Employee and looking for object with parameters  
	 * and with lazy Data Base.
	 * @param searchParams
	 * @param info
	 * @return list
	 */
	public List<User> getEmployeeList(Map<String, Object> searchParams, PaginationInfo info) {
		List<User> list = null;

		String select = "select p ";
		String from = "from User p ";
		String where = "";
		String group_by = "";
		String having = "";
		String join = "JOIN  p.addresses a ";

		Integer idEmployee = (Integer) searchParams.get("idEmployee");
		String name = (String) searchParams.get("name");
		String surname = (String) searchParams.get("surname");

		if (idEmployee != null) {
			if (where.isEmpty()) {
				where = " where ";
			}else{
				where += " and ";
			}
			where += " p.idusers like :idEmployee";
		}
		
		if (name != null) {
			if (where.isEmpty()) {
				where = " where ";
			}else{
				where += " and ";
			}
			where += " p.name like :name";
		}
		
		if (surname != null) {
			if (where.isEmpty()) {
				where = " where ";
			}else{
				where += " and ";
			}
			where += " p.surname like :surname";
		}
		
		Query querycount = em.createQuery("SELECT COUNT(p.idusers) " + from + join);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (idEmployee != null) {
			query.setParameter("idEmployee", idEmployee);
		}
		
		if (name != null) {
			query.setParameter("name", name);
		}
		
		if (surname != null) {
			query.setParameter("surname", surname);
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
