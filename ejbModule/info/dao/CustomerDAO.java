package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	public User merge(User user) {
		return em.merge(user);
	}

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<User> getCustomerList(Map<String, Object> searchParams, PaginationInfo info) {
		List<User> list = null;

		String select = "select p ";
		String from = "from User p ";
		String where = "";
		String group_by = "";
		String having = "";
		String join = "";
		String orderBY = "";

		Integer idEmployee = (Integer) searchParams.get("idEmployee");
		String address = (String) searchParams.get("address");
		Integer idUser = (Integer) searchParams.get("idUser");

		if (idEmployee != null) {
			if (where.isEmpty()) {
				where = " where ";
			} else {
				where += " or ";
			}
			where += " p.idusers like :idEmployee";
		}

		if (address != null) {
			if (where.isEmpty()) {
				where = " where ";
			} else {
				where += " or ";
			}
			if (join.isEmpty()) {
				join = " JOIN ";
			}

			where += " p.address like :address";
			join += " p.address a ";

		}

		if (idUser != null) {
			if (where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " p.idusers like :idUser";
		}

		Query querycount = em.createQuery("SELECT COUNT(p.idusers) " + from + join);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + group_by + having + orderBY);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (idEmployee != null) {
			query.setParameter("idEmployee", idEmployee);
		}

		if (address != null) {
			query.setParameter("address", address);
		}
		
		if(idUser != null){
			query.setParameter("idUser", idUser);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
