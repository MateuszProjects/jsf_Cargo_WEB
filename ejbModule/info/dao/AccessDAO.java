package info.dao;

import javax.ejb.EJB;
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

		Query query = em.createQuery("SELECT e From User e where e.login=:login and e.pass=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);

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
