package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Transport;

@Stateless
public class TransportDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Transport find(Integer id) {
		return em.find(Transport.class, id);
	}

	public void createTransport(Transport transport) {
		em.persist(transport);
	}

	public void remove(Transport transport) {
		em.remove(em.merge(transport));
	}

	public Transport merge(Transport transport) {
		return em.merge(transport);
	}

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Transport> getSearchList(Map<String, Object> searchParams, PaginationInfo info) {

		List<Transport> list = null;

		String select = "SELECT t ";
		String from = "FROM Transport t ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderBY = "";

		Integer idTransport = (Integer) searchParams.get("idTransport");
		String name = (String) searchParams.get("name");
		Double payKm = (Double) searchParams.get("payKM");

		if (idTransport != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " t.idtransport like :idTransport ";
		}

		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " t.name like :name ";
		}

		if (payKm != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " t.paykm like :payKM ";
		}
		Query querycount = em.createQuery("SELECT COUNT(t.idtransport) " + from);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderBY);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (idTransport != null) {
			query.setParameter("idTransport", idTransport);
		}
		if (name != null) {
			query.setParameter("name", name);
		}
		if (payKm != null) {
			query.setParameter("payKM", payKm);
		}
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
}
