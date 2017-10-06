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

	public Payment merge(Payment payment) {
		return em.merge(payment);
	}

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Payment> getSearchList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Payment> list = null;

		String select = "select c ";
		String from = "from Payment c ";
		String join = " join ";
		String where = "";
		String groupBY = " group by ";
		String having = " having ";
		String orderBY = " order by ";

		Integer idPayemt = (Integer) searchParams.get("idPayment");
		Double amount = (Double) searchParams.get("amount");
		Integer idUser = (Integer) searchParams.get("idUser");

		if (idPayemt != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}

			where += " c.idpayment like :idPayment";
		}

		if (idUser != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and ";
			}
			where += "c.iduser like :idUser";
		}

		if (amount != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}

			where += " c.amoutn like :amount";

		}

		Query querycount = em.createQuery("SELECT COUNT(c.idpayment) " + from);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderBY);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (idPayemt != null) {
			query.setParameter("idPayment", idPayemt);
		}

		if (idUser != null) {
			query.setParameter("IdUser", idUser);
		}

		if (amount != null) {
			query.setParameter("amount", amount);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

}
