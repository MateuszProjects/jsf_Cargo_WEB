package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Location;

@Stateless
public class LoactionDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Location find(Integer id) {
		return em.find(Location.class, id);
	}

	public void createLocation(Location location) {
		em.persist(location);
	}

	public void remove(Location location) {
		em.remove(em.merge(location));
	}

	public Location merge(Location location) {
		return em.merge(location);
	}

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Location> getSearchList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Location> list = null;

		String select = "select c ";
		String from = "from Location c ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderby = "";


		Integer idLocation = (Integer) searchParams.get("idlocation");
		String description = (String) searchParams.get("description");
		Integer idDeliverySpecyfication = (Integer) searchParams.get("idDeliverySpecyf");
		
		if(idDeliverySpecyfication != null){
			if(where.isEmpty()){
				where = "where ";
			}else{
				where += " and ";
			}
			
			where += " c.deliveryspecification.iddeliverySpecification like :idDeliverySpec";
		}

		if (idLocation != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.idlocation like :idlocation ";
		}
		
		if (description != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.description like :description ";
		}

		Query querycount = em.createQuery("SELECT COUNT(c.idlocation) " + from + join + where);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderby);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if(description != null){
			query.setParameter("description", description);
		}
		
		if(idLocation != null){
			query.setParameter("idlocation", idLocation);
		}
		
		if(idDeliverySpecyfication != null){
			query.setParameter("idDeliverySpec", idDeliverySpecyfication);
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
