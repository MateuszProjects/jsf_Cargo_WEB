package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Customer;

@Stateless
public class CustomerImplDAO implements ICustomerDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	// object Interface;
	private ICustomerDAO _customerDAO;

	@Override
	public void createBooking(Customer customer) {
		em.persist(customer);

	}

}
