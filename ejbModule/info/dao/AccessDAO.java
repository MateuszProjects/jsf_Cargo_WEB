package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.User;

@Stateless
public class AccessDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public User getLogin(String login, String pass) {
		User user = null;

		String select = "SELECT e ";
		String from = "From User e ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderBY = "";

		if (login != null) {
			if (where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " e.login=:login";
		}

		if (pass != null) {
			if (where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " e.pass=:pass";
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderBY);
		
		if (login != null) {
			query.setParameter("login", login);
		}

		if (pass != null) {
			query.setParameter("pass", pass);
		}
		
		try {
			user = (User) query.getSingleResult();
			if (user == null) {
				return null;
			} else {

				try {
					user.getRoles();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return user;
	}

}
