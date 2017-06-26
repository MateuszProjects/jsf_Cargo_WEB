package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Payment;

@Stateless
public class PaymentDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Payment find(Integer id) {
		return em.find(Payment.class, id);
	}

	public void createPayment(Payment payment) {
		em.persist(payment);
	}

	public void remove(Payment payment) {
		em.remove(em.merge(payment));
	}

	public List<Payment> getSearchList(Map<String, Object> searchParams,PaginationInfo info){
		List<Payment> list = null;

		String select = "select c ";
		String from = "from Payment c ";
		String where = "";
		String orderby = "";
		String join = "";

		Query querycount = em.createQuery("SELECT COUNT(c.idpayment) " + from + join + where);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

}
