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

	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Cargo> getCargoList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Cargo> list = null;

		String select = "select c ";
		String from = "from Cargo c ";
		String join = " join ";
		String where = "";
		String groupBY = " group by ";
		String having = " having ";
		String orderBY = " order by ";

		Integer idCargo = (Integer) searchParams.get("idCargo");
		String name = (String) searchParams.get("name");
		Integer weight = (Integer) searchParams.get("weight");
		String hazMat = (String) searchParams.get("hazMat");
		Integer idUser = (Integer) searchParams.get("idUser");

		if (idCargo != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.idcargo like :idCargo ";
		}

		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.name like :name ";
		}

		if (weight != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.weight like :weight ";
		}

		if (hazMat != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += "c.hazMat lik :hazMat";
		}

		if (idUser != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and ";
			}

			where += "c.user.iduser liki: idUser";
		}

		Query querycount = em.createQuery("SELECT COUNT(c.idcargo) " + from);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where + groupBY + having + orderBY);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (idCargo != null) {
			query.setParameter("idCargo", idCargo);
		}

		if (name != null) {
			query.setParameter("name", name);
		}

		if (weight != null) {
			query.setParameter("weight", weight);
		}

		if (hazMat != null) {
			query.setParameter("hazMat", hazMat);
		}

		if (idUser != null) {
			query.setParameter("idUser", idUser);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
