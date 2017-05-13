package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Customer;

@Stateless
public class CustomerDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	
	public Customer find(Integer id) {
		return em.find(Customer.class, id);
	}

	public void createCustomer(Customer customer) {
		em.persist(customer);
	}

	public void remove(Customer customer) {
		em.remove(em.merge(customer));
	}

	public List<Customer> getSearchList(Map<String, Object> searchParams) {
		List<Customer> list = null;

		String select = "select c ";
		String from = "from Customer c ";
		String where = "";
		String orderby = "";
		String join = "";

		return list;

	}
	
}
