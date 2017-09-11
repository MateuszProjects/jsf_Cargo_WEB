package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Deliveryhistory;

@Stateless
public class DeliveryhistoryDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Deliveryhistory find(Integer id) {
		return em.find(Deliveryhistory.class, id);
	}

	public void createDeliveryhistory(Deliveryhistory deliveryhistory) {
		em.persist(deliveryhistory);
	}

	public void remove(Deliveryhistory deliveryhistory) {
		em.remove(em.merge(deliveryhistory));
	}

	public Deliveryhistory merge(Deliveryhistory deliveryhistory) {
		return em.merge(deliveryhistory);
	}

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Deliveryhistory> getSearchList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Deliveryhistory> list = null;

		String select = "select c ";
		String from = "from Deliveryhistory c ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderby = "";

		String comment = (String) searchParams.get("comment");
		Integer idDeliveryHistory = (Integer) searchParams.get("idDeliveryHistory");
		Integer idUserRole = (Integer) searchParams.get("idUserRole");

		if(idUserRole != null){
			if(where.isEmpty()){
				where = "where ";
			}else{
				where += " and ";
			}
			// subQuery JPQL.
			where += "c.idCargo like :(select u.iduser where )";
		}
		
		if (idDeliveryHistory != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.iddeliveryHistory like :idDeliveryHistory ";
		}

		if (comment != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.coment like :comment ";
		}
		Query querycount = em.createQuery("SELECT COUNT(c.iddeliveryHistory) " + from);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderby);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if(idDeliveryHistory != null){
			query.setParameter("idDeliveryHistory", idDeliveryHistory);
		}
		
		if(comment != null){
			query.setParameter("comment", comment);
		}
		
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
