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

	public List<Handlingevent> getHandingEventList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Handlingevent> list = null;
		

		// add searchParams 
		
		String select = "select p ";
		String from = "from Handlingevent p ";
		String where = "";
		String join = "";
		
		
		Query querycount = em.createQuery("SELECT COUNT(p.idhandlingEvent) " + from + join + where);
		
		
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
