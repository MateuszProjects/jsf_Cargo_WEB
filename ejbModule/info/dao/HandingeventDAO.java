package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Handlingevent;

@Stateless
public class HandingeventDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Handlingevent find(Integer id) {
		return em.find(Handlingevent.class, id);
	}

	public void createHandingevent(Handlingevent handlingevent) {
		em.persist(handlingevent);
	}

	public void remove(Handlingevent handlingevent) {
		em.remove(em.merge(handlingevent));
	}

	public Handlingevent merge(Handlingevent handlingevent) {
		return em.merge(handlingevent);
	}

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Handlingevent> getHandingEventList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Handlingevent> list = null;

		String select = "select p ";
		String from = "from Handlingevent p ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderBY = "";

		Integer idhandingevent = (Integer) searchParams.get("idhandingevent");
		String description = (String) searchParams.get("description");

		if (idhandingevent != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " p.idhandlingEvent like :idhandlingEvent ";
		}

		if (description != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " p.decriptonEvent like :decriptonEvent ";
		}

		Query querycount = em.createQuery("SELECT COUNT(p.idhandlingEvent) " + from + join + where);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderBY);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());
		
		if(idhandingevent != null){
			query.setParameter("idhandlingEvent", idhandingevent);
		}
		
		
		if(description != null){
			query.setParameter("decriptonEvent", description);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
