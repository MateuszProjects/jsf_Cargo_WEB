package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Payment;

@Stateless
public class PaymentDAO  {

	
	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public Payment find(Integer id) {
		return em.find(Payment.class, id);
	}

	public void createPayment(Payment payment ) {
		em.persist(payment);
	}

	public void remove(Payment payment) {
		em.remove(em.merge(payment));
	}

	public List<Payment> getSearchList(Map<String, Object> searchParams) {
		List<Payment> list = null;

		String select = "select c ";
		String from = "from Customer c ";
		String where = "";
		String orderby = "";
		String join = "";

		return list;

	}
	
}
