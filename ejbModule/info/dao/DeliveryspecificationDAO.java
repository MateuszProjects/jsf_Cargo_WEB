package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Deliveryspecification;

@Stateless
public class DeliveryspecificationDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Deliveryspecification find(Integer id) {
		return em.find(Deliveryspecification.class, id);
	}

	public void createDeliveryspecyfication(Deliveryspecification deliveryspecification) {
		em.persist(deliveryspecification);
	}

	public void remove(Deliveryspecification deliveryspecification) {
		em.remove(em.merge(deliveryspecification));
	}

	public List<Deliveryspecification> getDeliberySpecyficationList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Deliveryspecification> list = null;
		// add searchParams

		String select = "select p ";
		String from = "from Deliveryspecification p ";
		String where = "";
		String join = "";

		/*if (idAddress != null) {
		if (where.isEmpty()) {
			where = "where ";
		} else {
			where += " or ";
		}
		if (join.isEmpty()) {
			join = " join p.idaddress p  ";
		}
		where += " c.idCustomer like :idCustomer ";
	}*/
		
		Query querycount = em.createQuery("SELECT COUNT(p.iddeliverySpecification) " + from + join + where);

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
