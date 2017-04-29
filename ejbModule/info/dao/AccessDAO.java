package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Access;

@Stateless
public class AccessDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Access getLogin(String login, String pass) {
		Access access = null;

		Query query = em.createQuery("SELECT e From Access e where e.login=:login and e.pass=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);

		try {
			access = (Access) query.getSingleResult();
			if (access == null) {
				return null;
			} else {
				
				try {
					access.getEmployee();
					access.getCustomer();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return access;
	}
}
