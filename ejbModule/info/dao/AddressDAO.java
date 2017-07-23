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

	public Address merge(Address address) {
		return em.merge(address);
	}

	public List<Address> getAddressList(Map<String, Object> searchParams, PaginationInfo info) {
		List<Address> list = null;

		String select = "select a ";
		String from = "from Address a ";
		String join = "";
		String where = "";
		String groupBY = "";
		String having = "";
		String orderBY = "";

		Integer idAddress = (Integer) searchParams.get("idAddress");
		String cityCode = (String) searchParams.get("cityCode");
		String street = (String) searchParams.get("street");

		if (idAddress != null) {
			if (where.isEmpty()) {
				where = " where ";
			}else{
				where += " or ";
			}
			where += " a.idaddress like :idAddress ";
		}

		if (cityCode != null) {
			if (where.isEmpty()) {
				where = " where ";
			}else{
				where += " or ";
			}
			where += " a.cityCode like :CityCode";
		}

		if (street != null) {
			if (where.isEmpty()) {
				where = " where ";
			}else{
				where += " or ";
			}
			where += " a.street like :Street";
		}
		Query querycount = em.createQuery("SELECT COUNT(a.idaddress) " + from);

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (idAddress != null) {
			query.setParameter("idAddress", idAddress);
		}

		if (street != null) {
			query.setParameter("Street", street);
		}

		if (cityCode != null) {
			query.setParameter("CityCode", cityCode);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
