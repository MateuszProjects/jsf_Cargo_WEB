package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Customer;

@Stateless
public class CustomerLoginDAO /* implements ICustomerLoginDAO */ {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Customer getCustomerLogin(String login, String pass) {
		Customer customer = null;

		Query query = em.createQuery("SELECT e From Customer e where e.login=:login and e.pass=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);
		
		try {
			customer = (Customer) query.getSingleResult();
			if(customer == null){
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return customer;
	}

}
