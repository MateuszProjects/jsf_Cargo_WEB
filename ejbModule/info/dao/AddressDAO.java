package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import info.entities.Address;

@Stateless
public class AddressDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Address find(Integer id) {
		return em.find(Address.class, id);
	}

	public void createAddress(Address address) {
		em.persist(address);
	}

	public void remove(Address address) {
		em.remove(em.merge(address));
	}

	public List<Address> getAddressList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Address> list = null;

		// add searchParams

		String select = "select p ";
		String from = "from Address p ";
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
		

		Query querycount = em.createQuery("SELECT COUNT(p.idaddress) " + from + join + where);

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
