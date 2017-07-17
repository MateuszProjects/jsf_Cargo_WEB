package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Billoflading;

@Stateless
public class BillofladingDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Billoflading find(Integer id) {
		return em.find(Billoflading.class, id);
	}

	public void createBilloflading(Billoflading billoflading) {
		em.persist(billoflading);
	}

	public void remove(Billoflading billoflading) {
		em.remove(em.merge(billoflading));
	}

	public Billoflading merge(Billoflading billoflading) {
		return em.merge(billoflading);
	}

	public List<Billoflading> getBillofladingList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Billoflading> list = null;

		String select = "select p ";
		String from = "from Billoflading p ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderBY = "";


		Integer idBillofloading = (Integer) searchParams.get("idBillofloading");
		String text = (String) searchParams.get("text");

		if (text != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += text;
		}
		
		if (idBillofloading != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += idBillofloading;
		}		

		Query querycount = em.createQuery("SELECT COUNT(p.idbillofLading) " + from + join + where);

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