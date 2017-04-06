package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Customer;

@Stateless
public class CustomerDAO implements ICustomerDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public ICustomerDAO _ICustomerDAO;

	@Override
	public void createCustomer(Customer customer) {
		em.persist(customer);		
	}

	@Override
	public Customer find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer merge(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getSearchList(Map<String, Object> searchParams) {
		// TODO Auto-generated method stub
		return null;
	}

}
