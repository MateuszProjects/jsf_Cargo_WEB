package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Cargo;

@Stateless
public class CargoDAO {
	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Cargo find(Integer id) {
		return em.find(Cargo.class, id);
	}

	public void createCargo(Cargo cargo) {
		em.persist(cargo);
	}

	public void remove(Cargo cargo) {
		em.remove(em.merge(cargo));
	}

	public Cargo merge(Cargo cargo) {
		return em.merge(cargo);
	}

	public List<Cargo> getCargoList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Cargo> list = null;

		String select = "select c ";
		String from = "from Cargo c ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderBY = "";

		Integer idCargo = (Integer) searchParams.get("idCargo");

		if (idCargo != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.idcargo like :idCargo ";
		}

		Query querycount = em.createQuery("SELECT COUNT(c.idcargo) " + from + join + where);

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
